package com.wang.music_player.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.music_player.R;
import com.wang.music_player.activity.MusicPlayerActivity;
import com.wang.music_player.music.SearchMP3;
import com.wang.music_player.service.MusicPlayerService;


//第1界面：音乐界面
public class MusicPlayerFragment extends Fragment implements
        OnItemClickListener, OnSeekBarChangeListener, OnClickListener {
    private View view;
    private ArrayAdapter<String> adapter;
    private ListView musicList;
    private String[] musicName;
    private String[] musicPath;
    private String path;
    private TextView tvCurrentTime, tvTitle, tvTotalTime;
    private Button btnPrevious, btnPlayOrPause, btnNext, btnPlayMode;
    private SeekBar seekBar;
    private Intent serviceIntent = null;
    private int state;

    private MusicPlayerService mpService = null;
    private ServiceConnection connection;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.music_player_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
         * 千万不要在onCreate方法中启动，否则会无法得到mpService 对象（onServiceConnected这个方法的回调延迟了）
		 * 最好在onResume启动
		 */
        startMPService();
    }

    private void init() {
        tvTitle = ((TextView) view.findViewById(R.id.tv_title));
        path = (Environment.getExternalStorageDirectory().getPath() + "/handsome/music");
        SearchMP3 searchMP3 = new SearchMP3(path);
        musicName = searchMP3.getMusicName();
        musicPath = searchMP3.getMusicPath();
        if (musicName == null) {
            tvTitle.setText(path + "目录下搜索不到mp3文件");
            return;
        }
        btnPrevious = (Button) view.findViewById(R.id.btn_previous);
        btnPrevious.setOnClickListener(this);
        btnPlayOrPause = (Button) view.findViewById(R.id.btn_play_or_pause);
        btnPlayOrPause.setOnClickListener(this);
        btnNext = (Button) view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        btnPlayMode = (Button) view.findViewById(R.id.btn_play_mode);
        btnPlayMode.setOnClickListener(this);
        tvCurrentTime = ((TextView) view.findViewById(R.id.tv_current_time));
        tvTotalTime = ((TextView) view.findViewById(R.id.tv_total_time));
        musicList = ((ListView) view.findViewById(R.id.music_list));
        musicList.setOnItemClickListener(this);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, musicName);
        musicList.setAdapter(adapter);

        connection = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Toast.makeText(getContext(), "Sorry，音乐服务意外停止，请重新启动",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mpService = ((MusicPlayerService.MyBinder) binder).getMusicPlayerService();
                initMpService();
                ((MusicPlayerActivity) getActivity())
                        .setServiceConnection(connection);
                ((MusicPlayerActivity) getActivity())
                        .setServiceIntent(serviceIntent);
                ((MusicPlayerActivity) getActivity()).setMpService(mpService);
                mpService.recoverUI();
            }
        };

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_previous) {
            mpService.previous();
        } else if (v.getId() == R.id.btn_play_or_pause) {
            if (mpService.getState() == MusicPlayerService.STATE_PLAY) {
                mpService.pause();
            } else {
                mpService.play();
            }
        } else if (v.getId() == R.id.btn_next) {
            mpService.next();
        } else if (v.getId() == R.id.btn_play_mode) {

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (mpService == null) {
            startMPService();
        }
        mpService.play(position);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        if (mpService == null) {
            startMPService();
        }
        int totalMusicTime = mpService.getTotalMusicTime();
        int newMusicTime = (int) (totalMusicTime * progress * 0.01);
        mpService.setNewMusicTime(newMusicTime);

        tvCurrentTime.setText(mpService.transTime(newMusicTime));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mpService.getState() == MusicPlayerService.STATE_PLAY) {
            mpService.movePosition();
        }
    }

    private void startMPService() {
        // 若music文件夹下没有mp3文件，则musicName为空，不启动服务
        if (musicName == null) {
            return;
        }
        serviceIntent = new Intent(getActivity(), MusicPlayerService.class);
        getActivity().startService(serviceIntent);
        getActivity().bindService(serviceIntent, connection,
                Context.BIND_AUTO_CREATE);
        /*
         * 这里不能直接调用initMpService()，因为bindService方法有延迟，刚执行完不会马上
		 * 回调onServiceConnected方法，所以现在mpService仍为空
		 * 所以最好在onServiceConnected方法中执行getMusicPlayerService()
		 * 之后才调用initMpService()
		 */
    }

    private void initMpService() {
        mpService.setMusicName(musicName);
        mpService.setMusicPath(musicPath);
        mpService.setSeekBar(seekBar);
        mpService.setTvCurrentTime(tvCurrentTime);
        mpService.setTvTitle(tvTitle);
        mpService.setTvTotalTime(tvTotalTime);
    }

}