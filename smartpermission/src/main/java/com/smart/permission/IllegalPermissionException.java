package com.smart.permission;

/**
 * Created by fengjh on 17/1/9.
 */

public class IllegalPermissionException extends RuntimeException {


    public IllegalPermissionException() {
        super();
    }

    public IllegalPermissionException(String s) {
        super(s);
    }

    public IllegalPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalPermissionException(Throwable cause) {
        super(cause);
    }
}
