package com.example.user.kgend;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.RidePath;
import com.amap.api.services.route.RideStep;

import java.util.List;

/**
 * Created by user on 2016/11/1.
 */

public class RideRouteOverlay extends RouteOverlay {
    private RidePath ridePath;
    private PolylineOptions mPolylineOption;

    public RideRouteOverlay(Context context, AMap amap, RidePath path,
                            LatLonPoint start, LatLonPoint end) {
        super(context);
        this.mAMap = amap;
        this.ridePath = path;
        startPoint = AMapUtil.convertToLatLng(start);
        endPoint = AMapUtil.convertToLatLng(end);
    }

    public void addToMap() {
        initPolylineOptions();
        try {
            List<RideStep> ridePaths = ridePath.getSteps();
            for (int i = 0; i < ridePaths.size(); i++) {
                RideStep rideStep = ridePaths.get(i);
                LatLng lanlng = AMapUtil.convertToLatLng(rideStep.getPolyline().get(0));
                if (i < ridePaths.size() - 1) {
                    if (i == 0) {
                        addRidePolyLine(startPoint, lanlng);
                    }
                    //checkDistanceToNextStep()
                } else {
                    LatLng latlng1 = AMapUtil.convertToLatLng(getLastWalkPoint(rideStep));
                    addRidePolyLine(latlng1, endPoint);
                }
                addRideStationMakers(rideStep, lanlng);
                addRidePolyLines(rideStep);
            }
            addStartAndEndMaker();
            showPolyLine();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void showPolyLine() {
        addPolyLine(mPolylineOption);
    }

    private void addStartAndEndMaker() {
    }

    private void addRideStationMakers(RideStep rideStep, LatLng lanlng) {
    }

    private LatLonPoint getLastWalkPoint(RideStep rideStep) {
        return rideStep.getPolyline().get(rideStep.getPolyline().size() - 1);
    }

    private void addRidePolyLine(LatLng latlngFrom, LatLng latlngTo) {
        mPolylineOption.add(latlngFrom, latlngTo);
    }

    private void addRidePolyLines(RideStep rideStep) {
        mPolylineOption.addAll(AMapUtil.coverArrayList(rideStep.getPolyline()));
    }

    private void initPolylineOptions() {
    }
}
