package com.apl.lms.price.exp.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author hjr start
 * @date 2020-08-18 17:33
 */
@Data
public class CustomerGroupDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long customerGroupsId;

    private String customerGroupsName;
}
