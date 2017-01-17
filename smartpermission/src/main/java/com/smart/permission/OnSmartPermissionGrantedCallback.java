package com.smart.permission;

import java.util.List;

/**
 * Created by fengjh on 17/1/6.
 */

public interface OnSmartPermissionGrantedCallback {

    void permissionGrantedResult(int requestCode, List<String> permissions);

}
