package com.versionsystem.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * HashMap 拓展
 */
public class DataMap extends HashMap<String, Object> {

	private static final String template = "<${key}>${value}</${key}>";

	private static final long serialVersionUID = 12L;

	private String splitting = "\n";

	private Logger logger = LogManager.getLogger(DataMap.class);

	public DataMap() {
	}

	public DataMap(Map<String, ?> maps) {
		putAll(maps);
	}

	/**
	 * @param list 如果是List&lt;FilterRequest&gt;将自动转换<br />
	 *             如果不是,必须有一个getId方法作为Map的Key
	 */
	public DataMap(List<?> list) {
		putList(list);
	}

	/**
	 * @param obj 字段为key,字段值为value
	 */
	public DataMap(Object obj) {
		putObject(obj);
	}

	public DataMap(String[] keys, Object[] values) {
		for (int i = 0; i < (keys.length <= values.length ? keys.length : values.length); i++) {
			put(keys[i], values[i]);
		}
	}

	@Deprecated
	public static DataMap createSingleValMap(String key,Object val){
		DataMap map = new DataMap();
		map.put(key, val);
		return map;
	}

	/**
	 * 创建一个单个值的DataMap
	 */
	public static DataMap newSingleItemMap(String key, Object val) {
		DataMap map = new DataMap();
		map.put(key, val);
		return map;
	}

	public List<Object> valueSet() {
		return new ArrayList<>(values());
	}

	public void putObject(Object obj) {
		if (obj == null) {
			return;
		}
		if (obj.getClass().getClassLoader() == null) {
			throw new ApplicationException("Convert Error: Not Class Load:" + obj.getClass());
		}
		try {
			Method[] methods = obj.getClass().getMethods();
			for (Method method : methods) {
				String name = method.getName();
				Class type = method.getReturnType();
				if (name.startsWith("get") && name.length() >= 5 && !name.equals("getClass") && !type.equals(List.class) && !type.getName().startsWith("com.versionsystem")) {
					name = name.substring(3);
					put(name.substring(0, 1).toLowerCase() + name.substring(1), method.invoke(obj));
				}
			}
		} catch (Exception e) {
			throw new ApplicationException("Convert Object to DataMap failed");
		}
	}

	public DataMap putList(List<?> list) {
		if (list == null || list.isEmpty()) {
			return this;
		}
		try {
			for (Object item : list) {
				Object id = item.getClass().getMethod("getId").invoke(item);
				if (id == null) {
					continue;
				}
				put(id.toString(), item);
			}
		} catch (Exception e) {
			throw new ApplicationException("Convert List to DataMap failed");
		}
		return this;
	}

	public void marge(DataMap dataMap) {
		if (dataMap == null || dataMap.isEmpty()) {
			return;
		}
		putAll(dataMap);
	}

	/**
	 * 将dataMap本身作为(原值,替换值)映射,替换所有对应项
	 *
	 * @param content
	 * @return
	 */
	public String replace(String content) {
		for (String item : keySet()) {
			content = content.replace(item, val(item));
		}
		return content;
	}

	/**
	 * 是否包含key并且有值
	 */
	public boolean has(String key) {
		return containsKey(key) && get(key) != null;
	}

	/**
	 * 是否没有值或者是空串
	 */
	public boolean isEmpty(String key) {
		return !has(key) || get(key).toString() == null || (get(key).toString()).isEmpty();
	}

	public String val(String key) {
		return value(key).toString();
	}

	public String val(String key, String replace) {
		String value = val(key);
		if (value == null || value.isEmpty()) {
			return replace;
		}
		return value;
	}

	public Object value(String key) {
		if (!has(key)) {
			return "";
		}
		return get(key);
	}

	public void appendBigDecimal(String key, BigDecimal appendVal){
		put(key, bigDecimal(key).add(appendVal));
	}

	public int integer(String key) {
		if (isEmpty(key)) {
			return 0;
		}
		return Integer.parseInt(val(key));
	}

	public Long longer(String key) {
		if (isEmpty(key)) {
			return 0L;
		}
		return Long.parseLong(val(key));
	}

