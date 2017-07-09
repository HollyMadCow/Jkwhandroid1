package com.yhwjdd.hollywin.jkwh;

/**
 * Created by HollyWin on 2017/6/29.
 */

public class PointBean {

    /**
     * info : {"area":"城关","batch":"3期","buildby":"海康","kindof":"MAC","la":28.435345,"lo":121.234238,"poingid":11111,"pointname":"HG06070什么点位在这里"}
     * msg : 成功
     * state : ok
     */

    private InfoBean info;
    private String msg;
    private String state;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class InfoBean {
        /**
         * area : 城关
         * batch : 3期
         * buildby : 海康
         * kindof : MAC
         * la : 28.435345
         * lo : 121.234238
         * poingid : 11111
         * pointname : HG06070什么点位在这里
         */

        private String area;
        private String batch;
        private String buildby;
        private String kindof;
        private double la;
        private double lo;
        private int poingid;
        private String pointname;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getBatch() {
            return batch;
        }

        public void setBatch(String batch) {
            this.batch = batch;
        }

        public String getBuildby() {
            return buildby;
        }

        public void setBuildby(String buildby) {
            this.buildby = buildby;
        }

        public String getKindof() {
            return kindof;
        }

        public void setKindof(String kindof) {
            this.kindof = kindof;
        }

        public double getLa() {
            return la;
        }

        public void setLa(double la) {
            this.la = la;
        }

        public double getLo() {
            return lo;
        }

        public void setLo(double lo) {
            this.lo = lo;
        }

        public int getPoingid() {
            return poingid;
        }

        public void setPoingid(int poingid) {
            this.poingid = poingid;
        }

        public String getPointname() {
            return pointname;
        }

        public void setPointname(String pointname) {
            this.pointname = pointname;
        }
    }
}
