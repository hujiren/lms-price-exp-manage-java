package com.apl.lms.price.exp.lib.typeHandler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class CommonJsonbHandler  extends BaseTypeHandler<Object> {

    private static  ObjectMapper objectMapper = null;
    {
        objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object obj, JdbcType jdbcType) throws SQLException {
        PGobject jsonObject = new PGobject();
        jsonObject.setType("json");
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        String val = objectMapper.writeValueAsString(obj);
        if(null!=val && val.contains("_")){
            log.debug("====================下划线==="+val);
        }
        jsonObject.setValue(val);
        preparedStatement.setObject(i, jsonObject);
    }


    @SneakyThrows
    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {

        String data = rs.getString(columnName);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//忽略未知属性
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);//驼峰映射

        List customerGroupIdList = objectMapper.readValue(data, List.class);

        return customerGroupIdList;
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
