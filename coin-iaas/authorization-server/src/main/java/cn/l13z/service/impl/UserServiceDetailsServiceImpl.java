package cn.l13z.service.impl;

import cn.l13z.constant.LoginConstant;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * ClassName: UserServiceDetailsServiceImpl.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-25 23:23 <br> Description: 用户服务实现类 <br>
 * <p>
 * Modification History: <br> - 2024/4/25 AlfredOrlando 用户服务实现类 <br>
 */
@Slf4j
@Service
public class UserServiceDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 区分时后台人员还是我们的用户登录
        String loginType = requestAttributes.getRequest().getParameter("login_type");
        if (StringUtils.isEmpty(loginType)) {
            throw new AuthenticationServiceException("登录类型不能为null");
        }
        UserDetails userDetails = null;
        try {
            // refresh_token 进行纠正
            String grantType = requestAttributes.getRequest().getParameter("grant_type");
            if (LoginConstant.REFRESH_TYPE.equalsIgnoreCase(grantType)) {
                username = adjustUsername(username, loginType);
            }

            switch (loginType) {
                case LoginConstant.ADMIN_TYPE:
                    userDetails = loadSysUserByUsername(username);
                    break;
                case LoginConstant.MEMBER_TYPE:
                    userDetails = loadMemberUserByUsername(username);
                    break;
                default:
                    throw new AuthenticationServiceException("暂不支持的登录方式:" + loginType);
            }
        } catch (IncorrectResultSizeDataAccessException e) { // 我们的用户不存在

            throw new UsernameNotFoundException("用户名" + username + "不存在");
        }

        return userDetails;
    }

    /**
     * 纠正用户的名称
     *
     * @param username  用户的id
     * @param loginType admin_type  member_type
     * @return
     */
    private String adjustUsername(String username, String loginType) {
        if (LoginConstant.ADMIN_TYPE.equals(loginType)) {
            // 管理员的纠正方式
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_USER_WITH_ID, String.class, username);
        }
        if (LoginConstant.MEMBER_TYPE.equals(loginType)) {
            // 会员的纠正方式
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_USER_WITH_ID, String.class, username);
        }
        return username;
    }

    /**
     * 后台人员的登录
     *
     * @param username
     * @return
     */
    private UserDetails loadSysUserByUsername(String username) {
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_SQL, (rs, rowNum) -> {
            if (rs.wasNull()) {
                throw new UsernameNotFoundException("用户名" + username + "不存在");
            }
            long id = rs.getLong("id"); // 用户的id
            String password = rs.getString("password"); // 用户的密码
            int status = rs.getInt("status");

//            log.error("下面是从数据库中查询到的用户信息：" + id + "," + password + "," + status);
            User user = new User(     // 3 封装成一个UserDetails对象，返回
                String.valueOf(1), //使用id->username
                password,
                status == 1,
                true,
                true,
                true,
                getSysUserPermissions(id));


            return user;
        }, username);
    }

    /**
     * // 2 查询这个用户对应的权限 通过用户的id 查询用户的权限
     *
     * @param id
     * @return
     */
    private Collection<? extends GrantedAuthority> getSysUserPermissions(long id) {
        // 1 当用户为超级管理员时，他拥有所有的权限数据
        String roleCode = jdbcTemplate.queryForObject(LoginConstant.QUERY_ROLE_CODE_SQL, String.class, id);
        // 权限的名称
        List<String> permissions = null;
        // 超级用户
        if (LoginConstant.ADMIN_ROLE_CODE.equals(roleCode)) {
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_ALL_PERMISSIONS, String.class);
        } else { // 2 普通用户，需要使用角色->权限数据
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_PERMISSION_SQL, String.class, id);
        }

//        log.error("下面是从数据库中查询到的用户权限：" + permissions);
        if (permissions == null || permissions.isEmpty()) {
            return Collections.emptySet();
        }
        return permissions.stream()
            .distinct() // 去重
            .map(perm -> new SimpleGrantedAuthority(perm))
            .collect(Collectors.toSet());
    }

    /**
     * 会员的登录
     *
     * @param username
     * @return
     */
    private UserDetails loadMemberUserByUsername(String username) {
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_SQL, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                if (rs.wasNull()) {
                    throw new UsernameNotFoundException("用户：" + username + "不存在");
                }
                long id = rs.getLong("id"); // 会员的id
                String password = rs.getString("password");// 会员的登录密码
                int status = rs.getInt("status"); // 会员的状态
                return new User(
                    String.valueOf(id),
                    password,
                    status == 1,
                    true,
                    true,
                    true,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                );
            }
        }, username, username);
    }

}


