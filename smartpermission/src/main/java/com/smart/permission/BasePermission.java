package com.smart.permission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.List;

/**
 * Created by fengjh on 17/1/9.
 */

public abstract class BasePermission {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected static Activity getActivity(@NonNull Object object) {
        if (object instanceof Activity) {
            return ((Activity) object);
        } else if (object instanceof android.support.v4.app.Fragment) {
            return ((android.support.v4.app.Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).getActivity();
        }
        return null;
    }

    @NonNull
    @SuppressLint("NewApi")
    protected static android.support.v4.app.FragmentManager getSupportFragmentManager(@NonNull Object object) {
        if (object instanceof android.support.v4.app.FragmentActivity) {
            return ((android.support.v4.app.FragmentActivity) object).getSupportFragmentManager();
        } else if (object instanceof android.support.v4.app.Fragment) {
            return ((android.support.v4.app.Fragment) object).getChildFragmentManager();
        }
        return null;
    }

    @NonNull
    protected static android.app.FragmentManager getFragmentManager(@NonNull Object object) {
        if (object instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                return ((Activity) object).getFragmentManager();
            }
        } else if (object instanceof android.app.Fragment) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return ((android.app.Fragment) object).getChildFragmentManager();
            } else {
                return ((android.app.Fragment) object).getFragmentManager();
            }
        }
        return null;
    }

    protected static void checkObjectsValid(@Nullable Object object) {
        if (null == object) {
            throw new NullPointerException("Activity or Fragment should not be null");
        }
        boolean isActivity = object instanceof Activity;
        boolean isSupportFragment = object instanceof android.support.v4.app.Fragment;
        boolean isFragment = object instanceof android.app.Fragment;
        if (!(isActivity || isSupportFragment || (isFragment && isAndroidM()))) {
            if (isFragment) {
                throw new IllegalArgumentException(
                        "Target SDK needs to be greater than 23 if caller is android.app.Fragment");
            } else {
                throw new IllegalArgumentException("Caller must be an Activity or a Fragment.");
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected static boolean shouldShouldRequestPermissionRationale(@NonNull Object object, @NonNull String permission) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, permission);
        } else if (object instanceof android.support.v4.app.Fragment) {
            return ((android.support.v4.app.Fragment) object).shouldShowRequestPermissionRationale(permission);
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).shouldShowRequestPermissionRationale(permission);
        }
        return false;
    }

    protected static boolean isUsingAndroidAnnotations(@NonNull Object object) {
        if (!object.getClass().getSimpleName().endsWith("_")) {
            return false;
        }
        try {
            Class clazz = Class.forName("org.androidannotations.api.view.HasViews");
            return clazz.isInstance(object);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean somePermissionPermanentlyDenied(@NonNull Object object,
                                                          @NonNull List<String> deniedPermissions) {
        for (String deniedPermission : deniedPermissions) {
            if (permissionPermanentlyDenied(object, deniedPermission)) {
                return true;
            }
        }

        return false;
    }

    public static boolean permissionPermanentlyDenied(@NonNull Object object,
                                                      @NonNull String deniedPermission) {
        return !shouldShouldRequestPermissionRationale(object, deniedPermission);
    }

    public static boolean isAndroidM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    //打开对应应用的应用程序设置页面
    public static void openSettings(Context context) {
        //步骤：应用信息－>权限－>'勾选对应权限'
        Uri uri = Uri.parse("package:" + context.getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //通过任务管理器杀死进程
    //需添加权限 {@code <uses-permission android:name="android.permission.RESTART_PACKAGES"/>}</p>
    public static void restartActivity(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RESTART_PACKAGES) != PackageManager.PERMISSION_GRANTED) {
            throw new IllegalPermissionException("require android.permission.RESTART_PACKAGES");
        }
        int currentVersion = Build.VERSION.SDK_INT;
        if (currentVersion > Build.VERSION_CODES.ECLAIR_MR1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            System.exit(0);
        } else {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
        }
    }
}
