package com.versionsystem.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "APP_MENU")
@NamedQuery(name = "AppMenu.findAll", query = "SELECT m FROM AppMenu m")
public class AppMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "ALLOW")
    private long allowedAction;

    @Column(name = "LINE")
    private BigDecimal dispalySeqNo;

    private long leaf;

    @Column(name = "ROUTE")
    private String menuName;

    @Column(name = "PATH")
    private String seqNo;

    @Column(name = "ROLE_ID")
    private String sysRole;

    @Column(name = "MODULE_CODE")
    private String moduleCode;

    @Column(name = "MENU_TYPE")
    private String menuType;

    @Column(name = "CREATE_DATE")
    private Timestamp createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;

    @Column(name = "LAST_UPDATE_USER")
    private String lastUpdateUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAllowedAction() {
        return allowedAction;
    }

    public void setAllowedAction(long allowedAction) {
        this.allowedAction = allowedAction;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public BigDecimal getDispalySeqNo() {
        return dispalySeqNo;
    }

    public void setDispalySeqNo(BigDecimal dispalySeqNo) {
        this.dispalySeqNo = dispalySeqNo;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public long getLeaf() {
        return leaf;
    }

    public void setLeaf(long leaf) {
        this.leaf = leaf;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getSysRole() {
        return sysRole;
    }

    public void setSysRole(String sysRole) {
        this.sysRole = sysRole;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }
}
