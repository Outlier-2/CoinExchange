package cn.l13z.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ClassName: WebLog.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-27 03:26 <br> Description: 日志类 <br>
 * <p>
 * Modification History: <br> - 2024/4/27 AlfredOrlando 日志类 <br>
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class WebLog {
    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 消耗时间
     */
    private Integer spendTime;

    /**
     * 根路径
     */
    private String basePath;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private Object parameter;

    /**
     * 返回结果
     */
    private Object result;

}
