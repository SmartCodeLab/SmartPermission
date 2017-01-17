package com.smart.permission;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.SparseArray;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by fengjh on 17/1/9.
 */

public class SmartPermission extends BasePermission {

    private Object mObject;
    private String[] mPermissions;
    private int mRequestPermissionCode;

    private SmartPermission(Object object) {
        this.mObject = object;
    }

    public static boolean hasPermission(@NonNull Context context, @NonNull @PermissionSingle String... permissions) {
        if (!isAndroidM()) {
            return true;
        }
        for (String perm : permissions) {
            boolean granted = ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED;
            if (!granted) {
                return false;
            }
        }
        return true;
    }

    public static SmartPermission smartWith(@NonNull Activity activity) {
        if (null == activity) {
            throw new NullPointerException("activity should not null");
        }
        return new SmartPermission(activity);
    }

    public static SmartPermission smartWith(@NonNull android.support.v4.app.Fragment fragment) {
        if (null == fragment) {
            throw new NullPointerException("fragment should not null");
        }
        return new SmartPermission(fragment);
    }

    public static SmartPermission smartWith(@NonNull android.app.Fragment fragment) {
        if (null == fragment) {
            throw new NullPointerException("fragment should not null");
        }
        return new SmartPermission(fragment);
    }

    public SmartPermission setPermissionSingle(@NonNull @PermissionSingle String... permissionSingle) {
        if (null == permissionSingle || permissionSingle.length == 0) {
            throw new IllegalPermissionException("permissionSingle should not null or length = 0");
        }
        this.mPermissions = permissionSingle;
        return this;
    }

