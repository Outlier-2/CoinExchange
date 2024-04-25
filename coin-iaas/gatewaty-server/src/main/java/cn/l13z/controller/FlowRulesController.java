package cn.l13z.controller;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: FlowRulesController.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-24 23:45 <br> Description: 流给咱控制器 <br>
 * <p>
 * Modification History: <br> - 2024/4/24 AlfredOrlando 流给咱控制器 <br>
 */
@RestController
public class FlowRulesController {

    @GetMapping("/gateway")
    public Set<GatewayFlowRule> getGatewayFlowRules() {
        return GatewayRuleManager.getRules();
    }

    @GetMapping("/api")
    public Set<ApiDefinition> getApiGroupRules() {
        return GatewayApiDefinitionManager.getApiDefinitions();
    }
}
