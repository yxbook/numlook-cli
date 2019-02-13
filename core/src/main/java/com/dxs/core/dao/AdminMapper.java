package com.dxs.core.dao;

import com.dxs.core.domain.Admin;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer pkId);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer pkId);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    // *** 自定义 ***

    Admin getByAuthenticatorUkAccount(String ukAccount);

    Map getAdminByUkAccount(String ukAccount);

    /**
     * 批量删除
     * @param pkIds
     * @return
     */
    int deleteBatch(Integer[] pkIds);


    /**
     * 列表
     * @param queryMap
     * @return
     */
    List<Map> listPage(Map queryMap);

    /**
     * 统计
     * @param queryMap
     * @return
     */
    Integer countPage(Map queryMap);
}