package com.example.user.kgend;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RidePath;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;

/**
 * Created by user on 2016/10/31.
 */
public class WorkdayActivity extends Activity implements LocationSource, AMapLocationListener, RouteSearch.OnRouteSearchListener, AMap.OnMapClickListener {
    private AMap mAMap;
    private MapView mMapView;
    private Context mContext;
    private RouteSearch mRouteSearch;
    private RideRouteResult mRideResult;
    private RelativeLayout mBottomLayout;
    private LatLonPoint mStartPoint = new LatLonPoint(31.131233,121.461006);
    private LatLonPoint mEndPoint = new LatLonPoint(31.170121,121.401232);
    private OnLocationChangedListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mClientOption;
    private final int ROUTE_TYPE_RIDE = 4;
    private ProgressDialog progDialog = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.basemap_activity);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        init();
        setFromtoMaker();
        searchRouteResult(ROUTE_TYPE_RIDE, RouteSearch.RidingDefault);
    }

    private void searchRouteResult(int routeType, int mode) {
        if (mStartPoint == null) {
            ToastUtil.show(mContext, "Locating... please wait");
            return;
        }
        if (mEndPoint == null) {
            ToastUtil.show(mContext, "Did not choose a Ending yet");
            return;
        }
        showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_RIDE) {
            RouteSearch.RideRouteQuery query = new RouteSearch.RideRouteQuery(fromAndTo, mode);
            mRouteSearch.calculateRideRouteAsyn(query);
        }
    }

    private void showProgressDialog() {
        if (progDialog == null) {
            progDialog = new ProgressDialog(this);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setIndeterminate(false);
            progDialog.setMessage("searching...");
            progDialog.setCancelable(true);
            progDialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    private void setFromtoMaker() {
        mAMap.addMarker(new MarkerOptions().position(AMapUtil.convertToLatLng(mStartPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
        mAMap.addMarker(new MarkerOptions().position(AMapUtil.convertToLatLng(mEndPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));

    }

    private void init() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            setUpMap();
        }
        registerListener();
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
    }

    private void registerListener() {
        mAMap.setOnMapClickListener(WorkdayActivity.this);
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
        } else {
            Log.e("ErrorCode", aMapLocation.getErrorCode() + aMapLocation.getErrorInfo());
            String Cityname = aMapLocation.getCity();
        }
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
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult result, int errorCode) {
        dismissProgressDialog();
        mAMap.clear();
        if (errorCode == 1000) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mRideResult = result;
                    final RidePath ridePath = mRideResult.getPaths().get(0);
                    RideRouteOverlay rideRouteOverlay = new RideRouteOverlay(this, mAMap, ridePath, mRideResult.getStartPos(), mRideResult.getTargetPos());
                    rideRouteOverlay.removeFromMap();
                    rideRouteOverlay.addToMap();
                    rideRouteOverlay.zoomToSpan();
                    mBottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) ridePath.getDistance();
                    int dur = (int) ridePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" +AMapUtil.getFriendlyLength(dis) + ")";
                    mBottomLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, RideDetailActivity.class);
                            intent.putExtra("ride_path", ridePath);
                            intent.putExtra("ride_result", mRideResult);
                            startActivity(intent);
                        }
                    });
                } else if (result == null && result.getPaths() == null) {
                    ToastUtil.show(mContext, "Sorry, did not get any data!");
                }
            } else {
                ToastUtil.show(mContext, "Sorry, cannot get any data!");
            }
        } else {
            ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }
}
