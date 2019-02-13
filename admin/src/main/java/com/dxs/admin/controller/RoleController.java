package com.dxs.admin.controller;

import com.dxs.admin.constants.ParamConstant;
import com.dxs.admin.result.RestResult;
import com.dxs.admin.service.RoleService;
import com.dxs.admin.utils.PageUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Mr.Dxs on 2018/7/26.
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController {


    @Autowired
    private RoleService roleService;


    /**
     * 添加角色
     * @param params
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public RestResult saveRole(@RequestBody Map params){
        return roleService.add(params);
    }


    /**
     * 删除角色
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public RestResult deleteRole(@RequestBody Map map){
        List<Integer> roleIds = (List<Integer>) map.get("pkIds");
        return roleService.delete(roleIds);
    }


    /**
     * 修改角色信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public RestResult updateRole(@RequestBody Map map){
        return roleService.update(map);
    }


    /**
     * 角色分页数据
     * @param map
     * @return
     */
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public RestResult listPage(@RequestBody Map map){
        return roleService.listPage(PageUtils.formatPageParam(map));
    }


    /**
     * 获取角色列表
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public RestResult listRole(){
        return roleService.getAll();
    }


    /**
     * 获取角色所拥有的 资源 ID 列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/getResourceIds",method = RequestMethod.POST)
    public RestResult roleHaveResource(@RequestBody Map map){
        Integer roleId = (Integer) map.get("roleId");
        return roleService.getRoleHaveResource(roleId);
    }


}
