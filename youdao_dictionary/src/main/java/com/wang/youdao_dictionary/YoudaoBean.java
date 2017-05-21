package com.wang.youdao_dictionary;

import java.util.List;

/**
 * by wangrongjun on 2017/3/2.
 */
public class YoudaoBean {

    /**
     * explains : ["n. 汽车；车厢","n. (Car)人名；(土)贾尔；(法、西)卡尔；(塞)察尔"]
     * phonetic : kɑː
     * uk-phonetic : kɑː
     * us-phonetic : kɑr
     */

    private BasicBean basic;
    /**
     * basic : {"explains":["n. 汽车；车厢","n. (Car)人名；(土)贾尔；(法、西)卡尔；(塞)察尔"],"phonetic":"kɑː","uk-phonetic":"kɑː","us-phonetic":"kɑr"}
     * errorCode : 0
     * query : car
     * translation : ["车"]
     * web : [{"key":"Car","value":["汽车","小汽车","轿车"]},{"key":"concept car","value":["概念车","概念车","概念汽车"]},{"key":"bumper car","value":["碰碰车","碰撞用汽车","碰碰汽车"]}]
     */

    private int errorCode;
    private String query;
    private List<String> translation;
    /**
     * key : Car
     * value : ["汽车","小汽车","轿车"]
     */

    private List<WebBean> web;

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public List<WebBean> getWeb() {
        return web;
    }

    public void setWeb(List<WebBean> web) {
        this.web = web;
    }

    public static class BasicBean {
        private String phonetic;
        private List<String> explains;

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }
    }

    public static class WebBean {
        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}
