package com.dxs.admin.shiro;

import com.dxs.admin.constants.AdminConstant;
import com.dxs.admin.exceptions.APIRunTimeException;
import com.dxs.admin.redis.JWTRedisDAO;
import com.dxs.admin.utils.StringUtil;
import com.dxs.core.dao.AdminMapper;
import com.dxs.core.dao.ResourceMapper;
import com.dxs.core.dao.RoleMapper;
import com.dxs.core.domain.Admin;
import com.dxs.core.domain.Resource;
import com.dxs.core.domain.Role;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mr.Dxs on 2018/7/30.
 */
public class AdminRealm extends AuthorizingRealm {


    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private JWTRedisDAO jwtRedisDAO;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof ShiroToken;
    }


    /**
     * 权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String account = (String) principalCollection.getPrimaryPrincipal();
        Admin admin = adminMapper.getByAuthenticatorUkAccount(account);

        List<String> roleNames = new ArrayList<>();
        List<Integer> roleIds = new ArrayList<>();

        // 权限列表
        List<String> perms = new ArrayList<>();

        if (null != admin){
            // 获取当前 管理员 角色列表
            List<Role> roles = roleMapper.getRolesByAdminId(admin.getPkId());
            // 获取 角色名称列表、角色ID列表
            for (Role role: roles
                    ) {
                roleNames.add(role.getUkName());
                roleIds.add(role.getPkId());
            }

            if (roleIds.size() > 0){
                // 获取当前角色下的 资源列表
                List<Resource> resources = resourceMapper.getResourcesByRoleId(roleIds);

                for (Resource resource:resources
                     ) {
                    if (null != resource){
                        if (!StringUtil.isEmpty(resource.getPerms())){
                            if (resource.getPerms().contains(",")) {
                                String[] permsArray = resource.getPerms().split(",");
                                perms.addAll(Arrays.asList(permsArray));
                            } else {
                                perms.add(resource.getPerms());
                            }
                        }
                    }
                }
            }

        }else {
            throw new AuthorizationException();
        }

        //为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roleNames);
        authorizationInfo.addStringPermissions(perms);
        return authorizationInfo;
    }

    /**
     * 身份认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws APIRunTimeException {

        // 验证用户是否携带 token
        String principal  = (String) authenticationToken.getPrincipal();
        if (null == principal){
            throw new AuthorizationException();
        }

        // 获取 account 、 token
        String[] target = principal.split(":");
        String token = target[1];
        String account = target[0];

        // 验证用户是否存在
        Admin admin = adminMapper.getByAuthenticatorUkAccount(account);
        if (admin == null){
            // 账号不存在
            throw new APIRunTimeException(1,"account not exist");
//            throw new UnknownAccountException();
        }

        // 验证 token
        String redisToken = jwtRedisDAO.get(AdminConstant.JWT_TOKEN+account);
        if (null == redisToken || (!token.equals(redisToken))){
//            throw new AuthorizationException();
            throw new APIRunTimeException(1,"Token not matcher");
        }

        // 交给 AuthenticatingRealm 使用 CredentialsMatcher 进行密码匹配 ，可以自定实现
        SimpleAuthenticationInfo simpleAuthorizationInfo = new
                SimpleAuthenticationInfo(account, admin.getAuthenticator(), ByteSource.Util.bytes(admin.getSalt()), getName());

        return simpleAuthorizationInfo;
    }
}
