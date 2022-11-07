package com.versionsystem.common;

import com.google.common.base.CaseFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DataList extends ArrayList<DataMap> {

	Function<DataMap, DataMap> function;

	public DataList() {
	}

	public void parse(List<Map<String, Object>> map) {
		for (Map<String, Object> objectMap : map) {
			add(convertItem(objectMap));
		}
	}

	private DataMap convertItem(Map<String, Object> item) {
		DataMap result = new DataMap();
		for (String key : item.keySet()) {
			result.append(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key), item.get(key));
		}
		if (function != null){
			return function.apply(result);
		}
		return result;
	}

	/**
	 * 对field做一些转换设置
	 */
	public void setConvertFunction(Function<DataMap, DataMap> function) {
		this.function = function;
	}

	/**
	 * 只返回一个field为内容的数组
	 * @param field 这个field的列名
	 */
	public List<String> singleValList(String field) {
		List<String> result = new ArrayList<>();
		setConvertFunction(dataMap -> {
			result.add(dataMap.val(field));
			return null;
		});
		return result;
	}

}
