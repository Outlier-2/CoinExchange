package cn.l13z.service.impl;

import cn.l13z.domain.Config;
import cn.l13z.mapper.ConfigMapper;
import cn.l13z.service.ConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * ClassName: ConfigServiceImpl.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-27 03:53 <br> Description: 实现类 <br>
 * <p>
 * Modification History: <br> - 2024/4/27 AlfredOrlando 实现类 <br>
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    /**
     * 条件分页查询参数
     *
     * @param page 分页参数
     * @param type 类型
     * @param name 参数名称
     * @param code 参数Code
     * @return
     */
    @Override
    public Page<Config> findByPage(Page<Config> page, String type, String name, String code) {
        return page(page, new LambdaQueryWrapper<Config>()
            .like(!StringUtils.isEmpty(type), Config::getType, type)
            .like(!StringUtils.isEmpty(name), Config::getName, name)
            .like(!StringUtils.isEmpty(code), Config::getCode, code)
        );
    }

    /**
     * 通过的规则的Code 查询签名
     *
     * @param code 签名的code
     * @return config value
     */
    @Override
    public Config getConfigByCode(String code) {
        return getOne(new LambdaQueryWrapper<Config>().eq(Config::getCode, code));
    }
}
