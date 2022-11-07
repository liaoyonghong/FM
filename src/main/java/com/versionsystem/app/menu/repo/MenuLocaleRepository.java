package com.versionsystem.app.menu.repo;

import com.versionsystem.persistence.model.AppMenuLocale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MenuLocaleRepository extends JpaRepository<AppMenuLocale, Long> {

    AppMenuLocale findByMenuIdAndLocale(long meunId, String locale);

    @Query(value = "SELECT LABEL FROM APP_MENU_LOCALE where MENU_ID=? AND LOCALE=?", nativeQuery = true)
    String getLabelByMenuId(long meunId, String locale);

    @Query(value = "SELECT LABEL FROM APP_MENU_LOCALE WHERE MENU_ID=(SELECT ID FROM APP_MENU m WHERE m.PATH=?1 AND ROWNUM=1) AND LOCALE=?2", nativeQuery = true)
    String getLabel(String path, String locale);
}
