package com.newland.mybatis.handler;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ListTypeHandler<T> extends BaseTypeHandler<List<T>> {
    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.setSerializationInclusion(Include.NON_NULL);
    }
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<T> ts, JdbcType jdbcType) throws SQLException {
    	if(ts.size()>0){
			StringBuffer result = new StringBuffer();
			for (T value : ts) {
				result.append(value).append(",");
			}
			result.deleteCharAt(result.length() - 1);
			ps.setString(i, result.toString());
		}else{
			ps.setString(i, null);
		}
	}

	@Override
	public List<T> getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
		return toArray(resultSet.getString(columnName));
	}

	@Override
	public List<T> getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
		return toArray(resultSet.getString(columnIndex));
	}

	@Override
	public List<T> getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
		return toArray(callableStatement.getString(columnIndex));
	}

	List<T> toArray(String columnValue) {
		if (columnValue == null) {
			return createArray(0);
		}
		String[] values = columnValue.split(",");
		List<T> array = new ArrayList<>();
		for (int i = 0; i < values.length; i++) {
			array.add(parseString(values[i]));
		}
		return array;
	}

	List<T> createArray(int size) {
		return new ArrayList<>();
	}

	abstract T parseString(String value);

}