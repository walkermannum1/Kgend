package com.example.user.kgend.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.user.kgend.data.PathRecord;

import java.util.List;

/**
 * Created by user on 2016/11/16.
 */

public class RecordAdapter extends BaseAdapter {

    private Context mContext;
    private List<PathRecord> mRecordList;

    public RecordAdapter(Context context, List<PathRecord> list) {
        this.mContext = context;
        this.mRecordList = list;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

}
