package com.versionsystem.basic.parameter;

import com.versionsystem.common.DataMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Parameter")
public class ParameterController {

	@Autowired
	private ParameterService parameterService;
	private final Logger logger = LogManager.getLogger(ParameterController.class);

	@RequestMapping(value = "/readSystemParas")
	public DataMap readSystemParas() {
		try {
			return parameterService.readSystemParas();
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

}
