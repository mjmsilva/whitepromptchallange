package com.ys.challange.rest.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class Util {

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static String getExtensionByStringHandling(String fileName) {
        String e = fileName.substring(fileName.lastIndexOf(".") );
        return e;
    }



}
