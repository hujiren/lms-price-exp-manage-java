package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @Classname SysInnerOrgPo
 * @Date 2020/10/8 14:51
 */
@Data
@TableName("sys_inner_org")
@ApiModel(value="租户表-持久化对象", description="租户表-持久化对象")
public class SysInnerOrgPo extends Model<SysInnerOrgPo> {

    @ApiModelProperty(name = "id", value = "id", required = true)
    private Long id;

    @ApiModelProperty(name = "orgCode", value = "内部组织代码", required = true)
    private String orgCode;

    @ApiModelProperty(name = "orgName", value = "内部组织名称", required = true)
    private String orgName;

    @ApiModelProperty(name = "tenantGroup", value = "多租户分组", required = true)
    private String tenantGroup;

    @ApiModelProperty(name = "ownModule", value = "拥有模块")
    private String ownModule;

    @ApiModelProperty(name = "orgStatus", value = "组织状态")
    private Integer orgStatus;
}
