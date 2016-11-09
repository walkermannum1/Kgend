package com.example.user.kgend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.ashokvarma.bottomnavigation.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;
import static android.R.attr.version;

/**
 * Created by user on 2016/11/9.
 */
public class DbAdapter {
    public static final String KEY_ROWID = "id";
    public static final String KEY_DISTANCE = "distance";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_LINE = "line";
    public static final String KEY_SPEED = "averagespeed";
    public static final String KEY_START = "startpoint";
    public static final String KEY_END = "endpoint";
    public static final String KEY_DATE = "date";
    private final static String DATABASE_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "Record";
    static final String DATABASE_NAME = DATABASE_PATH + "/" + "record.db";
    private static final int DATABASE_VERSION = 1;
    private static final String RECORD_TABLE = "record";
    private static final String RECORD_CREATE = "create table if not exists record("
            + KEY_ROWID
            + "integer primary key autoincrement, "
            + "startpoint STRING, "
            + "endpoint STRING, "
            + "line STRING, "
            + "distance STRING, "
            + "duration STRING, "
            + "averagespeed STRING, "
            + "date STRING, " + ");";

    public static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(RECORD_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    private Context mContext = null;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DbAdapter (Context context) {
        this.mContext = context;
        dbHelper = new DatabaseHelper(mContext);
    }

    public DbAdapter open() throws SQLiteException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public Cursor getAll() {
        return db.rawQuery("SELECT * FROM record", null);
    }

    public boolean delete(long rowId) {
        return db.delete(RECORD_TABLE, "id =" + rowId, null) > 0;
    }

    public long createRecord(String distance, String duration, String averagespeed, String line, String startpoint, String endpoint, String date) {
        ContentValues args = new ContentValues();
        args.put("distance", distance);
        args.put("duration", duration);
        args.put("averagespeed", averagespeed);
        args.put("line", line);
        args.put("startpoint", startpoint);
        args.put("endpoint", endpoint);
        args.put("date", date);
        return db.insert(RECORD_CREATE, null, args);
    }

    public List<PathRecord> queryRecordAll() {
        List<PathRecord> allRecord = new ArrayList<PathRecord>();
        Cursor allRecordCursor = db.query(RECORD_TABLE, getColumns(), null, null, null, null, null);
        while (allRecordCursor.moveToNext()) {
            PathRecord record = new PathRecord();
            record.setId(allRecordCursor.getInt(allRecordCursor.getColumnIndex(DbAdapter.KEY_ROWID)));
            record.setId(allRecordCursor.getString(allRecordCursor.getColumnIndex(DbAdapter.KEY_DISTANCE)));
            record.setId(allRecordCursor.getString(allRecordCursor.getColumnIndex(DbAdapter.KEY_DURATION)));
            record.setId(allRecordCursor.getString(allRecordCursor.getColumnIndex(DbAdapter.KEY_DATE)));
            record.setId(allRecordCursor.getString(allRecordCursor.getColumnIndex(DbAdapter.KEY_LINE)));
            String lines = allRecordCursor.getString(allRecordCursor.getColumnIndex(KEY_LINE));
            record.setPathline(Util.parselocations(lines));
        }
        return allRecord;
    }

    private String[] getColumns() {
        return new String[] { KEY_ROWID, KEY_DISTANCE, KEY_DURATION, KEY_SPEED, KEY_LINE, KEY_START, KEY_END, KEY_DATE};
    }
}
