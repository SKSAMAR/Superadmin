package com.fintech.superadmin.util;

import java.io.IOException;

public class Exceptions {

    public static IOException noInternetException(String message){
        return new IOException(message);
    }
}
