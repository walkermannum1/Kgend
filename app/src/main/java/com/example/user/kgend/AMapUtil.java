package com.example.user.kgend;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/11/7.
 */
public class AMapUtil {
    public static LatLng convertToLatLng(LatLonPoint latlon) {
        return new LatLng(latlon.getLatitude(), latlon.getLongitude());
    }

    public static ArrayList<LatLng> coverArrayList(List<LatLonPoint> shapes) {
        ArrayList<LatLng> lineShapes = new ArrayList<LatLng>();
        for (LatLonPoint point : shapes) {
            LatLng latLngTemp = AMapUtil.convertToLatLng(point);
            lineShapes.add(latLngTemp);
        }
        return lineShapes;
    }
}
