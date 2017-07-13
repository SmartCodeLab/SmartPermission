package com.smart.demo.smartpermission;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.smart.permission.PermissionSingle;
import com.smart.permission.SmartPermission;
import com.smart.permission.SmartPermissionCode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_camera_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCamera();
            }
        });
        findViewById(R.id.btn_contact_sms_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestContactSMS();
            }
        });
    }

    @SmartPermissionCode(value = 100)
    public void requestCamera() {
        if (SmartPermission.hasPermission(this, PermissionSingle.CAMERA)) {
            Toast.makeText(this, "有相机权限", Toast.LENGTH_SHORT).show();
        } else {
            SmartPermission
                    .smartWith(this)
                    .setPermissionSingle(PermissionSingle.CAMERA)
                    .setRequestCode(100)
                    .requestPermissions();
        }
    }

    @SmartPermissionCode(value = 300)
    public void requestContactSMS() {
//        String[] perms = new String[]{PermissionSingle.READ_CONTACTS, PermissionSingle.WRITE_CONTACTS, PermissionSingle.READ_SMS,PermissionSingle.SEND_SMS};
        String[] perms = new String[]{PermissionSingle.READ_CONTACTS};
        if (SmartPermission.hasPermission(this, perms)) {
            Toast.makeText(this, "有联系人和短信权限", Toast.LENGTH_SHORT).show();
        } else {
            SmartPermission.smartWith(this)
                    .setPermissionSingle(perms)
                    .setRequestCode(300)
                    .requestPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SmartPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
//        SmartPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults, new OnSmartPermissionGrantedCallback() {
//            @Override
//            public void permissionGrantedResult(int requestCode, List<String> permissions) {
//
//            }
//        }, new OnSmartPermissionDeniedCallback() {
//            @Override
//            public void permissionDeniedResult(int requestCode, List<String> permissions) {
//
//            }
//        });
    }
}
