package com.smart.permission;

import java.util.List;

/**
 * Created by fengjh on 17/1/6.
 */

public interface OnSmartPermissionDeniedCallback {

    void permissionDeniedResult(int requestCode, List<String> permissions);

}
