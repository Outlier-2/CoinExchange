package cn.l13z.controller;

import cn.l13z.domain.SysUser;
import cn.l13z.model.Result;
import cn.l13z.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: TestController.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-27 22:22 <br> Description: 测试控制类 <br>
 * <p>
 * Modification History: <br> - 2024/4/27 AlfredOrlando 测试控制类 <br>
 */
@RestController
@Api(tags = "后台管理系统接口")
public class TestController {

    @Autowired
    private SysUserService sysUserService ;

    @GetMapping("/user/info/{id}")
    @ApiOperation(value = "使用用户的id查询用户",authorizations = {@Authorization("Authorization")})
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",value = "用户的id值")
    })
    public Result<SysUser> sysUser(@PathVariable("id")Long id){
        SysUser sysUser = sysUserService.getById(id);
        return Result.ok(sysUser) ;
    }

}