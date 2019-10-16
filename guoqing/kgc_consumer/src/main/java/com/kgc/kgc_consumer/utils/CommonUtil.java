package com.kgc.kgc_consumer.utils;



import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/15 10:31
 * @since
 */
@Component
public class CommonUtil {
    public static  String createUUID(int size){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,size);
    }
}
