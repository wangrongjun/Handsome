package com.wang.youdao_dictionary;

import com.wang.java_util.GsonUtil;

import java.util.List;

/**
 * by wangrongjun on 2017/3/2.
 */
public class Youdao {

    public static String getUrl(String word) {
        return "http://fanyi.youdao.com/openapi.do?" +
                "keyfrom=WangRongJun&key=1609847882&type=data&doctype=json&version=1.1&q=" + word;
    }

    public static String parseJson(String json) {
        String result = "";
        try {
            YoudaoBean youdaoBean = GsonUtil.fromJson(json, YoudaoBean.class);

            result += "直接翻译：" + youdaoBean.getTranslation() + "\n";

            result += "\n\n基本释义：\n\n";
            List<String> explains = youdaoBean.getBasic().getExplains();
            for (String explain : explains) {
                result += explain + "\n";
            }

            result += "\n\n网络释义：\n\n";
            List<YoudaoBean.WebBean> webBeanList = youdaoBean.getWeb();
            for (YoudaoBean.WebBean webBean : webBeanList) {
                result += webBean.getKey() + ":\n";
                List<String> list = webBean.getValue();
                for (String s : list) {
                    result += s + "，";
                }
                result += "\n\n";
            }

        } catch (Exception e) {
            return e.toString();
        }

        return result;
    }

}
