package com.versionsystem.common;

import org.apache.commons.lang.text.StrBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 拼接出一个条件查询语句, 并生成它的参数列表
 * e.g.
 * <pre>
 * "where a=? AND b=? AND c=?"
 * Object[]{a,b,c}
 * </pre>
 */
public class WhereJoiner extends StrBuilder {

	private DataMap paramMap;
	private List<Object> paramValueList;

	public WhereJoiner(DataMap maps) {
		this.paramMap = maps;
		paramValueList = new ArrayList<>();
	}

	public void set(String sql) {
		addSql(sql);
	}

	public void add(String field) {
		add(field, field + " = ?");
	}

	public void add(String field, String sql) {
		Object paramObj = paramMap.get(field);
		if (paramObj == null || (paramObj instanceof String && ((String) paramObj).isEmpty())) {
			return;
		}
		addSql(sql);
		paramValueList.add(paramObj);
	}

	public void like(String field) {
		like(field, field.replaceAll("%", "") + " LIKE ?");
	}

	public void like(String field, String sql) {
		String param = paramMap.val(field.replaceAll("%", ""));
		if (param.isEmpty()) {
			return;
		}
		addSql(sql);
		paramValueList.add(field.replaceAll("\\w+", param));
	}

	private void addSql(String sql) {
		if (isEmpty()) {
			append("WHERE ");
		} else {
			append(" AND ");
		}
		append(sql);
	}

	public Object[] paramValueList() {
		return paramValueList.toArray();
	}

	public String sql() {
		return toString();
	}

}
