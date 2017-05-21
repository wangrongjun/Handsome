package com.wang.wifi_manager.data;

import com.wang.java_util.StreamUtil;
import com.wang.java_util.TextUtil;
import com.wang.wifi_manager.bean.WifiInfo;
import com.wang.wifi_manager.exception.NotRootException;
import com.wang.wifi_manager.exception.ParseWifiInfoException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * by wangrongjun on 2017/2/28.
 * <p/>
 * http://blog.csdn.net/jdsjlzx/article/details/40857039   Android 用代码查看本机保存的Wifi密码
 * <p/>
 * 1、通过Runtime.getRuntime().exec("su")获取root权限。
 * 2、通过process.getOutputStream()和process.getInputStream()获取终端的输入流和输出流。
 * 3、通过dataOutputStream.writeBytes("cat /data/misc/wifi/*.conf\n")往终端中输入命令。
 * 注意，这里必须要有\n作为换行，否则会与后一个exit命令作为一个命令，最终导致命令执行失败，无法得到结果。
 * 4、通过dataInputStream获取命令执行结果，并以UTF-8的编码转换成字符串。
 * 5、使用正则表达式过滤出wifi的用户名和密码。
 * <p/>
 * <p/>
 * <p/>
 * http://blog.csdn.net/jiangwei0910410003/article/details/41800409
 * Android中可以做的两件坏事---破解锁屏密码和获取Wifi密码
 * <p/>
 * 打开adb：
 * =>adb shell
 * =>su
 * =>cd data/misc/wifi
 * =>cat wpa_supplicant.conf
 */
public class WifiDataImpl implements IWifiData {

    @Override
    public List<WifiInfo> getWifiInfoList() throws Exception {

        Process process;
        InputStream is = null;
        OutputStream os = null;
        String result = "";

        try {
            try {
                process = Runtime.getRuntime().exec("su");
            } catch (IOException e) {
                e.printStackTrace();
                throw new NotRootException();
            }

            os = process.getOutputStream();
            os.write("cat /data/misc/wifi/wpa_supplicant.conf\n".getBytes());
            os.write("exit\n".getBytes());
            os.flush();

            is = process.getInputStream();
            result = StreamUtil.readInputStream(is, null);

            process.waitFor();

        } finally {
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
//            if (process != null) {
//                process.destroy();
//            }
        }

        if (TextUtil.isEmpty(result)) {
            return new ArrayList<>();
        } else {
            try {
                return parseWifiText(result);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ParseWifiInfoException(result);
            }
        }

    }

    public static List<WifiInfo> parseWifiText(String wifiText) throws Exception {
/*  
        network={
                ssid="SM-E7000"
                psk="87654321"
                key_mgmt=WPA-PSK
                priority=25
        }
        network={
                ssid="三民主义"
                psk="21436587"
                key_mgmt=WPA-PSK
                priority=27
        }
*/
        List<WifiInfo> wifiInfoList = new ArrayList<>();
        Pattern pattern = Pattern.compile("network=\\{([^\\}]+)\\}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(wifiText);
        while (matcher.find()) {
            WifiInfo wifiInfo = new WifiInfo();
            String s = matcher.group();

            //解析ssid，wifi名称
            Pattern ssidPattern = Pattern.compile("ssid=\"([^\"]+)\"");
            Matcher ssidMatcher = ssidPattern.matcher(s);
            if (ssidMatcher.find()) {
                String ssid = ssidMatcher.group(1);
                wifiInfo.setSsid(ssid);
            }

            //解析psk，wifi密码
            Pattern pskPattern = Pattern.compile("psk=\"([^\"]+)\"");
            Matcher pskMatcher = pskPattern.matcher(s);
            String psk = null;
            if (pskMatcher.find()) {
                psk = pskMatcher.group(1);
            }
            if (TextUtil.isEmpty(psk)) {
                psk = "无";
            }
            wifiInfo.setPsk(psk);

            //解析key_mgmt，加密方式
            Pattern keyMgmtPattern = Pattern.compile("key_mgmt=([^\n]+)\n");
            Matcher keyMgmtMatcher = keyMgmtPattern.matcher(s);
            if (keyMgmtMatcher.find()) {
                String keyMgmt = keyMgmtMatcher.group(1);
                wifiInfo.setKeyMgmt(keyMgmt);
            }

            //解析priority，优先级
            Pattern priorityPattern = Pattern.compile("priority=([\\d]+)");
            Matcher priorityMatcher = priorityPattern.matcher(s);
            if (priorityMatcher.find()) {
                String priority = priorityMatcher.group(1);
                wifiInfo.setPriority(Integer.parseInt(priority));
            }

            wifiInfoList.add(wifiInfo);
        }

        return wifiInfoList;
    }

}
