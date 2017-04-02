package com.junnanhao.samantha.workflow.scanner;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.junnanhao.samantha.R;
import com.junnanhao.samantha.model.entity.Raw;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
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

    private static final String SUBJECT_PATTERN = "^【([^【】]+)】|^\\[([^\\[\\]]+)\\]|【([^【】]+)】$|\\[([^\\[\\]]+)\\]$";

    public SmsScanner(Context context) {
        mContext = context;
    }

    private void start() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        lastReadDate = preferences.getLong(LAST_READ, 0);
    }

    private void end() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(LAST_READ, lastReadDate);
        editor.apply();
    }

    public static Cursor readSms(Context context, int year, int month, int date) {

        Calendar selectAfter = Calendar.getInstance();
        selectAfter.set(year, month, date);

        String[] selectionArgs = new String[]{Long.toString(selectAfter.getTimeInMillis())};
        final Cursor cursor = context.getContentResolver().query(SMS_CONTENT, projection, selection, selectionArgs, "date desc");    // 获取手机短信


        if (cursor != null) {
            final int bodyIndex = cursor.getColumnIndex("body");
            final int dateIndex = cursor.getColumnIndex("date");
            final int senderIndex = cursor.getColumnIndex("address");

            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    final Pattern pattern = Pattern.compile(SUBJECT_PATTERN);

                    while (cursor.moveToNext()) {


                        Raw sms = realm.createObject(Raw.class, UUID.randomUUID().hashCode());
                        sms.body(cursor.getString(bodyIndex))
                                .datetime(new Date(cursor.getLong(dateIndex)))
                                .type(Raw.TYPE_SMS)
                                .sender(cursor.getString(senderIndex));

                        Matcher matcher = pattern.matcher(sms.body());
                        if (matcher.find()) {
                            int count = matcher.groupCount();
                            for (int i = 1; i <= count; i++) {
                                String group_i = matcher.group(i);
                                if (group_i != null) {
                                    sms.subject(group_i);
                                    break;
                                }
                            }
                        }

                        System.out.println("--sms-- : " + sms.toString());
                    }
                }
            });
        }

        return cursor;
    }


    @Override
    public List<Raw> scan() {
        start();
        int res = mContext.checkCallingOrSelfPermission(Manifest.permission.READ_SMS);
        if (res != PackageManager.PERMISSION_GRANTED) {
            Timber.e("no permission to read sms");
            return null;
        }

        String[] selectionArgs = new String[]{Long.toString(lastReadDate)};
        Calendar selectAfter = Calendar.getInstance();
        selectAfter.set(2015, 1, 1);
        selectionArgs = new String[]{Long.toString(selectAfter.getTimeInMillis())};

        final Cursor cursor = mContext.getContentResolver()
                .query(SMS_CONTENT, projection, selection, selectionArgs, sortOrder);    // 获取手机短信

        List<Raw> data = null;
        if (cursor != null) {
            data = new ArrayList<>(cursor.getCount());

            final int bodyIndex = cursor.getColumnIndex("body");
            final int dateIndex = cursor.getColumnIndex("date");
            final int senderIndex = cursor.getColumnIndex("address");

            lastReadDate = System.currentTimeMillis();

            while (cursor.moveToNext()) {
                data.add(new Raw()
                        .body(cursor.getString(bodyIndex))
                        .datetime(new Date(cursor.getLong(dateIndex)))
                        .type(Raw.TYPE_SMS)
                        .sender(cursor.getString(senderIndex)));
            }
            cursor.close();
        }
        end();
        return data;
    }

    @Override
    public String[] permissions() {
        return new String[]{Manifest.permission.READ_SMS};
    }


}
