package com.example.user.kgend;

import com.amap.api.location.AMapLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/11/9.
 */
public class PathRecord {
    private List<AMapLocation> mPathlinePoints = new ArrayList<AMapLocation>();

    public void setId(int anInt) {
    }

    public void setId(String string) {
    }

    public void setPathline(List<AMapLocation> pathline) {
        this.mPathlinePoints = pathline;
    }
}
