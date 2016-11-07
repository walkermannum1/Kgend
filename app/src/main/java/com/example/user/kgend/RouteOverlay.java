package com.example.user.kgend;

import android.content.Context;
import android.graphics.Bitmap;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import static com.amap.api.services.nearby.NearbySearch.destroy;

/**
 * Created by user on 2016/11/1.
 */

public class RouteOverlay {
    protected List<Marker> stationMakers = new ArrayList<Marker>();
    protected List<Polyline> allPolylines = new ArrayList<Polyline>();
    protected Marker startMaker;
    protected Marker endMaker;
    protected AMap mAMap;
    protected LatLng startPoint;
    protected LatLng endPoint;
    private Context mContext;
    private Bitmap startBit, endBit;

    public RouteOverlay(Context context) {
        mContext = context;
    }

    public void removeFromMap() {
        if (startMaker != null) {
            startMaker.remove();
        }
        if (endMaker != null) {
            endMaker.remove();
        }
        for (Marker maker : stationMakers) {
            maker.remove();
        }
        for (Polyline line : allPolylines) {
            line.remove();
        }
        destroyBit();
    }

    private void destroyBit() {
        if (startBit != null) {
            startBit.recycle();
            startBit = null;
        }
        if (endBit != null) {
            endBit.recycle();
            endBit = null;
        }
    }

    public void zoomToSpan() {
        if (startPoint != null) {
            if (mAMap == null)
                return;

                try {
                    LatLngBounds bounds = getLatlngBounds();
                    mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                } catch (Throwable e) {
                    e.printStackTrace();
                }

        }
    }

    protected LatLngBounds getLatlngBounds() {
        LatLngBounds.Builder b = LatLngBounds.builder();
        b.include(new LatLng(startPoint.latitude, startPoint.longitude));
        b.include(new LatLng(endPoint.latitude, endPoint.latitude));
        return b.build();
    }

    protected void addPolyLine(PolylineOptions options) {
        if (options == null) {
            return;
        }
        Polyline polyline = mAMap.addPolyline(options);
        if (polyline != null) {
            allPolylines.add(polyline);
        }
    }
}
