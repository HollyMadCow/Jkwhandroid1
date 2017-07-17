package com.yhwjdd.hollywin.jkwh;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
import com.baidu.location.Poi;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.io.IOException;
import java.util.List;
import com.alibaba.fastjson.*;

import okhttp3.*;





public class MainActivity extends AppCompatActivity {
    public CustomApplication app;
    public BaiduMap baiduMap = null;
    private Button bt;
    private Button bs;
    private Button btnsavecurrent;
    private EditText ed;
    BDLocation shardedlocal;
    OkHttpClient okHttpClient = OkHttpUtils.getInstance().getOkHttpClient();



    // 定位相關聲明
    public LocationClient locationClient = null;
    //自定義圖標
    BitmapDescriptor mCurrentMarker = null;
    boolean isFirstLoc = true;// 是否首次定位
    MapView mMapView = null;

    private static int LOCATION_COUTNS = 0;

    public BDLocationListener myListener = new BDLocationListener() {

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }


        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null)
                return;
            shardedlocal = location;
            app.setShardedlocal(location);
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此處設置開發者獲取到的方向信息，順時針0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);    //設置定位數據


            if (isFirstLoc) {
                isFirstLoc = false;


                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 16);    //設置地圖中心點以及縮放級別
                baiduMap.animateMapStatus(u);
            }

            StringBuffer sb = new StringBuffer(256);
            sb.append("Time : ");
            sb.append(location.getTime());
            sb.append("\nError code : ");
            sb.append(location.getLocType());
            sb.append("\nLatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nLontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nRadius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){
                sb.append("\nSpeed : ");
                sb.append(location.getSpeed());
                sb.append("\nSatellite : ");
                sb.append(location.getSatelliteNumber());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
                sb.append("\nAddress : ");
                sb.append(location.getAddrStr());
            }
            LOCATION_COUTNS ++;
            sb.append("\n检查位置更新次数：");
            sb.append(String.valueOf(LOCATION_COUTNS));

            Log.v("!!!!",sb.toString());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        app = (CustomApplication) getApplication();
//        Log.i("FirstActivity", "初始值=====" + app.getValue()); // 获取进程中的全局变量值，看是否是初始化值
//        app.setValue("Harvey Ren"); // 重新设置值
//        Log.i("FirstActivity", "修改后=====" + app.getValue());
//        OkHttpClient okHttpClient = OkHttpUtils.getInstance().getOkHttpClient();



        //获取地图控件引用
        bt = (Button)findViewById(R.id.btnback) ;
        bs = (Button)findViewById(R.id.btnsearch);
        btnsavecurrent = (Button)findViewById(R.id.btnsavecurrentpoint);
        btnsavecurrent.setOnClickListener(new SaveCurrentPoint());
        bt.setOnClickListener(new MyListener());
        bs.setOnClickListener(new MySearch());
        ed = (EditText)findViewById(R.id.editText);
        ed.setOnEditorActionListener(new editactivate());
       // ed.setOnClickListener(new clickactivate());
        mMapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();
        //開啟定位圖層
        baiduMap.setMyLocationEnabled(true);

        locationClient = new LocationClient(getApplicationContext()); // 實例化LocationClient類
        locationClient.registerLocationListener(myListener); // 注冊監聽函數
        this.setLocationOption();	//設置定位參數
        locationClient.start(); // 開始定位
    }



    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打開GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 設置定位模式
        option.setCoorType("bd09ll"); // 返回的定位結果是百度經緯度,默認值gcj02
        option.setScanSpan(10000); // 設置發起定位請求的間隔時間為5000ms
        option.setIsNeedAddress(true); // 返回的定位結果包含地址信息
        option.setNeedDeviceDirect(true); // 返回的定位結果包含手機機頭的方向

        locationClient.setLocOption(option);

