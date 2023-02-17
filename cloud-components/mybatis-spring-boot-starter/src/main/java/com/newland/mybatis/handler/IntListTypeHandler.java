package com.newland.mybatis.handler;

public class IntListTypeHandler extends ListTypeHandler<Integer> {
	@Override
	Integer parseString(String value) {
		return Integer.parseInt(value);
	}
}
