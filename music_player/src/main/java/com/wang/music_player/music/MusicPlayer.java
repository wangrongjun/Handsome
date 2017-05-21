package com.wang.music_player.music;

import android.media.MediaPlayer;
import android.util.Log;

public class MusicPlayer {
    private MediaPlayer mediaPlayer = null;
    private int pausePosition;
    private int totalTime;
    private boolean isPrepare;
    private String musicName;

    public MusicPlayer() {
        mediaPlayer = new MediaPlayer();
        isPrepare = false;
        musicName = null;
    }

    public void prepare(String musicPath, String musicName) {
        try {
            isPrepare = true;
            setPausePosition(0);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(musicPath);
            mediaPlayer.prepare();
            totalTime = mediaPlayer.getDuration();
            mediaPlayer.setLooping(true);
            this.musicName = musicName;
        } catch (Exception localException) {
            Log.i("error", "MusicPlayerError");
        }
    }

    public void play() {
        mediaPlayer.seekTo(getPausePosition());
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
        setPausePosition(mediaPlayer.getCurrentPosition());
    }

    public void movePosition(int position) {
        mediaPlayer.pause();
        mediaPlayer.seekTo(position);
        mediaPlayer.start();
    }

    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        setPausePosition(0);
        isPrepare = false;
    }

    public void finish() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        setPausePosition(0);
        isPrepare = false;
    }

    public int getCurrentTime() {
        if (isPrepare) {
            return mediaPlayer.getCurrentPosition();
        } else
            return 0;
    }

    public int getTotalTime() {
        if (isPrepare) {
            return mediaPlayer.getDuration();
        } else {
            return 0;
        }
    }

    public String getMusicName() {
        return musicName;
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public int getPausePosition() {
        return pausePosition;
    }

    public void setPausePosition(int pausePosition) {
        this.pausePosition = pausePosition;
    }

}