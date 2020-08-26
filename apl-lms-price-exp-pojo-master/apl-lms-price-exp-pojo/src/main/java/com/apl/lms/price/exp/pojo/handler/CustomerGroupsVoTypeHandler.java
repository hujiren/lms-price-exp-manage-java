package com.apl.lms.price.exp.pojo.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/6/11 - 10:10
*/
@Component
public class CustomerGroupsVoTypeHandler extends BaseTypeHandler<List> {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List customerGroupsIdList, JdbcType jdbcType) throws SQLException {
        PGobject jsonObject = new PGobject();
        jsonObject.setType("json");
        jsonObject.setValue(customerGroupsIdList.toString());
        preparedStatement.setObject(i, jsonObject);
    }

    @SneakyThrows
    @Override
    public List getNullableResult(ResultSet rs, String columnName) throws SQLException {

//        String ids = rs.getString("customer_groups_id");
//        String names = rs.getString("customer_groups_name");
//
//        List<CustomerGroupInfo> list = new ArrayList<>();
//
//        String[] idArr = ids.replace("[", "").replace("]", "").split(",");
//        String[] nameArr = names.split(",");
//        for (int i = 0; i < idArr.length; i++) {
//            CustomerGroupInfo customerGroupInfo = new CustomerGroupInfo();
//            customerGroupInfo.setCustomerGroupsId(Long.parseLong(idArr[i]));
//            customerGroupInfo.setCustomerGroupsName(nameArr[i]);
//            list.add(customerGroupInfo);
//        }
//
        return null;
    }


    @Override
    public List getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public List getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }

    class CustomerGroupInfo{
        Long customerGroupsId;
        String customerGroupsName;

        public Long getCustomerGroupsId() {
            return customerGroupsId;
        }

        public void setCustomerGroupsId(Long customerGroupsId) {
            this.customerGroupsId = customerGroupsId;
        }

        public String getCustomerGroupsName() {
            return customerGroupsName;
        }

        public void setCustomerGroupsName(String customerGroupsName) {
            this.customerGroupsName = customerGroupsName;
        }
    }

}