//
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//
//        option.setCoorType("bd09ll");
//        //可选，默认gcj02，设置返回的定位结果坐标系
//
//        int span=5000;
//        option.setScanSpan(span);
//        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//
//        option.setIsNeedAddress(true);
//        //可选，设置是否需要地址信息，默认不需要
//
//        option.setOpenGps(true);
//        //可选，默认false,设置是否使用gps
//
//        option.setLocationNotify(true);
//        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
//
//        option.setIsNeedLocationDescribe(true);
//        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//
//        option.setIsNeedLocationPoiList(true);
//        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//
//        option.setIgnoreKillProcess(false);
//        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//
//        option.SetIgnoreCacheException(false);
//        //可选，默认false，设置是否收集CRASH信息，默认收集
//
//        option.setEnableSimulateGps(true);
//        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
//
//        locationClient.setLocOption(option);
    }


    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            LatLng ll = new LatLng(shardedlocal.getLatitude(),
                    shardedlocal.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll,16);    //設置地圖中心點以及縮放級別
            baiduMap.animateMapStatus(u);
        }
    }
    class SaveCurrentPoint implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            //setClass的第一个参数为Context对象（Activity为其子类），第二个参数为要启动的Activity
            intent.setClass(MainActivity.this,SavePointActivity.class);
            startActivity(intent);
        }
    }
    class MySearch implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //EditText editText = (EditText)findViewById(R.id.editText);
            if (!TextUtils.isEmpty(ed.getText())){
                String domainip = app.getdomainip();
                String s = domainip + "getpointinfo/"+ed.getText().toString();
                Request request = new Request.Builder().
                        get().
                        url(s).
                        build();

                request = addBasicAuthHeaders(request);

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String string = response.body().string();
//                        Log.i(TAG, "onResponse: "+string);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

//                            java.lang.reflect.Type type = new TypeToken<PointBean>() {}.getType();
                                PointBean point = JSON.parseObject(string,PointBean.class);
                                //Log.i("TTEST",string);
                                if (point.getState().equals("ok"))
                                {
                                    //Log.d("TAG111","response"+ point.getInfo().getArea());
                                    Log.d("TAG111","response:"+ point.getMsg());
                                }

                                if (point.getState().equals("fail"))
                                {
                                    //Log.d("TAG111","response"+ point.getInfo().getArea());
                                    Log.d("TAG111","response:"+ point.getMsg());
                                }

                            }
                        });
                    }
                });
            }else {
                alarm();
            }

//
        }
    }

    class editactivate implements TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            //当actionId == XX_SEND 或者 XX_DONE时都触发
            //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
            //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
               // EditText editText = (EditText)findViewById(R.id.editText);
                String domainip = app.getdomainip();
                String s = domainip + "getpointinfo/"+ed.getText().toString();
                Request request = new Request.Builder().
                        get().
                        url(s).
                        build();

                request = addBasicAuthHeaders(request);

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String string = response.body().string();
//                        Log.i(TAG, "onResponse: "+string);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

//                            java.lang.reflect.Type type = new TypeToken<PointBean>() {}.getType();
                                PointBean point = JSON.parseObject(string,PointBean.class);
                                //Log.i("TTEST",string);
                                if (point.getState().equals("ok"))
                                {
                                    //Log.d("TAG111","response"+ point.getInfo().getArea());
                                    Log.d("TAG111","response:"+ point.getMsg());
                                }
                                if (point.getState().equals("fail"))
                                {
                                    //Log.d("TAG111","response"+ point.getInfo().getArea());
                                    Log.d("TAG111","response:"+ point.getMsg());
                                }

                            }
                        });
                    }
                });
            }
            return false;
        }
    }

    private Request addBasicAuthHeaders(Request request) {
        final String login = "zlwh2y";
        final String password = "zlw2551";
        String credential = Credentials.basic(login, password);
        return request.newBuilder().header("Authorization", credential).build();
    }

    protected  void alarm(){
        Dialog alertDialog = new AlertDialog.Builder(this).
                setTitle("无点位编号！").
                setMessage("请输入点位编号。").
                setIcon(R.drawable.timg).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                }).
                create();
        alertDialog.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

}

