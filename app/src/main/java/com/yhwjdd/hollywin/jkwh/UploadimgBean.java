package com.yhwjdd.hollywin.jkwh;

/**
 * Created by HollyWin on 2017/7/3.
 */

public class UploadimgBean {

    /**
     * filescount : 4
     * info : {"imgfile1":"20170703001949971784.jpg","imgfile2":"20170703001949972784.jpg","imgfile3":"20170703001949972784.png","imgfile4":"20170703001949973785.png"}
     * msg : 上传成功！
     * state : ok
     */

    private int filescount;
    private InfoBean info;
    private String msg;
    private String state;

    public int getFilescount() {
        return filescount;
    }

    public void setFilescount(int filescount) {
        this.filescount = filescount;
    }

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
    }
}