	public Date date(String key) {
		if (isEmpty(key)) {
			return null;
		}
		return ObjectConverter.parseDateTime(val(key));
	}

	public Timestamp timestamp(String key) {
		if (isEmpty(key)) {
			return null;
		}
		return new Timestamp(ObjectConverter.parseDateTime(val(key)).getTime());
	}

	public BigDecimal bigDecimal(String key) {
		if (isEmpty(key)) {
			return new BigDecimal(0);
		}
		return new BigDecimal(val(key));
	}


	/**
	 * is null or false
	 *
	 * @param key
	 * @return
	 */
	public boolean bool(String key) {
		return !val(key).isEmpty() && Boolean.parseBoolean(val(key));
	}

	public double doubled(String key) {
		if (val(key).isEmpty()) {
			return 0;
		}
		return Double.parseDouble(val(key));
	}

	/**
	 * @param param 待验证值
	 * @param value 若param is blank,return value
	 */
	public static String blank(Object param, String value) {
		return param == null || param.toString().isEmpty() ? value : param.toString();
	}

	public DataMap convert(String before, String after) {
		put(after, remove(before));
		return this;
	}

	public <T> T convert(Class<T> obj) {
		try {
			T result = obj.newInstance();
			for (Method method : obj.getMethods()) {
				String item = method.getName();
				String key = item.substring(3);
				key = key.substring(0, 1).toLowerCase() + key.substring(1);
				if (!item.startsWith("set") || isEmpty(key)) {
					continue;
				}
				Object value = get(key);
				Class<?> parameter = method.getParameterTypes()[0];
				switch (parameter.getTypeName()) {
					case "java.math.BigDecimal":
						value = bigDecimal(key);
						break;
					case "java.sql.Timestamp":
						value = new Timestamp(date(key).getTime());
						break;
					case "java.util.Date":
						value = date(key);
						break;
				}
				try {
					method.invoke(result, value);
				} catch (Exception ex){
					logger.error(ex);
					System.out.println(method);
				    throw ex;
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Convert Object to DataMap failed");
		}
	}

	public DataMap append(String key, Object value) {
		put(key, value);
		return this;
	}

	public Object put(Long key, Object value) {
		if (key == null) {
			return null;
		}
		return super.put(key.toString(), value);
	}

	public Object get(Long key) {
		return super.get(key + "");
	}

	public DataMap getDataMap(String key) {
		if (!has(key)) {
			return new DataMap();
		}
		Object val = get(key);
		if (val instanceof LinkedHashMap) {
			return new DataMap((LinkedHashMap) get(key));
		}
		return (DataMap) get(key);
	}

	public String toXml() {
		StrBuilder xmlContent = new StrBuilder();
		for (Entry<String, Object> item : entrySet()) {
			xmlContent
					.append(template.replace("${key}", item.getKey()).replace("${value}", item.getValue().toString()));
		}
		return xmlContent.toString();
	}

	public String toDataXml() {
		return "<data>" + toXml() + "</data>";
	}

	public DataMap tidy() {
		if (isEmpty()) {
			return null;
		}
		return this;
	}

	public static List<DataMap> toList(List<?> array) {
		if (array == null || array.isEmpty()) {
			return Collections.emptyList();
		}
		List<DataMap> maps = new ArrayList<>(array.size());
		for (Object item : array) {
			maps.add(new DataMap(item));
		}
		return maps;
	}

	/**
	 * 合并两个key <br/>
	 * e.g.
	 * <pre>
	 * DataMap map = new DataMap();
	 * map.append("a", "1").append("b", "2");
	 * map.merge("a", "b")
	 * map值为{"a":"1\n2"}
	 * </pre>
	 * @param benchmark 合并的key
	 * @param pivot 被合并的key / 单个字符, 如果值是空的就不合并了
	 */
	public void merge(String benchmark, String... pivot) {
		if (pivot == null) {
			return;
		}
		for (String item : pivot) {
			if (item.length() == 1) {
				put(benchmark, val(benchmark) + item);
				continue;
			}
			if (StringUtils.isEmpty(val(item))) {
				continue;
			}
			put(benchmark, val(benchmark) + get(item));
			remove(item);
		}
	}

}
