package com.versionsystem.vos.news.impl;

import com.versionsystem.common.BeanUtils;
import com.versionsystem.persistence.model.VosNews;
import com.versionsystem.vos.news.model.NewsUI;
import com.versionsystem.vos.news.repo.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
	@Autowired
	private NewsRepository repository;

	public List<VosNews> findAll(){
		return repository.findAll();
	}

	public List<NewsUI> findAllNews() {
		List<NewsUI> rl = new ArrayList<>();
		List<VosNews> l = repository.findAllByExpiryDate();
		for (VosNews temp : l) {
			NewsUI vo = buildUI(temp, new NewsUI());
			if (vo != null)
				rl.add(vo);
		}
		return rl;
	}
	private NewsUI buildUI(VosNews temp, NewsUI vo) {
		BeanUtils.copyProperties(temp, vo);
		return vo;
	}
}
