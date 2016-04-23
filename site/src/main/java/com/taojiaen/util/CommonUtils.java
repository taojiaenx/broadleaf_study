package com.taojiaen.util;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class CommonUtils {
    private static final ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);

    public static ShaPasswordEncoder getCustomerPasswordEncoder() {
       return encoder;
    }
}