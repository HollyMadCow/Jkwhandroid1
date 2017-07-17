package com.yhwjdd.hollywin.jkwh;

import android.app.Application;

import com.baidu.location.BDLocation;

import okhttp3.OkHttpClient;

/**
 * Created by HollyWin on 2017/5/17.
 */

public class CustomApplication extends Application {

    //private String domain = "http://192.168.2.221/";
    private String domainAddr = "https://api.yhgaj.cc/v1/";
    //private String version = "v1/";


    private float lo;
    private float la;
    BDLocation shardedlocal;

    public void onCreate(){
        super.onCreate();
        OkHttpClient okHttpClient = OkHttpUtils.getInstance().getOkHttpClient();
    }

    public void setValue(float lo,float la) {
        this.lo = lo;
        this.la = la;
    }

    public float getlo() {
        return lo;
    }
    public float getla() {
        return la;
    }

    public String getdomainip() {
        return domainAddr;
    }
    public void setShardedlocal(BDLocation location){
        this.shardedlocal = location;
    }
    public BDLocation getlocation() {
        return shardedlocal;
    }


}
