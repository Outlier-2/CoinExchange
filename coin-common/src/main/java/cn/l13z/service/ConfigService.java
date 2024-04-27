package cn.l13z.service;

import cn.l13z.domain.Config;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * ClassName: ConfigService.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-27 03:49 <br> Description: Service配置类 <br>
 * <p>
 * Modification History: <br> - 2024/4/27 AlfredOrlando Service配置类 <br>
 */
@SuppressWarnings("unused")
public interface ConfigService extends IService<Config> {

    /**
     * 条件分页查询参数
     *
     * @param page 分页参数
     * @param type 类型
     * @param name 参数名称
     * @param code 参数Code
     * @return
     */
    Page<Config> findByPage(Page<Config> page, String type, String name, String code);

    /**
     * 通过的规则的Code 查询签名
     *
     * @param code 签名的code
     * @return config value
     */
    Config getConfigByCode(String code);
}
