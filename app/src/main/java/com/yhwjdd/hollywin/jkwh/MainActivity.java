package com.yhwjdd.hollywin.jkwh;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
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

import java.util.List;

public class MainActivity extends Activity {
//    public BDLocationListener myListener = new MyLocationListener();
//    public MapView mapView = null;
    public BaiduMap baiduMap = null;

    // 定位相關聲明
    public LocationClient locationClient = null;
    //自定義圖標
    BitmapDescriptor mCurrentMarker = null;
    boolean isFirstLoc = true;// 是否首次定位
    MapView mMapView = null;
//    private LocationClient mLocationClient = null;

//    private Button startLocation;
//    private MapView mapView;
//    private BaiduMap mBaiduMap;

    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null)
                return;

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
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        //获取地图控件引用
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
        option.setScanSpan(5000); // 設置發起定位請求的間隔時間為5000ms
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

