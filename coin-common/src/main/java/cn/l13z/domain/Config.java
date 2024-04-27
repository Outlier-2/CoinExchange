package cn.l13z.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;


/**
 * ClassName: Config.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-27 03:49 <br> Description: domain对象配置 <br>
 * <p>
 * Modification History: <br> - 2024/4/27 AlfredOrlando domain对象配置 <br>
 */
@ApiModel(value = "cn-l13z-domain-Config")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "config")
public class Config {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 配置规则类型
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "配置规则类型")
    @NotBlank
    private String type;

    /**
     * 配置规则代码
     */
    @TableField(value = "code")
    @ApiModelProperty(value = "配置规则代码")
    @NotBlank
    private String code;

    /**
     * 配置规则名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "配置规则名称")
    @NotBlank
    private String name;

    /**
     * 配置规则描述
     */
    @TableField(value = "`desc`")
    @ApiModelProperty(value = "配置规则描述")
    private String desc;

    /**
     * 配置值
     */
    @TableField(value = "value")
    @ApiModelProperty(value = "配置值")
    @NotBlank
    private String value;

    /**
     * 创建时间
     */
    @TableField(value = "created", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date created;
}
