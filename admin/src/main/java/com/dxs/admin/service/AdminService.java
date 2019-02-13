package com.dxs.admin.service;

import com.dxs.admin.params.AdminRequest;
import com.dxs.admin.params.AdminUpdateRequest;
import com.dxs.admin.result.RestResult;
import com.dxs.core.domain.Admin;

import java.util.List;
import java.util.Map;

/**
 * Created by Mr.Dxs on 2018/7/13.
 */
public interface AdminService {


    /**
     * 登录
     * @param map
     * @return
     */
    RestResult login(Map map);


    /**
     * 退出
     * @param Authorization
     * @return
     */
    RestResult logout(String Authorization);


    /**
     * 添加 管理员
     * @param adminRequest
     * @return
     */
    RestResult saveAdmin(AdminRequest adminRequest);


    /**
     * 批量添加 管理员
     * @param admins
     * @return
     */
    RestResult saveBatch(List<AdminRequest> admins);


    /**
     * 根据 pkId  删除管理员
     * @param pkIds
     * @return
     */
    RestResult deleteAdmin(List<Integer> pkIds);


    /**
     * 修改 管理员信息
     * @param admin
     * @return
     */
    RestResult updateAdmin(AdminUpdateRequest admin);


    /**
     * 获取管理员信息  根据主键ID\账户名
     * @param type
     * @param keys
     * @return
     */
    RestResult getAdmin(String type, String keys);


    /**
     * 管理员列表
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    RestResult listAdmin(String keyword, Integer pageNum, Integer pageSize);




}