    public SmartPermission setPermissionGroup(@NonNull @PermissionGroup String... permissionGroup) {
        if (null == permissionGroup || permissionGroup.length == 0) {
            throw new IllegalPermissionException("permissionGroup should not null or length = 0");
        }
        SparseArray<String> array = new SparseArray<>();
        for (String group : permissionGroup) {
            switch (group) {
                case PermissionGroup.GROUP_CALENDAR:
                    for (String single : PermissionGroup.CALENDAR) {
                        array.append(array.size(), single);
                    }
                    break;
                case PermissionGroup.GROUP_CAMERA:
                    for (String single : PermissionGroup.Group.CAMERA) {
                        array.append(array.size(), single);
                    }
                    break;
                case PermissionGroup.GROUP_CONTACTS:
                    for (String single : PermissionGroup.Group.CONTACTS) {
                        array.append(array.size(), single);
                    }
                    break;
                case PermissionGroup.GROUP_LOCATION:
                    for (String single : PermissionGroup.Group.LOCATION) {
                        array.append(array.size(), single);
                    }
                    break;
                case PermissionGroup.GROUP_MICROPHONE:
                    for (String single : PermissionGroup.Group.MICROPHONE) {
                        array.append(array.size(), single);
                    }
                    break;
                case PermissionGroup.GROUP_PHONE:
                    for (String single : PermissionGroup.Group.PHONE) {
                        array.append(array.size(), single);
                    }
                    break;
                case PermissionGroup.GROUP_SENSORS:
                    for (String single : PermissionGroup.Group.SENSORS) {
                        array.append(array.size(), single);
                    }
                    break;
                case PermissionGroup.GROUP_SMS:
                    for (String single : PermissionGroup.Group.SMS) {
                        array.append(array.size(), single);
                    }
                    break;
                case PermissionGroup.GROUP_STORAGE:
                    for (String single : PermissionGroup.Group.STORAGE) {
                        array.append(array.size(), single);
                    }
                    break;
                default:
                    throw new IllegalPermissionException("please set valid permissionGroup");
            }
        }
        mPermissions = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            mPermissions[i] = array.get(i);
        }
        return this;
    }

    public SmartPermission setRequestCode(int requestCode) {
        this.mRequestPermissionCode = requestCode;
        return this;
    }

    @SuppressLint("NewApi")
    public void requestPermissions() {
        if (null == mObject || null == mPermissions) {
            throw new NullPointerException("activity or fragment or permissionSingle or permissionGroup should not null");
        }
        checkObjectsValid(mObject);
        boolean showRationale = false;
        for (String perm : mPermissions) {
            showRationale = showRationale || shouldShouldRequestPermissionRationale(mObject, perm);
        }
        if (showRationale) {
            Activity activity = getActivity(mObject);
            if (null == activity) {
                throw new IllegalStateException("Can't show rationale dialog for null Activity");
            }
            if (null != getSupportFragmentManager(mObject)) {
                showRationalAppCompatDialogFragment(activity);
            } else if (null != getFragmentManager(mObject)) {
                showRationalDialogFragment(activity);
            } else {
                showRationaleAlertDialog(activity);
            }
        } else {
            executePermissionsRequest();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void executePermissionsRequest() {
        checkObjectsValid(mObject);
        if (mObject instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) mObject, mPermissions, mRequestPermissionCode);
        } else if (mObject instanceof android.support.v4.app.Fragment) {
            ((android.support.v4.app.Fragment) mObject).requestPermissions(mPermissions, mRequestPermissionCode);
        } else if (mObject instanceof android.app.Fragment) {
            ((android.app.Fragment) mObject).requestPermissions(mPermissions, mRequestPermissionCode);
        }
    }

    private void showRationalAppCompatDialogFragment(Activity activity) {
//        new android.support.v7.app.AppCompatDialogFragment();
        new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setMessage(activity.getResources().getString(R.string.rationale_ask))
                .setTitle(activity.getResources().getString(R.string.rationale_ask_title))
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executePermissionsRequest();
                    }
                })
                .show();
    }

    private void showRationalDialogFragment(Activity activity) {
//        new android.app.DialogFragment();
        new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setMessage(activity.getResources().getString(R.string.rationale_ask))
                .setTitle(activity.getResources().getString(R.string.rationale_ask_title))
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executePermissionsRequest();
                    }
                })
                .show();
    }

    private void showRationaleAlertDialog(Activity activity) {
        new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setMessage(activity.getResources().getString(R.string.rationale_ask))
                .setTitle(activity.getResources().getString(R.string.rationale_ask_title))
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executePermissionsRequest();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executePermissionsRequest();
                    }
                })
                .show();
    }

    public static void onRequestPermissionsResult(@NonNull final Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, Object... callback) {
        ArrayList<String> granted = new ArrayList<>();
        ArrayList<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }
        if (null != callback && callback.length > 0) {
            for (Object permissionCallback : callback) {
                if (!granted.isEmpty() && permissionCallback instanceof OnSmartPermissionGrantedCallback) {
                    ((OnSmartPermissionGrantedCallback) permissionCallback).permissionGrantedResult(requestCode, granted);
                }
                if (!denied.isEmpty() && permissionCallback instanceof OnSmartPermissionDeniedCallback) {
                    ((OnSmartPermissionDeniedCallback) permissionCallback).permissionDeniedResult(requestCode, denied);
                    if (SmartPermission.somePermissionPermanentlyDenied(activity, denied)) {
                        new AlertDialog.Builder(activity)
                                .setTitle(activity.getResources().getString(R.string.rationale_title_settings))
                                .setMessage(activity.getResources().getString(R.string.rationale_ask_again))
                                .setNegativeButton(activity.getResources().getString(R.string.rationale_cancel), null)
                                .setPositiveButton(activity.getResources().getString(R.string.rationale_setting), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SmartPermission.openSettings(activity);
                                    }
                                }).show();

                    }
                }
            }
        }
        if (!granted.isEmpty() && denied.isEmpty()) {
            annotatedMethod(activity, requestCode);
        }
    }

    private static void annotatedMethod(@NonNull Object object, int requestCode) {
        Method method = getMethod(object, SmartPermissionCode.class, requestCode);
        if (null != method) {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            try {
                method.invoke(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static <A extends Annotation> Method getMethod(@NonNull Object object, Class<A> annotation, int requestCode) {
        Class clazz = object.getClass();
        if (isUsingAndroidAnnotations(object)) {
            clazz = clazz.getSuperclass();
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                int code = method.getAnnotation(SmartPermissionCode.class).value();
                if (code == requestCode) {
                    if (method.getParameterTypes().length > 0) {
                        throw new RuntimeException(
                                "Cannot execute method " + method.getName() + " because it is non-void method and/or has input parameters.");
                    }
                    return method;
                }
            }
        }
        return null;
    }

}
