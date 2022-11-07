package com.versionsystem.persistence.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "APP_MENU_LOCALE")
@NamedQuery(name = "AppMenuLocale.findAll", query = "SELECT m FROM AppMenuLocale m")
public class AppMenuLocale {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String locale;

    @Column(name = "MENU_ID")
    private Long menuId;

    @Column(name = "CREATE_DATE")
    private Timestamp createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "LABEL")
    private String formLabel;

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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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

    public String getFormLabel() {
        return formLabel;
    }

    public void setFormLabel(String formLabel) {
        this.formLabel = formLabel;
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
}
