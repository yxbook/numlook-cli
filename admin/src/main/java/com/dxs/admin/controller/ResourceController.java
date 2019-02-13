package com.dxs.admin.controller;

import com.dxs.admin.result.RestResult;
import com.dxs.admin.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Mr.Dxs on 2018/7/27.
 */
@RestController
@RequestMapping("/sys/menus")
public class ResourceController {



    @Autowired
    private ResourceService resourceService;


    /**
     * 添加 一级菜单/二级列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public RestResult save(@RequestBody Map map){
        return resourceService.save(map);
    }


    /**
     * 删除 一级菜单/二级列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public RestResult delete(@RequestBody Map map){
        Integer pkId = (Integer) map.get("pkId");
        return resourceService.delete(pkId);
    }


    /**
     * 修改 菜单/列表 信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public RestResult update(@RequestBody Map map){
        return resourceService.update(map);
    }


    /**
     * 一级菜单 列表
     * @return
     */
    @RequestMapping(value = "/parentList",method = RequestMethod.POST)
    public RestResult list(){
        return resourceService.parentList();
    }


    /**
     * 获取所有资源列表
     * @return
     */
    @RequestMapping(value = "/all",method = RequestMethod.POST)
    public RestResult getAll(){
        return resourceService.getAll();
    }

    /**
     * 根据 管理员ID 获取菜单列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/resource",method = RequestMethod.POST)
    public RestResult getMenusByAdminId(@RequestBody Map map){
        return resourceService.getMenusByAdminId((Integer) map.get("adminId"));
    }


}
