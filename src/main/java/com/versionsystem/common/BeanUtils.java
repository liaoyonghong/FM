package com.versionsystem.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public abstract class BeanUtils extends org.springframework.beans.BeanUtils {


	public static void copyProperties(Object source, Object target) throws BeansException {

		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);

						if (value == null) {
							String type = readMethod.getReturnType().getName();
							if ("java.lang.String".equals(type))
								value = "";
							else if ("java.math.BigDecimal".equals(type))
								value = BigDecimal.ZERO;
						}
//						if (value != null) {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
//						}
					} catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}

	public static void copyProperties1(Object source, Object target) throws BeansException {

		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);

						if (value == null) {
							String type = readMethod.getReturnType().getName();
							if ("java.lang.String".equals(type))
								value = "";
//							else if ("java.math.BigDecimal".equals(type))
//								value = BigDecimal.ZERO;
						}
						if (value != null) {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					} catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static <T> T convertMap(Class<T> clazz, Map map) {

		BeanUtils.pretreatment(clazz, map);

		T obj = null;
		try {
			obj = clazz.newInstance(); // 创建 JavaBean 对象
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性
			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (map.containsKey(propertyName)) {
					// 调试时下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
					Object value = map.get(propertyName);
					Object[] args = new Object[1];
					args[0] = value;
					descriptor.getWriteMethod().invoke(obj, args);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 值比较测试用例
	 *
	 * @param args
	 */
//	public static void main(String[] args) {
//		CmsVioPmiMember c = new CmsVioPmiMember();
//		CmsPmiMemberUI ui = new CmsPmiMemberUI();
//		c.setSurName("123");
//		ui.setSurName("123");
//		ui.setSex("123");
//		c.setSex("123");
//		c.setId(123);
//		ui.setId(1233);
//		System.out.println("args = [" + compare(c, ui, new String[]{"SurName", "Sex", "SurName"}) + "]");
//	}

	/**
	 * 值比较
	 *
	 * @param o1     被比较对象
	 * @param o2     比较对象
	 * @param fields 比较值列表
	 * @return 比较值是否全部一致
	 */
	public static boolean compare(Object o1, Object o2, String[] fields) {
		try {
			for (String field : fields) {
				Object compareValue = o1.getClass().getMethod("get" + field).invoke(o1);
				Object value = o2.getClass().getMethod("get" + field).invoke(o2);
				if (compareValue == null) {
					if (value == null) {
						continue;
					}
					if (value.equals("")) {
						continue;
					}
					return false;
				} else if (compareValue == "" && value == null) {
					continue;
				}
				if (compareValue.equals(value)) {
					continue;
				}
				if (compareValue instanceof Date
						&& ObjectConverter.cleanTime(ObjectConverter.convertToUTCForBack((Date) compareValue)).getTime() == ObjectConverter.cleanTime((Date) value).getTime()) {
					continue;
				}
				return false;
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ApplicationException("Bean compare fail");
		}
	}

	/**
	 *
	 * @param o1 比较对象，例如PO
	 * @param o2 被比较对象，例如UI
	 * @param arr 比较的列
	 * @return 差异列
	 */
	public static String getDifferenceFiled(Object o1, Object o2, List<String> arr) {
		StringJoiner builder = new StringJoiner(",");
		try {
			for (String key : arr) {
				String field = key;
				String displayField = field;
				int labelIndex = key.indexOf(":");
				if (labelIndex > -1) {
					field = key.substring(0, labelIndex);
					displayField = key.substring(labelIndex + 1);
					if (key.endsWith(":")) {
						displayField = field.replaceAll("\\B(?=[A-Z])", " ");
						displayField = displayField.replaceAll("^[a-z]", displayField.substring(0, 1).toUpperCase());
					}
				}
				Field compareField = o1.getClass().getDeclaredField(field);
				compareField.setAccessible(true);
				Object compareValue = compareField.get(o1);
				Field valueField = o2.getClass().getDeclaredField(field);
				valueField.setAccessible(true);
				Object value = valueField.get(o2);
				//				Object compareValue = o1.getClass().getMethod("get" + field).invoke(o1);
				//				Object value = o2.getClass().getMethod("get" + field).invoke(o2);
				if (compareValue == null) {
					if (value == null) {
						continue;
					}
					if (value.equals("")) {
						continue;
					}
					builder.add(displayField);
					continue;
				} else if ("".equals(compareValue) && value == null) {
					continue;
				}
				if (compareValue.equals(value)) {
					continue;
				}
				if (compareValue instanceof Date
						&& ObjectConverter.cleanTime(ObjectConverter.convertToUTCForBack((Date) compareValue)).getTime() == ObjectConverter.cleanTime((Date) value).getTime()) {
					continue;
				}
				builder.add(displayField);
			}
		} catch (Exception ex){
			ex.printStackTrace();
			throw new ApplicationException("Bean compare fail");
		}
		return builder.toString();
	}

	@SuppressWarnings({"rawtypes"})
	private static <T> void pretreatment(Class<T> clazz, Map map) {

		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			String fieldName = field.getName();
			Object mo = map.get(fieldName);
			if (mo == null)
				continue;
			if (field.getType() == Date.class) {
				if (mo instanceof Number) {
					mo = new Date((Long) mo);
				}
			} else if (Number.class.isAssignableFrom(field.getType())) {
				if (mo instanceof String || mo instanceof Character) {
					if (field.getType() == BigDecimal.class) {
						mo = BigDecimal.valueOf(Long.valueOf((String) mo));
					} else if (field.getType() == Integer.class) {
						mo = Integer.valueOf((String) mo);
					} else if (field.getType() == Short.class) {
						mo = Short.valueOf((String) mo);
					}
				}
			}
			map.put(fieldName, mo);
		}
	}

	public static Method findMethod(Class classes, String method, Class... type) {
		return findMethod(classes, method, type);
	}

	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (!checkIP(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (!checkIP(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (!checkIP(ip)) {
			ip = request.getRemoteHost();
		}
		if (!checkIP(ip)) {
			ip = request.getRemoteAddr();
		}

		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
	}

	public static boolean checkIP(String ip) {
		
		if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip) || ip.split("\\.").length != 4) {
			return false;
		}
		return true;
	}


}
