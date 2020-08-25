package com.apl.lms.price.exp.manage.mybatisTypeHandler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
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
public class PriceExpCustomerGroupsIdTypeHandler extends BaseTypeHandler<List> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    //private static final PGobject PGOBJECT = new PGobject();


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List list, JdbcType jdbcType) throws SQLException {
        PGobject jsonObject = new PGobject();
        jsonObject.setType("json");
        jsonObject.setValue(list.toString());
        preparedStatement.setObject(i, jsonObject);
    }

    @SneakyThrows
    @Override
    public List getNullableResult(ResultSet rs, String columnName) throws SQLException {

        String data = rs.getString(columnName);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//忽略未知属性
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);//驼峰映射

        List customerGroupsIdList = objectMapper.readValue(data, List.class);

        return customerGroupsIdList;
    }


    
    @Override
    public List getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public List getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }


}
