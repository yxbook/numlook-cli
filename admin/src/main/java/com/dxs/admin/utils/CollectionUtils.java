package com.dxs.admin.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Mr.Dxs on 2018/7/27.
 */
public class CollectionUtils<T> {

    /**
     * 去除重复
     * @param target
     * @return
     */
    public static<T>  Set<T> toSet(List<T> target){
        Set<T> set = new HashSet();
        set.addAll(target);
        return set;
    }

}
