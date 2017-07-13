package com.smart.permission;

import android.Manifest;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.smart.permission.PermissionGroup.GROUP_CALENDAR;
import static com.smart.permission.PermissionGroup.GROUP_CAMERA;
import static com.smart.permission.PermissionGroup.GROUP_CONTACTS;
import static com.smart.permission.PermissionGroup.GROUP_LOCATION;
import static com.smart.permission.PermissionGroup.GROUP_MICROPHONE;
import static com.smart.permission.PermissionGroup.GROUP_PHONE;
import static com.smart.permission.PermissionGroup.GROUP_SENSORS;
import static com.smart.permission.PermissionGroup.GROUP_SMS;
import static com.smart.permission.PermissionGroup.GROUP_STORAGE;

/**
 * Created by fengjh on 17/1/5.
 */

@Retention(RetentionPolicy.SOURCE)
@StringDef({GROUP_CALENDAR, GROUP_CAMERA, GROUP_CONTACTS,
        GROUP_LOCATION, GROUP_MICROPHONE, GROUP_PHONE,
        GROUP_SENSORS, GROUP_SMS, GROUP_STORAGE})
public @interface PermissionGroup {

    String GROUP_CALENDAR = Manifest.permission_group.CALENDAR;

    String GROUP_CAMERA = Manifest.permission_group.CAMERA;

    String GROUP_CONTACTS = Manifest.permission_group.CONTACTS;

    String GROUP_LOCATION = Manifest.permission_group.LOCATION;

    String GROUP_MICROPHONE = Manifest.permission_group.MICROPHONE;

    String GROUP_PHONE = Manifest.permission_group.PHONE;

    String GROUP_SENSORS = Manifest.permission_group.SENSORS;

    String GROUP_SMS = Manifest.permission_group.SMS;

    String GROUP_STORAGE = Manifest.permission_group.STORAGE;

    interface Group {
        String[] CALENDAR = {PermissionSingle.READ_CALENDAR, PermissionSingle.WRITE_CALENDAR};
        String[] CAMERA = {PermissionSingle.CAMERA};
        String[] CONTACTS = {PermissionSingle.WRITE_CONTACTS, PermissionSingle.GET_ACCOUNTS, PermissionSingle.READ_CONTACTS};
        String[] LOCATION = {PermissionSingle.ACCESS_FINE_LOCATION, PermissionSingle.ACCESS_COARSE_LOCATION};
        String[] MICROPHONE = {PermissionSingle.RECORD_AUDIO};
        String[] PHONE = {PermissionSingle.READ_CALL_LOG, PermissionSingle.READ_PHONE_STATE, PermissionSingle.CALL_PHONE, PermissionSingle.WRITE_CALL_LOG, PermissionSingle.USE_SIP, PermissionSingle.PROCESS_OUTGING_CALLS, PermissionSingle.ADD_VOICEMAIL};
        String[] SENSORS = {PermissionSingle.BODY_SENSORS};
        String[] SMS = {PermissionSingle.READ_SMS, PermissionSingle.RECEIVE_WAP_PUSH, PermissionSingle.RECEIVE_MMS, PermissionSingle.RECEIVE_SMS, PermissionSingle.SEND_SMS};
        String[] STORAGE = {PermissionSingle.READ_EXTERNAL_STORAGE, PermissionSingle.WRITE_EXTERNAL_STORAGE};

    }
}

