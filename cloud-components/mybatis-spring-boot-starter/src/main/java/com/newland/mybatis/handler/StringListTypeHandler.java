package com.newland.mybatis.handler;

public class StringListTypeHandler extends ListTypeHandler<String> {

	@Override
	String parseString(String value) {
		return value;
	}

}
