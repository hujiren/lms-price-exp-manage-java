package com.apl.lms.price.exp.manage;

import com.apl.lms.price.exp.pojo.po.PriceExpListPo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author hjr start
 * @date 2020/6/11 - 10:10
 */
@Component
public class JsonTypeHandlerPg extends BaseTypeHandler<Object> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final PGobject PGOBJECT = new PGobject();
    @SneakyThrows
    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {

        String data = rs.getString(columnName);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//忽略未知属性
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);//驼峰映射

        PriceExpListPo pgData = MAPPER.readValue(data, PriceExpListPo.class);

        return pgData;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {

        if (preparedStatement != null) {

            PGOBJECT.setType("jsonb");
            PGOBJECT.setValue(o.toString());
            preparedStatement.setObject(i, PGOBJECT);
        }
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }


}
