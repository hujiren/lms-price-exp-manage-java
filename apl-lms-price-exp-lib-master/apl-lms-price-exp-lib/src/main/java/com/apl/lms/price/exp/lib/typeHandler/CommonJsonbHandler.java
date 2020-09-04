package com.apl.lms.price.exp.lib.typeHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CommonJsonbHandler  extends BaseTypeHandler<Object> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object obj, JdbcType jdbcType) throws SQLException {
        PGobject jsonObject = new PGobject();
        jsonObject.setType("json");
        jsonObject.setValue(objectMapper.writeValueAsString(obj));
        preparedStatement.setObject(i, jsonObject);
    }


    @Override
    public List getNullableResult(ResultSet rs, String columnName) throws SQLException {

        return null;

        /*String data = rs.getString(columnName);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//忽略未知属性
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);//驼峰映射

        List customerGroupsIdList = objectMapper.readValue(data, List.class);

        return customerGroupsIdList;*/
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
