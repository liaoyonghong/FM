package com.versionsystem.app.menu.repo;

import com.versionsystem.persistence.model.AppMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

public interface MenuRepository extends JpaRepository<AppMenu, Long> {

    List<AppMenu> findBySysRole(String sysRole);
    List<AppMenu> findBySeqNoStartingWith(String seqNo);
    List<AppMenu> findBySeqNoAndSysRole(String seqNo,String sysRole);
    List<AppMenu> findBySysRoleOrderByDispalySeqNoAsc(String sysRole);
    List<AppMenu> findBySysRoleAndMenuTypeAndModuleCodeOrderByDispalySeqNoAsc(String sysRole, String menuType, String moduleCode);
    List<AppMenu> findBySysRoleOrderBySeqNoAsc(String sysRole);

    @Query("select h from AppMenu h where h.sysRole=?1 and (h.moduleCode=?2 or h.moduleCode is null) "
            + " and (?3 is null or h.menuType=?3) "
            + " order by h.dispalySeqNo asc")
    List<AppMenu> getByRoleModuleType(String sysRole, String moduleCode, String menuType);

}
