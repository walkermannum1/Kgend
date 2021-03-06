package com.example.user.kgend.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.amap.api.maps.model.LatLng;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Guang on 2016/11/20.
 */

public class Replay implements Runnable{
    private TraceRePlayHandler mHandler;
    private static final int TRACE_MOVE = 1;
    private static final int TRACE_FINISH = 2;
    private List<LatLng> mTraceList;
    private int mIntervalMillisecond;
    private TraceRePlayListener mUpdateListener;
    private boolean mStop = false;

    public Replay(List<LatLng> list, int intervalMillisecond, TraceRePlayListener listener) {
        mTraceList = list;
        mIntervalMillisecond = intervalMillisecond;
        mUpdateListener = listener;
        mHandler = new TraceRePlayHandler(this);
    }

    public void stopTrace() {
        mStop = true;
    }
    @Override
    public void run() {
        if (mTraceList != null) {
            for (int i = 0; i < mTraceList.size(); i++) {
                if (mStop){
                    break;
                }
                LatLng latLng = mTraceList.get(i);
                Message message = mHandler.obtainMessage();
                message.what = TRACE_MOVE;
                message.obj = latLng;
                mHandler.sendMessage(message);
                try {
                    Thread.sleep(mIntervalMillisecond);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!mStop) {
                Message finishMessage = mHandler.obtainMessage();
                finishMessage.what = TRACE_FINISH;
                mHandler.sendMessage(finishMessage);
            }
        }
    }

    static class TraceRePlayHandler extends Handler {
        WeakReference<Replay> mReplay;

        public TraceRePlayHandler(Replay replay) {
            super(Looper.getMainLooper());
            mReplay = new WeakReference<Replay>(replay);
        }

        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            Replay play = mReplay.get();
            switch (message.what) {
                case TRACE_MOVE:
                    LatLng latLng = (LatLng) message.obj;
                    if (play.mUpdateListener != null) {
                        play.mUpdateListener.onTraceUpdating(latLng);
                    }
                    break;
                case TRACE_FINISH:
                    if (play.mUpdateListener != null) {
                        play.mUpdateListener.onTraceUpdateFinish();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private interface TraceRePlayListener {

        public void onTraceUpdating(LatLng latLng);

        public void onTraceUpdateFinish();
    }
}
