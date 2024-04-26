package cn.l13z.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: UserInfoController.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-25 09:08 <br> Description: 用户信息apiController <br>
 * <p>
 * Modification History: <br> - 2024/4/25 AlfredOrlando 用户信息apiController <br>
 */
@RestController
public class UserInfoController {

    /**
     * 获取用户信息当前登录的对象
     * @param principal {@link Principal}
     * @return {@link Principal}
     */
    @GetMapping("/user/info")
    public Principal userInfo(Principal principal) {
        return principal;
    }
}
