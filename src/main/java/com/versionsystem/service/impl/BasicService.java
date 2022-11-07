package com.versionsystem.service.impl;

import com.versionsystem.basic.user.UserService;
import com.versionsystem.common.ApplicationErrorCode;
import com.versionsystem.common.ApplicationException;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

/**
 * <pre>
 * 1.UI
 * 2.PO
 * 3.ID
 * 4.Repository
 *
 * automatic field
 * createUser
 * createDate
 * lastUpdateUser
 * lastUpdateDate
 * </pre>
 * <p>
 * <br />
 *
 * @param <UI> Model class
 * @param <PO> POJO class
 * @param <ID> Primary Key
 * @param <REPO>  Repository interface
 * @author Tian hao
 */
@Service
public abstract class BasicService<UI extends BasicUI, PO, ID extends Serializable, REPO extends JpaRepository<PO, ID>> {

    @Autowired
    public UserService userService;

    @Autowired
    public HttpServletRequest request;

    @Autowired
    public REPO repository;

    protected final Logger logger = LogManager.getLogger(getClass());

    /**
     * <pre>
     * 补充剩余的字段
     * 从PO复制字段到UI
     * createUser
     * createDate
     * lastUpdateUser
     * lastUpdateDate
     * </pre>
     *
     * @param ui
     * @param po
     * @return
     */
    public UI supplement(PO po, UI ui) {
        try {
            if (ui == null || po == null) {
                return null;
            }
            Object createUser = po.getClass().getMethod("getCreateUser").invoke(po);
            if (createUser != null && createUser.toString().length() > 1) {
                ui.setCreateUser(createUser.toString());
            }

            Object lastUpdateUser = po.getClass().getMethod("getLastUpdateUser").invoke(po);
            if (lastUpdateUser != null && lastUpdateUser.toString().length() > 1) {
                ui.setLastUpdateUser(lastUpdateUser.toString());
            }

            Object createDate = po.getClass().getMethod("getCreateDate").invoke(po);
            if (createDate instanceof Timestamp) {
                ui.setCreateDate((Timestamp) createDate);
            }

            Object lastUpdateDate = po.getClass().getMethod("getLastUpdateDate").invoke(po);
            if (lastUpdateDate instanceof Timestamp) {
                ui.setLastUpdateDate((Timestamp) lastUpdateDate);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        return ui;
    }

    public PO supplement(UI ui, PO po) {
        try {
            if (ui == null || po == null) {
                return null;
            }

            String createUser = ui.getCreateUser();
            if (StringUtils.isNotBlank(createUser)) {
                po.getClass().getMethod("setCreateUser", String.class).invoke(po, createUser);
            }

            String lastUpdateUser = ui.getLastUpdateUser();
            if (StringUtils.isNotBlank(lastUpdateUser)) {
                po.getClass().getMethod("setLastUpdateUser", String.class).invoke(po, lastUpdateUser);
            }

            Timestamp createDate = ui.getCreateDate();
            if (createDate != null) {
                po.getClass().getMethod("setCreateDate", Timestamp.class).invoke(po, createDate);
            }

            Timestamp lastUpdateDate = ui.getLastUpdateDate();
            if (lastUpdateDate != null) {
                po.getClass().getMethod("setLastUpdateDate", Timestamp.class).invoke(po, lastUpdateDate);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        return po;
    }

    /**
     * 获取有setLastUpdateIP字段的实体并且更新它<br />
     * <font color="red">与saveAndSyncIP联用是多余的</font>
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unused")
    public PO getRemoteAddrEntity(ID id) {
        PO po = repository.findOne(id);
        try {
            if (po == null) {
                return null;
            }
            po.getClass().getMethod("setLastUpdateIP", String.class).invoke(po, request.getRemoteAddr());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        return po;
    }

    /**
     * 保存有setLastUpdateIP字段的实体并且更新它
     *
     * @param po
     * @return
     */
    public PO saveAndSyncIP(PO po) {
        try {
            po.getClass().getMethod("setLastUpdateIP", String.class).invoke(po, request.getRemoteAddr());
            repository.saveAndFlush(po);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        return po;
    }

    /**
     * 保存前验证
     */
    public void validateBeforeSave(ID id) {
        if (repository.exists(id))
            throw new ApplicationException(
                    new ApplicationErrorCode("APP3001", "Validation Error:Failed to add new record!Duplicated Object!")
                            .toString());
    }

    /**
     * 将ui的值传递给po
     * <pre>
     *     po.setXX(ui.getXX());
     *     return super.builderPO(po);
     * </pre>
     */
    public PO builderPO(PO po, UI ui) {
        return supplement(ui, po);
    }

    /**
     * 每次应当构建一个新UI,并且将PO中的内容赋值过去
     * <pre>
     *     UI ui = supplement(po, new UI());
     *     ui.setXX(po.getXX());
     *     return ui;
     * </pre>
     *
     * @param po 已经在数据库真实存在
     * @return
     */
    public UI builderUI(PO po) {
        return null;
    }

    /**
     * 构造UI并保存
     *
     * @param po 应保存的PO
     * @param ui 更换PO的值
     * @return
     */
    @Transactional
    public UI save(PO po, UI ui) {
        if (po == null || ui == null) {
            return null;
        }
        return builderUI(repository.saveAndFlush(builderPO(po, ui)));
    }

    /**
     * <p>保存一个原本不存在的PO</p>
     * service.saveNewPO(ui.getId(), ui, new PO());
     *
     * @param id 根据此ID判断PO以前是否存在(自动ID传0即可)
     * @param ui PO的值
     * @param po new PO()
     * @return
     */
    public UI saveNewPO(ID id, UI ui, PO po) {
        ui.init(userService.getCurrentUser());
        return save(supplement(po, id), ui);
    }

    /**
     * <p>保存一个原本不存在的PO</p>
     * service.saveNewPO(ui.getId(), ui, new PO());
     *
     * @param ui PO的值
     * @param po new PO()
     * @return
     */
    public UI saveNewPO(UI ui, PO po) {
        ui.init(userService.getCurrentUser());
        try {
            supplement(po);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        return save(po, ui);
    }

    /**
     * <pre>
     * 验证ID是否已存在,自动ID传0,并且初始化一个不存在的PO,更新基础字段
     * createUser
     * createDate
     * lastUpdateUser
     * lastUpdateDate
     * </pre>
     *
     * @param po
     * @return
     */
    public PO supplement(PO po, ID id) {
        try {
            if (id != null && !(id instanceof Long) && !(id instanceof Integer)) {
                validateBeforeSave(id);
            }
            supplement(po);
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return null;
        }
        return po;
    }

    public PO supplement(PO po) {
        try {
            if (po == null) {
                return null;
            }
            po.getClass().getMethod("setCreateUser", String.class).invoke(po, userService.getCurrentUser());
            po.getClass().getMethod("setCreateDate", Timestamp.class).invoke(po, new Timestamp(new Date().getTime()));
            po.getClass().getMethod("setLastUpdateUser", String.class).invoke(po, userService.getCurrentUser());
            po.getClass().getMethod("setLastUpdateDate", Timestamp.class).invoke(po, new Timestamp(new Date().getTime()));
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return null;
        }
        return po;
    }

    /**
     * 依据此ID找出PO，然后更新它
     *
     * @param id 依据此ID找出PO
     * @param ui 依据builderPO更新PO
     * @return ui 依据builderUI返回UI
     */
    public UI update(ID id, UI ui) {
        ui.update(userService.getCurrentUser());
        return save(getPO(id), ui);
    }

    /**
     * 全查询
     *
     * @return
     */
    public List<UI> findAllUI() {
        return convertList(repository.findAll());
    }

    public List<UI> convertList(Iterable<PO> pos) {
        if (pos == null) {
            return Collections.emptyList();
        }
        //@see ArrayList.DEFAULT_CAPACITY
        int length = 10;
        if (pos instanceof List) {
            length = ((List) pos).size();
        }
        List<UI> uis = new ArrayList<>(length);
        for (PO po : pos) {
            uis.add(builderUI(po));
        }
        return uis;
    }

    public Page<UI> convertList(Page<PO> pos, Pageable pageable) {
        if (pos == null) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        return new PageImpl<>(convertList(pos.getContent()), pageable, pos.getTotalElements());
    }

    /**
     * 获取一个已存在PO,并且更新它的setLastUpdateUser与setLastUpdateDate
     *
     * @param id
     * @return
     */
    private PO getPO(ID id) {
        PO po = repository.findOne(id);
        try {
            if (po == null) {
                return null;
            }
            po.getClass().getMethod("setLastUpdateUser", String.class).invoke(po, userService.getCurrentUser());
            po.getClass().getMethod("setLastUpdateDate", Timestamp.class).invoke(po,
                    new Timestamp(new Date().getTime()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("update exception:", e);
            throw new RuntimeException("update exception:" + id);
        }
        return po;
    }

    /**
     * 销毁前更新(维护日志)
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unused")
    @Transactional
    public boolean destroyAndSync(ID id) {
        if (notRealId(id)) {
            return false;
        }
        PO po = repository.findOne(id);
        if (po == null) {
            return false;
        }
        try {
            po.getClass().getMethod("setLastUpdateIP", String.class).invoke(po, request.getRemoteAddr());
            po.getClass().getMethod("setLastUpdateUser", String.class).invoke(po,
                    "D:" + userService.getCurrentUser());
            po.getClass().getMethod("setLastUpdateDate", Timestamp.class).invoke(po,
                    new Timestamp(new Date().getTime()));
            repository.saveAndFlush(po);

            repository.delete(po);
            return true;
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 销毁前更新(维护日志)
     *
     * @param id
     * @return
     */
    @Transactional
    public boolean updateDestroy(ID id) {
        if (notRealId(id)) {
            return false;
        }
        PO po = repository.findOne(id);
        if (po == null) {
            return false;
        }
        try {

            po.getClass().getMethod("setLastUpdateUser", String.class).invoke(po, "D:" + userService.getCurrentUser());
            po.getClass().getMethod("setLastUpdateDate", Timestamp.class).invoke(po,
                    new Timestamp(new Date().getTime()));
            repository.save(po);

            repository.delete(po);
            return true;
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 直接销毁
     *
     * @param id
     */
    @Transactional
    public boolean destroy(ID id) {
        if (notRealId(id)) {
            return false;
        }
        PO po = repository.findOne(id);
        if (po != null) {
            repository.delete(po);
            return true;
        }
        return false;
    }

    private boolean notRealId(ID id) {
        if (id instanceof Long) {
            return Long.parseLong(id.toString()) == 0;
        }
        if (id instanceof Character) {
            return StringUtils.isBlank(id.toString());
        }
        return id instanceof Long && Long.parseLong(id.toString()) == 0;
    }

    public PageRequest pageable(Map<String, Object> request) {
//		DataMap dataMap = new DataMap(request);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        if (request.get("sort") != null && ((List<?>) request.get("sort")).size() > 0) {
            Map<String, String> sortMap = ((List<Map<String, String>>) request.get("sort")).get(0);
            sort = new Sort(Sort.Direction.fromStringOrNull(sortMap.get("dir").toUpperCase()), sortMap.get("field"), "id");
        }
        if (request.get("pageSize") != null && !request.get("pageSize").toString().equals("0")) {
            return new PageRequest((Integer) request.get("page"), (Integer) request.get("pageSize"), sort);
        }
        int pageNum = 0;
        if (request.get("page") != null) {
            pageNum = Integer.parseInt(request.get("page").toString());
        }
        return new PageRequest(pageNum, (int) repository.count(), sort);
    }

    /**
     * 删除子表并更新子表的lastUpdate Date&User
     *
     * @param childRepository 子表源
     * @param delPO           子集
     */
    @Transactional
    public <DPO> void delChild(JpaRepository<DPO, ?> childRepository, List<DPO> delPO) {
        if (delPO == null || delPO.isEmpty()) {
            return;
        }
        try {
            for (DPO item : delPO) {
                item.getClass().getMethod("setLastUpdateDate", Timestamp.class).invoke(item,
                        new Timestamp(new Date().getTime()));
                item.getClass().getMethod("setLastUpdateUser", String.class).invoke(item, userService.getCurrentUser());
                childRepository.save(item);
                childRepository.delete(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("can't delete:" + delPO.get(0).getClass());
        }
    }


    /**
     * 删除子表并更新子表的lastUpdate Date&User&IP
     *
     * @param childRepository 子表源
     * @param delPO           子集
     */
    @SuppressWarnings("unused")
    @Transactional
    public <DPO> void delChildIP(JpaRepository<DPO, ?> childRepository, List<DPO> delPO) {
        if (delPO == null || delPO.isEmpty()) {
            return;
        }
        try {
            for (DPO item : delPO) {
                item.getClass().getMethod("setLastUpdateIP", String.class).invoke(item, request.getRemoteAddr());
                item.getClass().getMethod("setLastUpdateDate", Timestamp.class).invoke(item,
                        new Timestamp(new Date().getTime()));
                item.getClass().getMethod("setLastUpdateUser", String.class).invoke(item, userService.getCurrentUser());
                childRepository.saveAndFlush(item);
                childRepository.delete(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("can't delete:" + delPO.get(0).getClass());
        }
    }

    /**
     * 删除一项关联数据
     *
     * @param itemRepository
     * @param item
     */
    @SuppressWarnings("unused")
    @Transactional
    public <DPO> void delItem(JpaRepository<DPO, ?> itemRepository, DPO item) {
        if (item == null) {
            return;
        }
        try {
            item.getClass().getMethod("setLastUpdateDate", Timestamp.class).invoke(item,
                    new Timestamp(new Date().getTime()));
            item.getClass().getMethod("setLastUpdateUser", String.class).invoke(item, userService.getCurrentUser());
            itemRepository.saveAndFlush(item);
            itemRepository.delete(item);
        } catch (Exception e) {
            logger.error("can't delete:" + item.getClass());
        }
    }

}