package com.example.demo.core.infrastructure.mapper;


import com.example.demo.core.domain.enums.EnabledStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(EnabledStatus.class)
public class EnabledStatusTypeHandler extends BaseTypeHandler<EnabledStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EnabledStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public EnabledStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return EnabledStatus.fromValue(value);
    }

    @Override
    public EnabledStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        return EnabledStatus.fromValue(value);
    }

    @Override
    public EnabledStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        return EnabledStatus.fromValue(value);
    }
} 