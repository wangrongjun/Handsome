package com.wang.video_downloader;

import android.content.Context;
import android.os.AsyncTask;

import com.wang.android_lib.util.AndroidHttpUtil;
import com.wang.android_lib.util.DialogUtil;
import com.wang.android_lib.util.M;
import com.wang.android_lib.util.NotificationUtil;
import com.wang.java_program.video_download.bean.Course;
import com.wang.java_program.video_download.bean.CourseDataFile;
import com.wang.java_program.video_download.bean.Video;
import com.wang.java_program.video_download.website.Imooc;
import com.wang.java_util.DebugUtil;
import com.wang.java_util.FileUtil;
import com.wang.java_util.GsonUtil;
import com.wang.java_util.StreamUtil;
import com.wang.java_util.TextUtil;

import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.listener.OnDetectBigUrlFileListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ImoocManager {

    private Context context;
    private String url;

    public ImoocManager(Context context) {
        this.context = context;
    }

    public void chooseQuality(final String url) {
        this.url = url;
        DialogUtil.showSingleChoiceDialog(
                context,
                "选择视频清晰度",
                new String[]{"标清", "高清", "超清"},
                0,
                new DialogUtil.OnSingleChoiceConfirmListener() {
                    @Override
                    public void onSingleChoiceConfirm(int checkedItem, String item) {
                        int videoQuality = Imooc.VIDEO_QUALITY_M;
                        switch (checkedItem) {
                            case 0:
                                videoQuality = Imooc.VIDEO_QUALITY_L;
                                break;
                            case 1:
                                videoQuality = Imooc.VIDEO_QUALITY_M;
                                break;
                            case 2:
                                videoQuality = Imooc.VIDEO_QUALITY_H;
                                break;
                        }
                        startGetCourseInfo(videoQuality);
                    }
                });
    }

    public void startGetCourseInfo(final int videoQuality) {

        boolean connected = AndroidHttpUtil.isNetworkConnected(context);
        if (!connected) {
            M.t(context, "网络不可用");
            return;
        }

        DialogUtil.showProgressDialog(context, "正在获取课程信息");
        new AsyncTask<Void, Void, Course>() {
            @Override
            protected Course doInBackground(Void... params) {
                String cookie = null;
                try {
                    InputStream is = context.getResources().getAssets().open("imooc.txt");
                    cookie = StreamUtil.readInputStream(is, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Imooc imooc = new Imooc(url, videoQuality, cookie);

                try {
                    return imooc.getCourse();
                } catch (Exception e) {
                    String trace = DebugUtil.getExceptionStackTrace(e);
                    NotificationUtil.showNotification(context, 111, "VideoDownloader - Error", trace);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Course course) {
                DialogUtil.cancelProgressDialog();
                DialogUtil.showProgressDialog(context, "正在创建下载任务");
                NotificationUtil.showNotification(context, 1111, "Message",
                        GsonUtil.formatJsonHtmlEscaping(course));
                if (course != null) {
                    P.setCourse(course);
                    String courseDir = C.downloadDir +
                            TextUtil.correctFileName(course.getCourseName(), "_") +
                            File.separator;
                    createFiles(course, courseDir);//创建文件夹，课程信息文件，保存课程信息
                    createDownloadTask(course, courseDir);
                } else {
                    M.t(context, "课程信息获取失败");
                }
                DialogUtil.cancelProgressDialog();
            }
        }.execute();

    }

    public String createFiles(Course course, String courseDir) {
        FileUtil.mkdirs(courseDir);
        List<String> chapterNames = course.getChapterNames();
        if (chapterNames != null) {
            for (String chapterName : chapterNames) {
                FileUtil.mkdirs(courseDir, TextUtil.correctFileName(chapterName, "_"));
            }
        }
        try {
            FileUtil.write(url, courseDir + "网址.txt");
            FileUtil.write(course.getCourseHint(), courseDir + "课程学习提示.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return courseDir;
    }

    public void createDownloadTask(Course course, String courseDir) {
        createAndStartDataFiles(course.getCourseDataFiles(), courseDir);//下载学习资料
        createAndStartVideos(course.getVideos(), courseDir);//下载课程视频
    }

    private void createAndStartDataFiles(List<CourseDataFile> courseDataFiles, final String courseDir) {
        if (courseDataFiles == null) {
            return;
        }
        for (final CourseDataFile dataFile : courseDataFiles) {
            FileDownloader.detect(dataFile.getUrl(), new OnDetectBigUrlFileListener() {
                @Override
                public void onDetectNewDownloadFile(String url, String fileName, String saveDir, long fileSize) {
                    // 如果有必要，可以改变文件名称fileName和下载保存的目录saveDir
                    FileDownloader.createAndStart(url, courseDir, dataFile.getName());
                }

                @Override
                public void onDetectUrlFileExist(String url) {
                    // 继续下载，自动会断点续传（如果服务器无法支持断点续传将从头开始下载）
                    FileDownloader.start(url);
                }

                @Override
                public void onDetectUrlFileFailed(String url, DetectBigUrlFileFailReason failReason) {
                    // 探测一个网络文件失败了，具体查看failReason
                    DebugUtil.println(failReason.getMessage());
                }
            });
        }
    }

    private void createAndStartVideos(List<Video> videos, final String courseDir) {
        if (videos == null) {
            M.t(context, "没有视频！！！");
            return;
        }
        for (final Video video : videos) {
            FileDownloader.detect(video.getRealUrl(), new OnDetectBigUrlFileListener() {
                @Override
                public void onDetectNewDownloadFile(String url, String fileName, String saveDir, long fileSize) {
                    // 如果有必要，可以改变文件名称fileName和下载保存的目录saveDir
                    FileDownloader.createAndStart(url, courseDir, video.getTitle());
                }

                @Override
                public void onDetectUrlFileExist(String url) {
                    // 继续下载，自动会断点续传（如果服务器无法支持断点续传将从头开始下载）
                    FileDownloader.start(url);
                }

                @Override
                public void onDetectUrlFileFailed(String url, DetectBigUrlFileFailReason failReason) {
                    // 探测一个网络文件失败了，具体查看failReason
                    DebugUtil.println(failReason.getMessage());
                }
            });
        }
    }

}
