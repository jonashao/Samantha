package com.junnanhao.samantha.workflow.scanner;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.junnanhao.samantha.BuildConfig;
import com.junnanhao.samantha.model.entity.Raw;
import com.junnanhao.samantha.model.entity.Sender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

/**
 * Created by Jonas on 2017/2/17.
 * scan sms in phone. add new messages into raw database.
 */

public class SmsScanner implements Scanner {

    private static final Uri SMS_CONTENT = Uri.parse("content://sms");
    private static final String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
    private static final String selection = "date>?";
    private static final String LAST_READ = "last_read_date";
    private static final String sortOrder = "date desc";

    private Context mContext;

    private long lastReadDate;

    private static final String SUBJECT_PATTERN = "(^【[^【】]+】|^\\[[^\\[\\]]+\\]|【[^【】]+】$|\\[[^\\[\\]]+\\]$)";
    private Pattern mPattern;

    private Raw splitSubject(Raw raw) {
        if (mPattern == null) {
            mPattern = Pattern.compile(SUBJECT_PATTERN);
        }
        // pre-handling: remove all space characters
        raw.body(raw.body().replaceAll("^\\s|\\s$", ""));

        Matcher matcher = mPattern.matcher(raw.body());

        if (matcher.find()) {
            String matched = matcher.group(0);
            raw.subject(matched.substring(1, matched.length() - 1));
            raw.body(matcher.replaceAll(""));
        }
        return raw;
    }

    public SmsScanner(Context context) {
        mContext = context;
        start();
    }

    public void start() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        lastReadDate = preferences.getLong(LAST_READ, 0);
        if (BuildConfig.DEBUG) {
            Calendar selectAfter = Calendar.getInstance();
            selectAfter.set(2015, 1, 1);
            lastReadDate = selectAfter.getTimeInMillis();
        }
    }

    public void stop() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(LAST_READ, lastReadDate);
        editor.apply();
    }


    @Override
    public List<Raw> scan(boolean scanAll) {
        Timber.d("lastReadDate:%s", new Date(lastReadDate));
        if (scanAll) {
            Calendar selectAfter = Calendar.getInstance();
            selectAfter.set(2015, 1, 1);
            lastReadDate = selectAfter.getTimeInMillis();
        }

        int res = mContext.checkCallingOrSelfPermission(Manifest.permission.READ_SMS);
        if (res != PackageManager.PERMISSION_GRANTED) {
            Timber.e("no permission to read sms");
            return null;
        }

        String[] selectionArgs = new String[]{Long.toString(lastReadDate)};

        final Cursor cursor = mContext.getContentResolver()
                .query(SMS_CONTENT, projection, selection, selectionArgs, sortOrder);    // 获取手机短信

        List<Raw> data = null;
        if (cursor != null) {
            data = new ArrayList<>(cursor.getCount());

            final int bodyIndex = cursor.getColumnIndex("body");
            final int dateIndex = cursor.getColumnIndex("date");
            final int senderIndex = cursor.getColumnIndex("address");
            if (scanAll) {
                lastReadDate = System.currentTimeMillis();
            }
            while (cursor.moveToNext()) {
                data.add(splitSubject(new Raw()
                        .body(cursor.getString(bodyIndex))
                        .id(UUID.randomUUID().hashCode())
                        .datetime(new Date(cursor.getLong(dateIndex)))
                        .sender(new Sender().type(Raw.TYPE_SMS).value(cursor.getString(senderIndex)))));
                System.out.println(data.get(data.size() - 1));

            }
            cursor.close();
        }
        return data;
    }


}
