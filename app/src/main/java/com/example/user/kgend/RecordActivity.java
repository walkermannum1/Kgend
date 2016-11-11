package com.example.user.kgend;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/11/10.
 */
public class RecordActivity extends Activity implements AdapterView.OnItemClickListener {
    private RecordAdapter mRecordAdapter;
    private ListView mAllRecordListView;
    private DbAdapter mDbAdapter;
    private List<PathRecord> mAllRecord = new ArrayList<PathRecord>();
    public static final String RECORD_ID = "Record_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
