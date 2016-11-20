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
        }
    }

    private class TraceRePlayListener {
    }
}
