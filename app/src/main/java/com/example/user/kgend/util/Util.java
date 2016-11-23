package com.example.user.kgend.util;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.amap.api.trace.TraceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/11/9.
 */
public class Util {
    public static List<TraceLocation> parceTraceLocationList(List<AMapLocation> list) {
        List<TraceLocation> traceList = new ArrayList<TraceLocation>();
        if (list == null) {
            return traceList;
        }
        for (int i = 0; i < list.size(); i++) {
            TraceLocation location = new TraceLocation();
            AMapLocation aMapLocation = list.get(i);
            location.setBearing(aMapLocation.getBearing());
            location.setLatitude(aMapLocation.getLatitude());
            location.setLongitude(aMapLocation.getLongitude());
            location.setSpeed(aMapLocation.getSpeed());
            location.setTime(aMapLocation.getTime());
            traceList.add(location);
        }
        return traceList;
    }

    public static List<LatLng> parseLatLngList(List<AMapLocation> list) {
        List<LatLng> traceList = new ArrayList<LatLng>();
        if (list == null) {
            return traceList;
        }
        for (int i = 0; i < list.size(); i++) {
            AMapLocation location = list.get(i);
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            LatLng latLng = new LatLng(lat, lon);
            traceList.add(latLng);
        }
        return traceList;
    }
    public static AMapLocation parselocation(String latLonString) {
        if (latLonString == null || latLonString.equals("") || latLonString.equals("[]")) {
            return null;
        }
        String[] loc = latLonString.split(",");
        AMapLocation location = null;
        if (loc.length == 6) {
            location = new AMapLocation(loc[2]);
            location.setProvider(loc[2]);
            location.setLatitude(Double.parseDouble(loc[0]));
            location.setLongitude(Double.parseDouble(loc[1]));
            location.setTime(Long.parseLong(loc[3]));
            location.setSpeed(Float.parseFloat(loc[4]));
            location.setBearing(Float.parseFloat(loc[5]));
        } else if (loc.length == 2) {
            location = new AMapLocation("gps");
            location.setLatitude(Double.parseDouble(loc[0]));
            location.setLongitude(Double.parseDouble(loc[1]));
        }

        return location;
    }
    public static ArrayList<AMapLocation> parselocations(String latLonString) {
        ArrayList<AMapLocation> locations = new ArrayList<AMapLocation>();
        String[] latLonStrs = latLonString.split(",");
        for (int i = 0; i < latLonStrs.length; i++) {
            AMapLocation location = Util.parselocation(latLonStrs[i]);
            if (location != null) {
                locations.add(location);
            }
        }
        return locations;
    }
}
