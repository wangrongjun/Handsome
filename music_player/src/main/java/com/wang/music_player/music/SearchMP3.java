package com.wang.music_player.music;

import android.util.Log;

import java.io.File;

public class SearchMP3 {
    private String[] musicName;
    private String[] musicPath;
    private String portfix = ".mp3";

    public SearchMP3(String searchPath) {
        File file = new File(searchPath);
        if (!file.exists()) {
            file.mkdirs();
            return;
        }
        if (!file.isDirectory()) {
            return;
        }

        File sonFiles[] = file.listFiles();
        int len = 0;
        // 获取文件中后缀为mp3的文件数目，确定musicName和musicPath的长度len
        for (int i = 0; i < sonFiles.length; i++) {
            if (sonFiles[i].getName().endsWith(portfix)) {
                len++;
            }
        }
        if (len == 0) {
            Log.i("info", "SearchMP3 Error3");
            return;
        }
        musicName = new String[len];
        musicPath = new String[len];
        for (int i = 0, j = 0; i < sonFiles.length && j < len; i++) {
            if (sonFiles[i].getName().endsWith(".mp3")) {
                musicName[j] = sonFiles[i].getName();
                musicPath[j] = sonFiles[i].getPath();
                j++;
            }
        }
    }

    public String[] getMusicName() {
        return musicName;
    }

    public String[] getMusicPath() {
        return musicPath;
    }
}
