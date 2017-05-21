package com.wang.video_downloader;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.wang.android_lib.util.DialogUtil;
import com.wang.android_lib.util.M;
import com.wang.android_lib.util.NotificationUtil;
import com.wang.java_util.DebugUtil;
import com.wang.java_util.TextUtil;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.base.Status;
import org.wlf.filedownloader.listener.OnDeleteDownloadFilesListener;
import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;
import org.wlf.filedownloader.listener.simple.OnSimpleFileDownloadStatusListener;

import java.util.ArrayList;
import java.util.List;

/**
 * by 王荣俊 on 2016/9/26.
 */
public class DownloadListActivity extends Activity implements View.OnClickListener {

    private ListView lvDownload;
    private ImageView btnOpenMenu;

    private DownloadAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_list);
        initView();
        FileDownloader.registerDownloadStatusListener(mOnFileDownloadStatusListener);
        initList();
    }

    private void initView() {
        lvDownload = (ListView) findViewById(R.id.lv_download);
        btnOpenMenu = (ImageView) findViewById(R.id.btn_open_menu);
        btnOpenMenu.setOnClickListener(this);
    }

    private void initList() {

        lvDownload.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DownloadListItem item = adapter.getDownloadListItems().get(position);

                switch (item.getState()) {
                    case DownloadListItem.STATE_DOWNLOADING:
                    case DownloadListItem.STATE_WAITING:
                        FileDownloader.pause(item.getUrl());
                        item.setState(DownloadListItem.STATE_PAUSE);
                        break;
                    case DownloadListItem.STATE_PAUSE:
                    case DownloadListItem.STATE_FAILED:
                        FileDownloader.start(item.getUrl());
                        item.setState(DownloadListItem.STATE_DOWNLOADING);
                        break;
                    case DownloadListItem.STATE_FINISH:
                        break;
                    case DownloadListItem.STATE_READING:
                        break;
                }
                adapter.notifyDataSetChanged();
            }
        });

        ArrayList<DownloadListItem> items = new ArrayList<>();
        List<DownloadFileInfo> filesInfo = FileDownloader.getDownloadFiles();
        for (DownloadFileInfo fileInfo : filesInfo) {
            int state = DownloadListItem.STATE_PAUSE;
            switch (fileInfo.getStatus()) {
                case Status.DOWNLOAD_STATUS_DOWNLOADING:
                    state = DownloadListItem.STATE_DOWNLOADING;
                    break;
                case Status.DOWNLOAD_STATUS_PAUSED:
                    state = DownloadListItem.STATE_PAUSE;
                    break;
                case Status.DOWNLOAD_STATUS_COMPLETED:
                    state = DownloadListItem.STATE_FINISH;
                    break;
                case Status.DOWNLOAD_STATUS_ERROR:
                    state = DownloadListItem.STATE_FAILED;
                    break;
                case Status.DOWNLOAD_STATUS_FILE_NOT_EXIST:
                    state = DownloadListItem.STATE_FINISH;
                    break;
                case Status.DOWNLOAD_STATUS_UNKNOWN:
                    state = DownloadListItem.STATE_FAILED;
                    break;
                case Status.DOWNLOAD_STATUS_WAITING:
                    state = DownloadListItem.STATE_WAITING;
                    break;
            }
            int type = DownloadListItem.toType(TextUtil.getTextAfterLastPoint(fileInfo.getFileName()));
            DownloadListItem item = new DownloadListItem(fileInfo.getFileName(),
                    fileInfo.getUrl(), type, state);
            item.setCurrentSize((int) fileInfo.getDownloadedSizeLong());
            item.setTotalSize((int) fileInfo.getFileSizeLong());
            items.add(item);
        }
        adapter = new DownloadAdapter(this, items);
        lvDownload.setAdapter(adapter);

    }

    private OnFileDownloadStatusListener mOnFileDownloadStatusListener = new OnSimpleFileDownloadStatusListener() {

        @Override
        public void onFileDownloadStatusWaiting(DownloadFileInfo downloadFileInfo) {
            // 等待下载（等待其它任务执行完成，或者FileDownloader在忙别的操作）
            boolean updateSucceed = updateDownloadListItem(downloadFileInfo, DownloadListItem.STATE_WAITING);
            if (!updateSucceed) {
                //若能够执行到这里，说明这是新建任务，列表还未显示，需要新建列表项。
                createDownloadListItem(downloadFileInfo, DownloadListItem.STATE_WAITING);
            }
        }

        @Override
        public void onFileDownloadStatusPreparing(DownloadFileInfo downloadFileInfo) {
            // 准备中（即，正在连接资源）
//            M.t(DownloadListActivity.this, "正在连接资源");
        }

        @Override
        public void onFileDownloadStatusPrepared(DownloadFileInfo downloadFileInfo) {
            // 已准备好（即，已经连接到了资源）
//            M.t(DownloadListActivity.this, "已经连接到了资源");
        }

        @Override
        public void onFileDownloadStatusDownloading(DownloadFileInfo downloadFileInfo, float downloadSpeed, long
                remainingTime) {
            // 正在下载，downloadSpeed为当前下载速度，单位KB/s，remainingTime为预估的剩余时间，单位秒
            List<DownloadListItem> items = adapter.getDownloadListItems();
            for (DownloadListItem item : items) {
                if (downloadFileInfo.getUrl().equals(item.getUrl())) {
                    item.setState(DownloadListItem.STATE_DOWNLOADING);
                    item.setCurrentSize((int) downloadFileInfo.getDownloadedSizeLong());
                    item.setTotalSize((int) downloadFileInfo.getFileSizeLong());
                    item.setSpeed((int) downloadSpeed * 1024);
                    item.setRemainTime((int) remainingTime);
                    adapter.notifyDataSetChanged();
                    return;
                }
            }
            //若能够执行到这里，说明这是新建任务，列表还未显示，需要新建列表项。
            createDownloadListItem(downloadFileInfo, DownloadListItem.STATE_DOWNLOADING);
        }

        @Override
        public void onFileDownloadStatusPaused(DownloadFileInfo downloadFileInfo) {
            // 下载已被暂停
            boolean updateSucceed = updateDownloadListItem(downloadFileInfo, DownloadListItem.STATE_PAUSE);
            if (!updateSucceed) {
                //若能够执行到这里，说明这是新建任务，列表还未显示，需要新建列表项。
                createDownloadListItem(downloadFileInfo, DownloadListItem.STATE_PAUSE);
            }
        }

        @Override
        public void onFileDownloadStatusCompleted(DownloadFileInfo downloadFileInfo) {
            // 下载完成（整个文件已经全部下载完成）
            updateDownloadListItem(downloadFileInfo, DownloadListItem.STATE_FINISH);
        }

        @Override
        public void onFileDownloadStatusFailed(String url, DownloadFileInfo downloadFileInfo, FileDownloadStatusFailReason failReason) {
            // 下载失败了，详细查看失败原因failReason，有些失败原因你可能必须关心

            String failType = failReason.getType();
            String failUrl = failReason.getUrl();// 或：failUrl = url，url和failReason.getType()会是一样的

            if (FileDownloadStatusFailReason.TYPE_URL_ILLEGAL.equals(failType)) {
                // 下载failUrl时出现url错误
            } else if (FileDownloadStatusFailReason.TYPE_STORAGE_SPACE_IS_FULL.equals(failType)) {
                // 下载failUrl时出现本地存储空间不足
            } else if (FileDownloadStatusFailReason.TYPE_NETWORK_DENIED.equals(failType)) {
                // 下载failUrl时出现无法访问网络
            } else if (FileDownloadStatusFailReason.TYPE_NETWORK_TIMEOUT.equals(failType)) {
                // 下载failUrl时出现连接超时
            } else {
                // 更多错误....
            }

            // 查看详细异常信息
            Throwable failCause = failReason.getCause();// 或：failReason.getOriginalCause()

            // 查看异常描述信息
            String failMsg = failReason.getMessage();// 或：failReason.getOriginalCause().getMessage()

            M.t(DownloadListActivity.this, "出现下载失败的任务: " + downloadFileInfo.getFileName());
            NotificationUtil.showNotification(DownloadListActivity.this, downloadFileInfo.getId(),
                    downloadFileInfo.getFileName(), DebugUtil.getExceptionStackTrace(failReason));

            updateDownloadListItem(downloadFileInfo, DownloadListItem.STATE_FAILED);

        }
    };

    private boolean updateDownloadListItem(DownloadFileInfo downloadFileInfo, int state) {
        List<DownloadListItem> items = adapter.getDownloadListItems();
        for (DownloadListItem item : items) {
            if (downloadFileInfo.getUrl().equals(item.getUrl())) {
                item.setState(state);
                adapter.notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }

    private void createDownloadListItem(DownloadFileInfo downloadFileInfo, int state) {
        DownloadListItem item = new DownloadListItem(
                downloadFileInfo.getFileName(),
                downloadFileInfo.getUrl(),
                DownloadListItem.toType(downloadFileInfo.getFileName()),
                state
        );
        adapter.getDownloadListItems().add(item);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        PopupMenu menu = new PopupMenu(this, btnOpenMenu);
        menu.getMenuInflater().inflate(R.menu.popul_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.btn_menu_imooc) {
                    DialogUtil.showInputDialog(DownloadListActivity.this, "输入慕课网课程地址",
                            C.imoocUrl + "515", new DialogUtil.OnInputFinishListener() {
                                @Override
                                public void onInputFinish(String url) {
                                    if (url.startsWith(C.imoocUrl) && url.length() > C.imoocUrl.length()) {
                                        ImoocManager manager = new ImoocManager(DownloadListActivity.this);
                                        manager.chooseQuality(url);
                                    } else {
                                        M.t(DownloadListActivity.this, "请输入正确的地址");
                                    }
                                }
                            });

                } else if (id == R.id.btn_menu_jikexueyuan) {
                    M.t(DownloadListActivity.this, "敬请期待");

                } else if (id == R.id.btn_menu_start_all) {
                    List<String> urls = new ArrayList<>();
                    List<DownloadListItem> items = adapter.getDownloadListItems();
                    for (DownloadListItem downloadListItem : items) {
                        urls.add(downloadListItem.getUrl());
                    }
                    FileDownloader.start(urls);

                } else if (id == R.id.btn_menu_pause_all) {
                    FileDownloader.pauseAll();

                } else if (id == R.id.btn_menu_clear_all) {
                    DialogUtil.showConfirmDialog(DownloadListActivity.this, "提示",
                            "确实要清空下载任务吗（不会删除文件）",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    clearAllDownloadTasks();
                                }
                            });

                }

                return false;
            }
        });
        menu.show();
    }

    private void clearAllDownloadTasks() {
        List<String> urls = new ArrayList<>();
        List<DownloadListItem> items = adapter.getDownloadListItems();
        for (DownloadListItem downloadListItem : items) {
            urls.add(downloadListItem.getUrl());
        }
        FileDownloader.delete(urls, false, new OnDeleteDownloadFilesListener() {
            @Override
            public void onDeleteDownloadFilesPrepared(List<DownloadFileInfo> downloadFilesNeedDelete) {

            }

            @Override
            public void onDeletingDownloadFiles(List<DownloadFileInfo> downloadFilesNeedDelete, List<DownloadFileInfo> downloadFilesDeleted, List<DownloadFileInfo> downloadFilesSkip, DownloadFileInfo downloadFileDeleting) {

            }

            @Override
            public void onDeleteDownloadFilesCompleted(List<DownloadFileInfo> downloadFilesNeedDelete, List<DownloadFileInfo> downloadFilesDeleted) {
                adapter.getDownloadListItems().clear();
                adapter.notifyDataSetChanged();
                M.t(DownloadListActivity.this, "全部已删除");
            }
        });
    }
}
