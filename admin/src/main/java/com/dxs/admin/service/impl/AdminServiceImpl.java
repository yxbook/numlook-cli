package com.dxs.admin.service.impl;

import com.dxs.admin.constants.AdminConstant;
import com.dxs.admin.constants.ParamConstant;
import com.dxs.admin.enums.ResultCodeEnum;
import com.dxs.admin.exceptions.APIRunTimeException;
import com.dxs.admin.exceptions.RoleNotExistException;
import com.dxs.admin.params.AdminRequest;
import com.dxs.admin.params.AdminUpdateRequest;
import com.dxs.admin.redis.JWTRedisDAO;
import com.dxs.admin.result.PageResult;
import com.dxs.admin.result.RestResult;
import com.dxs.admin.result.ResultUtils;
import com.dxs.admin.service.AdminService;
import com.dxs.admin.utils.DateUtil;
import com.dxs.admin.utils.EncryptUtils;
import com.dxs.admin.utils.JWTUtils;
import com.dxs.admin.utils.StringUtil;
import com.dxs.core.dao.AdminMapper;
import com.dxs.core.dao.AdminRoleMapper;
import com.dxs.core.dao.RoleMapper;
import com.dxs.core.domain.Admin;
import com.dxs.core.domain.AdminRole;
import com.dxs.core.domain.Role;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Mr.Dxs on 2018/7/13.
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {


    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private JWTRedisDAO jwtRedisDAO;


    @Autowired
    private RoleMapper roleMapper;

    @Override
    public RestResult login(Map map) {
        String account = (String) map.get("ukAccount");
        // 参数校验
        if (StringUtil.isEmpty(account)){
            return ResultUtils.error(ResultCodeEnum.ILLEGAL_ARGUMENT.getCode(),ResultCodeEnum.ILLEGAL_ARGUMENT.getMsg());
        }
        // 验证账户是否存在
        Admin admin = adminMapper.getByAuthenticatorUkAccount(account);
        if (null == admin){
            return ResultUtils.error(ResultCodeEnum.USER_NOT_FOUND.getCode(),ResultCodeEnum.USER_NOT_FOUND.getMsg());
        }
        // 验证密码
        /*String password = (String) map.get("password");
        if (!admin.getPassword().equals(EncryptUtils.encrypt(password,admin.getSalt()))){
            return ResultUtils.error(ResultCodeEnum.USER_PASSWORD_NOT_MATCH.getCode(),ResultCodeEnum.USER_PASSWORD_NOT_MATCH.getMsg());
        }*/
        // 验证是否禁用
        if (1 == admin.getStatus()){
            return ResultUtils.error(ResultCodeEnum.USER_BAN.getCode(),ResultCodeEnum.USER_BAN.getMsg());
        }

        // 生成 token
        Map domain = new HashMap();
        domain.put(AdminConstant.PKID, admin.getPkId());
        domain.put(AdminConstant.UKACCOUNT, admin.getUkAccount());
        domain.put("time", System.currentTimeMillis());
        String token = JWTUtils.generateToken(AdminConstant.JWT_SALT, domain);

        // 持久化到 Redis
        jwtRedisDAO.set(AdminConstant.JWT_TOKEN + account, token);

        // 返回数据
        Map adminMap = adminMapper.getAdminByUkAccount(account);
        Map params = new HashMap();
        params.put("admin",adminMap);
        params.put("token",token);

        return ResultUtils.success(params);
    }

    @Override
    public RestResult logout(String authorization) {

        Map params = JWTUtils.getClaims(AdminConstant.JWT_SALT, authorization);
        if (StringUtil.isEmpty((String) params.get(AdminConstant.UKACCOUNT))){
            return ResultUtils.error(ResultCodeEnum.ILLEGAL_ARGUMENT.getCode(),ResultCodeEnum.ILLEGAL_ARGUMENT.getMsg());
        }
        // 更新 redis token
        jwtRedisDAO.delete(AdminConstant.JWT_TOKEN+params.get(AdminConstant.UKACCOUNT));
        return ResultUtils.success(null);
    }

    @Override
    public RestResult saveAdmin(AdminRequest adminRequest) {

        // 判断 是否已经存在
        if (null != adminMapper.getAdminByUkAccount(adminRequest.getUkAccount())) {
            return ResultUtils.error(ResultCodeEnum.ACCOUNT_EXIST.getCode(),ResultCodeEnum.ACCOUNT_EXIST.getMsg());
        }

        Role role = roleMapper.selectByPrimaryKey(adminRequest.getRoleId());
        if (null == role){
            throw new RoleNotExistException(ResultCodeEnum.ROLE_NOT_FOUND.getCode(),ResultCodeEnum.ROLE_NOT_FOUND.getMsg());
        }


        Date date = DateUtil.getCurrentDate();

        // 初始化 管理员
        // 传递 date 作用：保持用户创建时间 与 用户角色关联信息创建时间一致性
        Admin admin = formatAdmin(adminRequest,date);

        adminMapper.insertSelective(admin);

        if (null == admin.getPkId()){
            throw new IllegalArgumentException();
        }

        // 持久化 用户角色关联信息
        AdminRole adminRole = formatAdminRole(adminRequest, admin.getPkId(), date);
        adminRoleMapper.insertSelective(adminRole);

        // 处理 返回特殊内容
        admin.setPassword(null);
        admin.setSalt(null);
        return ResultUtils.success(admin);
    }

    @Override
    public RestResult saveBatch(List<AdminRequest> adminRequests) {

        // 验证用户是否存在
        for (AdminRequest a:adminRequests
             ) {
            if (null != adminMapper.getAdminByUkAccount(a.getUkAccount()))
                throw new APIRunTimeException(ResultCodeEnum.ACCOUNT_EXIST.getCode(),ResultCodeEnum.ACCOUNT_EXIST.getMsg());
        }

        // 验证角色是否存在
        for (AdminRequest a:adminRequests){
            if (null == roleMapper.selectByPrimaryKey(a.getRoleId()))
                throw new RoleNotExistException(ResultCodeEnum.ROLE_NOT_FOUND.getCode(),ResultCodeEnum.ROLE_NOT_FOUND.getMsg());

        }

        List<Admin> admins = new ArrayList<>();
        Date currentDate = DateUtil.getCurrentDate();

        adminRequests.forEach(adminRequest -> admins.add(formatAdmin(adminRequest,currentDate)));

        // 批量持久化用户信息

        List<AdminRole> adminRoles = new ArrayList<>();
        for (int i=0; i<adminRequests.size(); i++){
            AdminRole adminRole = new AdminRole();
            adminRole.setAmdinId(admins.get(i).getPkId());
            adminRole.setRoleId(adminRequests.get(i).getRoleId());
            adminRole.setDeleteFlag(0);
            adminRole.setCreateTime(currentDate);
            adminRoles.add(adminRole);
        }

        // 批量持久化用户角色关联信息


        return ResultUtils.success(null);
    }

    @Override
    public RestResult deleteAdmin(List<Integer> pkIds) {

        Integer[] ids = new Integer[pkIds.size()];

        if (pkIds.size() > 0){
            for (int i = 0; i < pkIds.size(); i++) {
                ids[i] = pkIds.get(i);
            }
        }

        // 管理员信息 逻辑删除
        adminMapper.deleteBatch(ids);
        // 管理员与角色关联信息 物理删除
//        adminRoleMapper.deleteBatch(ids);
        return ResultUtils.success(null);
    }

    @Override
    public RestResult updateAdmin(AdminUpdateRequest adminUpdateRequest) {

        Date currentDate = DateUtil.getCurrentDate();

        Admin oldAdmin = adminMapper.selectByPrimaryKey(adminUpdateRequest.getPkId());

        // 初始化 修改信息
        Admin admin = new Admin();
        admin.setPkId(adminUpdateRequest.getPkId());
        admin.setNickname(adminUpdateRequest.getNickname());
        admin.setStatus(adminUpdateRequest.getStatus());
        admin.setPhone(adminUpdateRequest.getPhone());
        admin.setEmail(adminUpdateRequest.getEmail());
        admin.setUpdateTime(currentDate);

        String salt = oldAdmin.getSalt();
        String newPass = EncryptUtils.encrypt(adminUpdateRequest.getPassword(), salt);
        admin.setPassword(newPass);


//        adminMapper.updateByPrimaryKey(admin);
        adminMapper.updateByPrimaryKeySelective(admin);

        // 删除之前 管理员角色关联信息
        adminRoleMapper.deleteByAdminId(admin.getPkId());

        // 添加新的关联信息
        AdminRole adminRole = formatAdminRole(adminUpdateRequest,admin.getPkId(),currentDate);
        adminRoleMapper.insertSelective(adminRole);

        return ResultUtils.success(null);
    }

    @Override
    public RestResult getAdmin(String type, String keys) {

        if (AdminConstant.PKID.equals(type)){
            Integer pkId = Integer.valueOf(keys);
            return ResultUtils.success(adminMapper.selectByPrimaryKey(pkId));
        }else if (AdminConstant.UKACCOUNT.equals(type)){
            return ResultUtils.success(adminMapper.getAdminByUkAccount(keys));
        }
        return ResultUtils.error(ResultCodeEnum.ILLEGAL_ARGUMENT.getCode(),ResultCodeEnum.ILLEGAL_ARGUMENT.getMsg());
    }

    @Override
    public RestResult listAdmin(String keyword, Integer pageNum, Integer pageSize) {

        int n = pageNum == 1 ? pageNum = 0 : (pageNum = (pageNum - 1) * pageSize);

        // 查询参数
        Map domainMap = new HashMap();
        domainMap.put(ParamConstant.KEY_WORD,keyword);
        domainMap.put(ParamConstant.PAGE_SIZE,pageSize);
        domainMap.put(ParamConstant.PAGE_NUM,pageNum);

        List<Map> admins = adminMapper.listPage(domainMap);
        for (Map map:admins
             ) {
            map.put("createTime", DateUtil.parseDate((Date) map.get("createTime")));
        }
        int count = adminMapper.countPage(domainMap);

        return ResultUtils.success(new PageResult<Map>(admins,count));
    }


    // ********* 自定义 封装功能函数 ***********

    public Admin formatAdmin(AdminRequest adminRequest, Date currentDate){

        Date date = currentDate;

        // 初始化 管理员
        Admin admin = new Admin();
        admin.setUkAccount(adminRequest.getUkAccount());
        admin.setNickname(adminRequest.getNickname());
        admin.setPhone(adminRequest.getPhone());
        admin.setEmail(adminRequest.getEmail());
        admin.setAvatar(adminRequest.getAvatar());
        admin.setAddress(adminRequest.getAddress());
        admin.setStatus(adminRequest.getStatus());
        admin.setDeleteFlag(0);
        admin.setCreateTime(date);
        admin.setSortTime(date);

        // 生成盐值 加密密码 并持久化管理员信息
        String salt = EncryptUtils.createSalt();
        String newPass = EncryptUtils.encrypt(adminRequest.getPassword(), salt);
        String authenticator = EncryptUtils.encrypt(adminRequest.getUkAccount(), salt);      // 散列加密（account + salt） 用于 shiro 的身份认证
        admin.setPassword(newPass);
        admin.setSalt(salt);
        admin.setAuthenticator(authenticator);

        return admin;
    }

    public AdminRole formatAdminRole(Object param, Integer pkId, Date currentDate){
        AdminRole adminRole = new AdminRole();
        if (param instanceof AdminRequest){
            AdminRequest adminRequest = (AdminRequest) param;
            adminRole.setAmdinId(pkId);
            adminRole.setRoleId(adminRequest.getRoleId());
            adminRole.setDeleteFlag(0);
            adminRole.setCreateTime(currentDate);
        }else if (param instanceof AdminUpdateRequest){
            AdminUpdateRequest adminUpdateRequest = (AdminUpdateRequest) param;
            adminRole.setAmdinId(pkId);
            adminRole.setRoleId(adminUpdateRequest.getRoleId());
            adminRole.setDeleteFlag(0);
            adminRole.setCreateTime(currentDate);
        }
        return adminRole;
    }
}
