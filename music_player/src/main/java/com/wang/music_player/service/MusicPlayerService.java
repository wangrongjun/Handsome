package com.wang.music_player.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wang.music_player.R;
import com.wang.music_player.music.MusicPlayer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MusicPlayerService extends Service implements Runnable {
    private MusicPlayer musicPlayer;
    private String[] musicName;
    private String[] musicPath;
    private TextView tvCurrentTime, tvTitle, tvTotalTime;
    private SeekBar seekBar;
    private Handler handler;
    private int seekBarProgress;
    private Intent intent;
    private static final int NODIFICATION_ID = 111;

    public static final int STATE_PAUSE = 111;
    public static final int STATE_PLAY = 222;
    public static final int STATE_UNPREPARE = 333;
    // 歌曲播放状态
    private int state;
    // 当前歌曲编号，从0到musicName.length-1
    private int currentMusicId;
    // 拖动进度条后更新的音乐播放时间
    private int newMusicTime;
    // 当前音乐总时间
    private int totalMusicTime;
    // 显示当前播放时间和总时间的线程
    private Thread thread;
    private boolean threadCanStop;

    public class MyBinder extends Binder {
        public MusicPlayerService getMusicPlayerService() {
            return MusicPlayerService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public IBinder onBind(Intent intent) {
        musicPlayer = new MusicPlayer();
        this.intent = intent;
        return new MyBinder();
    }

    private void sendNotification() {
        Builder builder = new Builder(this);
        builder.setSmallIcon(R.mipmap.logo_music_player);
        builder.setContentTitle("欢迎使用英俊音乐盒");
        builder.setContentText(musicName[currentMusicId]);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NODIFICATION_ID, notification);
    }

    private void cancelNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(NODIFICATION_ID);
    }

    private void init() {
        musicPlayer = new MusicPlayer();
        setState(STATE_UNPREPARE);
        currentMusicId = 0;
        thread = new Thread(this);
        threadCanStop = false;

        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                int currentMusicTime = msg.what;
                getTvCurrentTime().setText(transTime(currentMusicTime));
                double progress = ((double) currentMusicTime / getTotalMusicTime()) * 100;
                seekBarProgress = (int) progress;
                getSeekBar().setProgress(seekBarProgress);
            }

            ;
        };
    }

    public void previous() {
        musicPlayer.stop();
        currentMusicId = (currentMusicId + getMusicPath().length - 1)
                % getMusicPath().length;
        startPlay();
    }

    public void play() {
        if (getState() == STATE_UNPREPARE) {
            startPlay();
            setState(STATE_PLAY);
        } else if (getState() == STATE_PAUSE) {
            musicPlayer.play();
            setState(STATE_PLAY);
        }
    }

    public void play(int position) {
        if (musicPlayer.isPlaying()) {
            musicPlayer.stop();
        }
        currentMusicId = position;
        startPlay();
        setState(STATE_PLAY);
    }

    public void pause() {
        if (getState() == STATE_PLAY) {
            musicPlayer.pause();
            setState(STATE_PAUSE);
        }
    }

    public void next() {
        musicPlayer.stop();
        currentMusicId = (currentMusicId + getMusicPath().length + 1)
                % getMusicPath().length;
        startPlay();
    }

    public void movePosition() {
        if (state == STATE_PLAY) {
            musicPlayer.movePosition(getNewMusicTime());
        } else if (state == STATE_PAUSE) {

        }
    }

    @Override
    public void onDestroy() {
        threadCanStop = true;
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
        cancelNotification();
        Log.i("info", "onDestroy");
        super.onDestroy();
    }

    // 把毫秒转换为00：00
    public String transTime(int milliseconds) {
        Date date = new Date(milliseconds);
        SimpleDateFormat sdf = new SimpleDateFormat(/* yyyy-MM-dd HH: */"mm:ss");
        return sdf.format(date);
    }

    private void startPlay() {
        musicPlayer.prepare(getMusicPath()[currentMusicId],
                getMusicName()[currentMusicId]);
        musicPlayer.play();
        getTvTitle().setText(getMusicName()[currentMusicId]);
        setTotalMusicTime(musicPlayer.getTotalTime());
        getTvTotalTime().setText(transTime(getTotalMusicTime()));
        sendNotification();
        setState(STATE_PLAY);
        if (!thread.isAlive()) {
            thread.start();
        }
    }

    // Activity重启时恢复音乐播放界面UI控件的状态
    public void recoverUI() {
        getTvTitle().setText(getMusicName()[currentMusicId]);
        getTvTotalTime().setText(transTime(getTotalMusicTime()));
        seekBar.setProgress(seekBarProgress);
    }

    @Override
    public void run() {
        int currentTime = 0;
        while (threadCanStop == false) {
            currentTime = musicPlayer.getCurrentTime();
            handler.sendEmptyMessage(currentTime);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("info", "thread");
        }
    }

    public int getNewMusicTime() {
        return newMusicTime;
    }

    public void setNewMusicTime(int newMusicTime) {
        this.newMusicTime = newMusicTime;
    }

    public int getTotalMusicTime() {
        return totalMusicTime;
    }

    public void setTotalMusicTime(int totalMusicTime) {
        this.totalMusicTime = totalMusicTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String[] getMusicName() {
        return musicName;
    }

    public void setMusicName(String[] musicName) {
        this.musicName = musicName;
    }

    public String[] getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String[] musicPath) {
        this.musicPath = musicPath;
    }

    public TextView getTvCurrentTime() {
        return tvCurrentTime;
    }

    public void setTvCurrentTime(TextView tvCurrentTime) {
        this.tvCurrentTime = tvCurrentTime;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public TextView getTvTotalTime() {
        return tvTotalTime;
    }

    public void setTvTotalTime(TextView tvTotalTime) {
        this.tvTotalTime = tvTotalTime;
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

}
