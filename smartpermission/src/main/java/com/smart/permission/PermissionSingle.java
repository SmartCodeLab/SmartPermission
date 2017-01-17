package com.smart.permission;

import android.Manifest;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.smart.permission.PermissionSingle.ACCESS_COARSE_LOCATION;
import static com.smart.permission.PermissionSingle.ACCESS_FINE_LOCATION;
import static com.smart.permission.PermissionSingle.ADD_VOICEMAIL;
import static com.smart.permission.PermissionSingle.BODY_SENSORS;
import static com.smart.permission.PermissionSingle.CALL_PHONE;
import static com.smart.permission.PermissionSingle.CAMERA;
import static com.smart.permission.PermissionSingle.GET_ACCOUNTS;
import static com.smart.permission.PermissionSingle.PROCESS_OUTGING_CALLS;
import static com.smart.permission.PermissionSingle.READ_CALENDAR;
import static com.smart.permission.PermissionSingle.READ_CALL_LOG;
import static com.smart.permission.PermissionSingle.READ_CONTACTS;
import static com.smart.permission.PermissionSingle.READ_EXTERNAL_STORAGE;
import static com.smart.permission.PermissionSingle.READ_PHONE_STATE;
import static com.smart.permission.PermissionSingle.READ_SMS;
import static com.smart.permission.PermissionSingle.RECEIVE_MMS;
import static com.smart.permission.PermissionSingle.RECEIVE_SMS;
import static com.smart.permission.PermissionSingle.RECEIVE_WAP_PUSH;
import static com.smart.permission.PermissionSingle.RECORD_AUDIO;
import static com.smart.permission.PermissionSingle.SEND_SMS;
import static com.smart.permission.PermissionSingle.USE_SIP;
import static com.smart.permission.PermissionSingle.WRITE_CALENDAR;
import static com.smart.permission.PermissionSingle.WRITE_CALL_LOG;
import static com.smart.permission.PermissionSingle.WRITE_CONTACTS;
import static com.smart.permission.PermissionSingle.WRITE_EXTERNAL_STORAGE;

/**
 * Created by fengjh on 17/1/5.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({READ_CALENDAR, WRITE_CALENDAR, CAMERA, WRITE_CONTACTS,
        GET_ACCOUNTS, READ_CONTACTS, ACCESS_FINE_LOCATION,
        ACCESS_COARSE_LOCATION, RECORD_AUDIO, READ_CALL_LOG,
        READ_PHONE_STATE, CALL_PHONE, WRITE_CALL_LOG, USE_SIP,
        PROCESS_OUTGING_CALLS, ADD_VOICEMAIL, BODY_SENSORS, READ_SMS,
        RECEIVE_WAP_PUSH, RECEIVE_MMS, RECEIVE_SMS, SEND_SMS,
        READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE})
public @interface PermissionSingle {

    String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;

    String CAMERA = Manifest.permission.CAMERA;

    String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    String READ_CONTACTS = Manifest.permission.READ_CONTACTS;

    String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;

    String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    String CALL_PHONE = Manifest.permission.CALL_PHONE;
    String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
    String USE_SIP = Manifest.permission.USE_SIP;
    String PROCESS_OUTGING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;

    String BODY_SENSORS = Manifest.permission.BODY_SENSORS;

    String READ_SMS = Manifest.permission.READ_SMS;
    String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    String SEND_SMS = Manifest.permission.SEND_SMS;

    String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

}
