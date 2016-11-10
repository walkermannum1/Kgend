package com.example.user.kgend;

import com.amap.api.location.AMapLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/11/9.
 */
public class PathRecord {
    private AMapLocation mStartpoint;
    private AMapLocation mEndpoint;
    private List<AMapLocation> mPathlinePoints = new ArrayList<AMapLocation>();
    private String mDistance;
    private String mDuration;
    private String mDate;
    private String mAveragespeed;
    private int mId = 0;

    public PathRecord() {
    }

    public AMapLocation getStartpoint() {
        return mStartpoint;
    }

    public AMapLocation getEndpoint() {
        return mEndpoint;
    }

    public List<AMapLocation> getPathlinePoints() {
        return mPathlinePoints;
    }

    public void setPathlinePoints(List<AMapLocation> pathlinePoints) {
        this.mPathlinePoints = pathlinePoints;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        this.mDistance = distance;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        this.mDuration = duration;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getAveragespeed() {
        return mAveragespeed;
    }

    public void setAveragespeed(String averagespeed) {
        this.mAveragespeed = averagespeed;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public List<AMapLocation> getPathline() {
        return mPathlinePoints;
    }
    public void setPathline(List<AMapLocation> pathline) {
        this.mPathlinePoints = pathline;
    }

    public void setStartpoint(AMapLocation startpoint) {
        this.mStartpoint = startpoint;
    }

    public void setEndpoint(AMapLocation endpoint) {
        this.mEndpoint = endpoint;
    }

    public void addPoint(AMapLocation point) {
        mPathlinePoints.add(point);
    }

    @Override
    public String toString() {
        StringBuilder record = new StringBuilder();
        record.append("记录大小：" + getPathline().size() + ", " );
        record.append("距离：" + getDistance() + "m, ");
        record.append("时间：" + getDuration() + "s.");
        return record.toString();
    }
}
