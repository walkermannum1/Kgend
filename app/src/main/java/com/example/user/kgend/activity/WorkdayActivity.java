package com.example.user.kgend.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.example.user.kgend.adapter.DbAdapter;
import com.example.user.kgend.PathRecord;
import com.example.user.kgend.R;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 2016/10/31.
 */
public class WorkdayActivity extends Activity implements LocationSource, AMapLocationListener {
    private AMap mAMap;
    private MapView mMapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mClientOption;
    private PolylineOptions mPolyOptions;
    private PathRecord record;
    private long mStartTime;
    private long mEndTime;
    private ToggleButton tbtn;
    private DbAdapter mDbAdapter;
    private ProgressDialog progDialog = null;
    private GoogleApiClient mClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.basemap_activity);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        init();
        initpolyline();
    }

    private void initpolyline() {
        mPolyOptions = new PolylineOptions();
        mPolyOptions.width(8f);
        mPolyOptions.color(Color.GREEN);
    }


    private void init() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            setUpMap();
        }
        tbtn = (ToggleButton) findViewById(R.id.locationbtn);
        tbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbtn.isChecked()) {
                    Log.i("标志:", "isChecked");
                    mAMap.clear(true);
                    if (record != null) {
                        record = null;
                    }
                    record = new PathRecord();
                    mStartTime = System.currentTimeMillis();
                    record.setDate(getFmDate(mStartTime));
                } else {
                    mEndTime = System.currentTimeMillis();
                    saveRecord(record.getPathline(), record.getDate());
                }
            }
        });
    }

    protected void saveRecord(List<AMapLocation> list, String time) {
        if (list != null && list.size() > 0) {
            mDbAdapter = new DbAdapter(this);
            mDbAdapter.open();
            String duration = getDuration();
            float distance = getDistance(list);
            String average = getAverage(distance);
            String pathlineString = getPathlineString(list);
            AMapLocation firstLocation = list.get(0);
            AMapLocation lastLocation = list.get(list.size() - 1);
            String startpoint = amapLocationToString(firstLocation);
            String endpoint = amapLocationToString(lastLocation);
            mDbAdapter.createRecord(String.valueOf(distance), duration, average, pathlineString, startpoint, endpoint, time);
            mDbAdapter.close();
        } else {
            Toast.makeText(WorkdayActivity.this, "Did not get any record!", Toast.LENGTH_LONG).show();
        }
    }

    private String getPathlineString(List<AMapLocation> list) {
        if (list == null || list.size() == 0) {
            return " ";
        }
        StringBuffer pathline = new StringBuffer();
        for (int i = 0; i < list.size() - 1; i++) {
            AMapLocation location = list.get(i);
            String locString = amapLocationToString(location);
            pathline.append(locString).append(";");
        }
        String pathLineString = pathline.toString();
        pathLineString = pathLineString.substring(0, pathLineString.length() - 1);
        return pathLineString;
    }

    private String amapLocationToString(AMapLocation location) {
        StringBuffer locString = new StringBuffer();
        locString.append(location.getLatitude()).append(",");
        locString.append(location.getLongitude()).append(",");
        locString.append(location.getTime()).append(",");
        locString.append(location.getProvider()).append(",");
        locString.append(location.getSpeed()).append(",");
        locString.append(location.getBearing());
        return locString.toString();
    }

    private String getAverage(float distance) {
        return String.valueOf(distance / (float)(mEndTime - mStartTime));
    }

    private float getDistance(List<AMapLocation> list) {
        float distance = 0;
        if (distance == 0 || list.size() == 0) {
            return distance;
        }
        for (int i = 0; i < list.size() - 1; i++) {
            AMapLocation firstpoint = list.get(i);
            AMapLocation secondpint = list.get(i+1);
            LatLng firstlatLng = new LatLng(firstpoint.getLatitude(), firstpoint.getLongitude());
            LatLng secondlatLng = new LatLng(secondpint.getLatitude(), secondpint.getLongitude());
            double betweenDis = AMapUtils.calculateLineDistance(firstlatLng, secondlatLng);
            distance = (float) (distance + betweenDis);
        }
        return distance;
    }

    private String getDuration() {
        return String.valueOf((mEndTime - mStartTime) / 1000f);
    }

    @SuppressLint("SimpleDateFormat")
    private String getFmDate(long time) {
        SimpleDateFormat formater = new SimpleDateFormat(
                "yyyy-MM-dd  HH:mm:ss ");
        Date curDate = new Date(time);
        String date = formater.format(curDate);
        return date;
    }


    private void setUpMap() {
        mAMap.setLocationSource(this);
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);
        mAMap.getUiSettings().setCompassEnabled(true);
        mAMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        mAMap.setMyLocationEnabled(true);
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        //LatLngBounds latLngBounds = new LatLngBounds();
        //mAMap.setMapStatusLimits();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        deactivate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation.getErrorCode() == 0) {
            mListener.onLocationChanged(aMapLocation);
            LatLng mylocation = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(mylocation));
            String Cityname = aMapLocation.getCity();
            if (tbtn.isChecked()) {
                record.addPoint(aMapLocation);
                mPolyOptions.add(mylocation);
                redrawlines();
            }
        } else {
            Log.e("ErrorCode", aMapLocation.getErrorCode() + aMapLocation.getErrorInfo());
        }
    }

    private void redrawlines() {
        if (mPolyOptions.getPoints().size() > 0) {
            mAMap.clear(true);
            mAMap.addPolyline(mPolyOptions);
        }
    }

    public void Record(View v) {
        Intent intent = new Intent(WorkdayActivity.this, RecordActivity.class);
        startActivity(intent);
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mClientOption = new AMapLocationClientOption();
            mLocationClient.setLocationListener(this);
            mClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationClient.setLocationOption(mClientOption);
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }
}
