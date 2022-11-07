package com.versionsystem.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Long key TABLE common basic services
 * <br />
 * e.g.
 * {@link com.versionsystem.vcms.document.impl.DocumentService}
 *
 * @param <UI>   Model class
 * @param <PO>   POJO class
 * @param <REPO> Repository interface
 * @author Tian hao
 */
public abstract class StandardService<UI extends BasicUI, PO, REPO extends JpaRepository<PO, Long>>
		extends BasicService<UI, PO, Long, REPO> {

}