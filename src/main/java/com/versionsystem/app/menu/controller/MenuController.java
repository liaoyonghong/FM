package com.versionsystem.app.menu.controller;

import com.versionsystem.app.menu.model.MenuUI;
import com.versionsystem.app.menu.service.MenuService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private MenuService menuService;
	private final Logger logger = LogManager.getLogger(MenuController.class);

	@RequestMapping(value = "/list")
	public Map<String, ?> list() {
		try {

			return menuService.findAll();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@RequestMapping(value = "/tree/forUser")
	public List<MenuUI> treeForUser() {
		try {
			return menuService.findTreeMenu();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return null;
		}
	}

	@RequestMapping(value = "/tree/forUser/{locale}")
	public List<MenuUI> treeForUser(@PathVariable String locale) {
		try {
			return menuService.findTreeMenu();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return null;
		}
	}

}
