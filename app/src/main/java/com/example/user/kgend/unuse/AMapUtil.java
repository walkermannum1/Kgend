package com.example.user.kgend.unuse;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;

import java.text.DecimalFormat;
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

    public static String getFriendlyTime(int second) {
        if (second > 3600) {
            int hour = second / 3600;
            int minute = (second % 3600) / 60;
            return hour + "H" + minute + "M";
        }
        if (second > 60) {
            int minute = second / 60;
            return minute + "M";
        }
        return second + "s";
    }

    public static String getFriendlyLength(int lenMeter) {
        if (lenMeter > 10000) {
            int dis = lenMeter / 1000;
            return dis + "km";
        }
        if (lenMeter > 1000) {
            float dis = (float) lenMeter / 1000;
            DecimalFormat fnum = new DecimalFormat("##0.0");
            String dstr = fnum.format(dis);
            return dstr + "km";
        }
        if (lenMeter > 100) {
            int dis = lenMeter / 50 * 50;
            return dis + "m";
        }

        int dis = lenMeter / 10 * 10;
        if (dis == 0) {
            dis = 10;
        }
        return dis + "s";
    }
}
