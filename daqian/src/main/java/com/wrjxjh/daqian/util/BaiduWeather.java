package com.wrjxjh.daqian.util;

import android.util.Log;

import com.wrjxjh.daqian.constant.C;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class BaiduWeather {
    private static final String baiduWeatherUrl =
            "http://api.map.baidu.com/telematics/v3/weather?output=json&ak=6tYzTvGZSOpYB5Oc2YGGOKt8&location=";
    public int errorCode;
    public String status;
    public String currentCity;
    public String currentDate;
    public String[] date;
    public String[] dayPictureUrl;
    public String[] nightPictureUrl;
    public String[] weather;
    public String[] wind;
    public String[] temperature;
    public String[] tipTipt;
    public String[] tipZs;
    public String[] tipDes;

    public String pm25;

    private boolean isEmpty = true;

    public String json = null;

    public void queryWeather(String city) {
        try {
            city = URLEncoder.encode(city, "utf-8");
            URL url = new URL(baiduWeatherUrl + city);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(C.CONNECTION_TIMEOUT);
            conn.setReadTimeout(C.READ_TIMEOUT);

            InputStream is = conn.getInputStream();

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }

            parseJSON(builder.toString());
            br.close();
            isr.close();

        } catch (Exception e) {
            Log.i("info", "queryWeather:error");
        }
    }

    private void parseJSON(String json) {
        this.json = json;

        try {
            JSONObject root = new JSONObject(json);

            errorCode = root.getInt("error");
            Log.i("info", "errorCode: " + errorCode);

            status = root.getString("status");
            Log.i("info", "status: " + status);

            currentDate = root.getString("date");
            Log.i("info", "date: " + currentDate);

            // ----------------------------------------------------------

            JSONObject result = root.getJSONArray("results").getJSONObject(0);

            currentCity = result.getString("currentCity");
            // M.i(currentCity);

            pm25 = result.getString("pm25");
            // M.i(pm25);

            JSONArray weatherData = result.getJSONArray("weather_data");
            int totalDays = weatherData.length();

            date = new String[totalDays];
            dayPictureUrl = new String[totalDays];
            nightPictureUrl = new String[totalDays];
            weather = new String[totalDays];
            wind = new String[totalDays];
            temperature = new String[totalDays];

            for (int i = 0; i < totalDays; i++) {
                JSONObject eachDayData = weatherData.getJSONObject(i);
                // M.i("-----------------------------------");
                date[i] = eachDayData.getString("date");
                // M.i(date[i]);
                dayPictureUrl[i] = eachDayData.getString("dayPictureUrl");
                // M.i(dayPictureUrl[i]);
                nightPictureUrl[i] = eachDayData.getString("nightPictureUrl");
                // M.i(nightPictureUrl[i]);
                weather[i] = eachDayData.getString("weather");
                // M.i(weather[i]);
                wind[i] = eachDayData.getString("wind");
                // M.i(wind[i]);
                temperature[i] = eachDayData.getString("temperature");
                // M.i(temperature[i]);
            }

            // -----------------------------------------------------

            JSONArray tipArray = result.getJSONArray("index");
            int tipNumber = tipArray.length();
            this.tipTipt = new String[tipNumber];
            this.tipZs = new String[tipNumber];
            this.tipDes = new String[tipNumber];

            for (int i = 0; i < tipNumber; i++) {
                JSONObject activity = tipArray.getJSONObject(i);
                // activity.getString("title");
                tipTipt[i] = activity.getString("tipt");
                tipZs[i] = activity.getString("zs");
                tipDes[i] = activity.getString("des");

                isEmpty = false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            isEmpty = true;
        }
    }

    public boolean isEmpty() {
        return isEmpty;
    }

}
