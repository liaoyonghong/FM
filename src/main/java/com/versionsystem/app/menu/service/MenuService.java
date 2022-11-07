package com.versionsystem.app.menu.service;

import com.versionsystem.app.menu.model.MenuUI;
import com.versionsystem.app.menu.repo.MenuLocaleRepository;
import com.versionsystem.app.menu.repo.MenuRepository;
import com.versionsystem.basic.user.UserService;
import com.versionsystem.common.ResponseMap;
import com.versionsystem.persistence.model.AppMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class MenuService {

    @Autowired
    private MenuRepository repository;

    @Autowired
    private MenuLocaleRepository localeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseMap<MenuUI> extJS;

    public Map<String, Object> findAll() {

        List<MenuUI> rl = new ArrayList<>();
        String locale = "en-US";
        List<AppMenu> l = repository.findAll(new Sort(Sort.Direction.ASC, "line"));

        MenuUI vo;
        for (AppMenu o : l) {
            vo = new MenuUI();
            vo.setCls("folder");
            vo.setId(o.getId());
            vo.setHasChildren(o.getLeaf() != 1);
            vo.setSeqNo(o.getSeqNo());
            vo.setOrder(o.getDispalySeqNo().intValue());
            vo.setSysRole(o.getSysRole());
            vo.setWidget(o.getMenuName());
            vo.setText(localeRepository.getLabel(o.getSeqNo(), locale));
            boolean existed = false;
            for (MenuUI temp : rl) {
                if (temp.isHasChildren() && vo.getSeqNo().contains(temp.getSeqNo()) && !vo.isHasChildren()) {
                    temp.getItems().add(vo);
                    temp.setExpanded(true);
                    existed = true;
                    break;
                }
            }
            if (!existed) {
                if (vo.isHasChildren() && vo.getItems() == null)
                    vo.setItems(new ArrayList<>());
                rl.add(vo);
            }
        }

        return extJS.mapOK(rl);
    }

    public List<MenuUI> findTreeMenu() {
        List<MenuUI> rl = new ArrayList<>();
        String locale = "en-US";
        String userId = userService.getCurrentUser();
        List<AppMenu> showL = repository.findAll();
        showL.sort(Comparator.comparing(AppMenu::getSeqNo));
        for (AppMenu o : showL) {
            if (("admin".equals(userId) && "downloads".equals(o.getMenuName()))
                    || (!"admin".equals(userId) && "edit".equals(o.getMenuName()))) {
                continue;
            }
            MenuUI vo = getMenuUI(o);
            vo.setChecked(true);
            vo.setText(localeRepository.getLabel(o.getSeqNo(), locale));
            vo.setMenuLabel(vo.getText());
            rl.add(vo);
        }
        return rl;
    }

    private MenuUI getMenuUI(AppMenu o) {
        MenuUI vo;
        vo = new MenuUI();
        vo.setCls("folder");
        vo.setId(o.getId());
        vo.setHasChildren(o.getLeaf() != 1);
        if (vo.isHasChildren())
            vo.setExpanded(true);
        vo.setSeqNo(o.getSeqNo());
        vo.setOrder(o.getDispalySeqNo().intValue());
        vo.setSysRole(o.getSysRole());
        vo.setWidget(o.getMenuName());
        return vo;
    }

}
