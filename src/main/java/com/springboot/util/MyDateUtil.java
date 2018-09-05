package com.springboot.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateUtil {

    public static String today(String mask){
        SimpleDateFormat sdf = new SimpleDateFormat(mask);
        return sdf.format(new Date());
    }
}
