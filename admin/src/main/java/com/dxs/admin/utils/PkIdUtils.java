package com.dxs.admin.utils;

import java.util.UUID;

/**
 * Created by Mr.Dxs on 2018/7/17.
 */
public class PkIdUtils {

    /**
     * 获取主键 ID
     * @return
     */
    public static String getPkId(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
