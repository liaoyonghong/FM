package com.versionsystem.common;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResponseMap<T> {

	public Map<String, Object> mapOK(List<T> items) {

		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("total", items.size());
		modelMap.put("data", items);
		modelMap.put("success", true);

		return modelMap;
	}

	public Map<String, Object> mapOK(List<T> items, long total) {

		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("total", total);
		modelMap.put("data", items);
		modelMap.put("success", true);

		return modelMap;
	}
	
	public Map<String, Object> mapOK(List<T> items, String message) {

		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("message", message);
		modelMap.put("data", items);
		modelMap.put("success", true);

		return modelMap;
	}
	public Map<String, Object> mapForDrug(List<T> items, String message,String orgCode,String voucherNo,String incurredDate,String doctorCode,String userId) {

		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("message", message);
		modelMap.put("data", items);
		if(userId!=null &&!"".equals(userId))
			modelMap.put("userId", userId);
		modelMap.put("orgCode", orgCode);
		if(doctorCode!=null && !"".equals(doctorCode))
			modelMap.put("doctorCode", doctorCode);
		modelMap.put("voucherNo", voucherNo);
		modelMap.put("orgCode", orgCode);
		modelMap.put("incurredDate", incurredDate);
		modelMap.put("success", true);

		return modelMap;
	}
	public Map<String, Object> mapForCheckDrug(List<T> items, List<String> message,String orgCode,String voucherNo,String incurredDate) {

		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		
		modelMap.put("data", items);
		modelMap.put("orgCode", orgCode);
		modelMap.put("voucherNo", voucherNo);
		modelMap.put("orgCode", orgCode);
		modelMap.put("incurredDate", incurredDate);
		if(message.size()>0){
			modelMap.put("success", false);
			modelMap.put("message", message);
		}else{
			modelMap.put("success", true);
			modelMap.put("message", "No data fault!");
		}

		return modelMap;
	}
	public Map<String, Object> mapForCheckDrug(List<T> items, String message,String orgCode,String voucherNo,String incurredDate) {

		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("message", message);
		modelMap.put("data", items);
		modelMap.put("orgCode", orgCode);
		modelMap.put("voucherNo", voucherNo);
		modelMap.put("orgCode", orgCode);
		modelMap.put("incurredDate", incurredDate);
		modelMap.put("success", true);

		return modelMap;
	}

	public Map<String, Object> mapOK(T item) {

		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("data", item);
		modelMap.put("success", true);

		return modelMap;
	}
	
	public Map<String, Object> mapOK(T item, String msg) {

		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("data", item);
		modelMap.put("message", msg);
		modelMap.put("success", true);
		
		return modelMap;
	}


	public  Map<String, Object> mapError(String msg) {

		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", false);

		return modelMap;
	}
	
	public  Map<String, Object> mapError(List<String> msgs) {

		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("message", msgs);
		modelMap.put("success", false);

		return modelMap;
	}
	public  Map<String, Object> mapOK(String msg) {

		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", true);

		return modelMap;
	}
}
