package com.example.user.kgend.activity;

import android.app.Activity;
import android.content.DialogInterface;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.trace.TraceListener;

import java.util.List;

/**
 * Created by Guang on 2016/11/20.
 */

public class ShowRecord extends Activity implements AMap.OnMapLoadedListener, TraceListener, DialogInterface.OnClickListener{

    private MapView mMapView;
    private AMap aMap;
    private Polyline mOriginPolyline, mProcedPolyline;
    private int mRecordItemId;
    private List<LatLng> mOriginLatLng;
    private List<LatLng> mProcedLatLng;

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onRequestFailed(int i, String s) {

    }

    @Override
    public void onTraceProcessing(int i, int i1, List<LatLng> list) {

    }

    @Override
    public void onFinished(int i, List<LatLng> list, int i1, int i2) {

    }
}
