package com.dxs.core.dao;

import com.dxs.core.domain.AdminRole;
import com.dxs.core.domain.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AdminRoleMapper {
    int deleteByPrimaryKey(Integer pkId);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    AdminRole selectByPrimaryKey(Integer pkId);

    int updateByPrimaryKeySelective(AdminRole record);

    int updateByPrimaryKey(AdminRole record);

    // **** 自定义 ***
    @Update("delete from sys_admin_role where amdin_id=#{amdinId} and role_id!=1")
    int deleteByAdminId(Integer adminId);


    int deleteBatch(Integer[] pkIds);

    @Select("select ar.role_id from sys_admin_role as ar where ar.amdin_id=#{adminId} and ar.delete_flag=0")
    List<Integer> getAdminRoles(Integer adminId);


}