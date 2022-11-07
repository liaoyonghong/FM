package com.versionsystem.vos.news.controller;

import com.versionsystem.vos.news.impl.NewsService;
import com.versionsystem.vos.news.model.NewsUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
	@Autowired
	private NewsService service;
	private final Logger logger = LogManager.getLogger(NewsController.class);
	@RequestMapping(value = "/findAllNews")
	public List<NewsUI> findAllNews()throws Exception {
		try {
			return service.findAllNews();
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			throw e;
		}
	}
}
