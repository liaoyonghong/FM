package com.versionsystem.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "APP_MENU_ACCESS_NAME")
@NamedQuery(name = "AppMenuAccessName.findAll", query = "SELECT a FROM AppMenuAccessName a")
public class AppMenuAccessName implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PARAMETER_KEY")
    private String parameterKey;

    private String name;

    @Column(name = "NAME_L2")
    private String nameL2;

    @Column(name = "NAME_L3")
    private String nameL3;

    @Column(name = "CREATE_DATE")
    private Timestamp createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;

    @Column(name = "LAST_UPDATE_USER")
    private String lastUpdateUser;

    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameL2() {
        return nameL2;
    }

    public void setNameL2(String nameL2) {
        this.nameL2 = nameL2;
    }

    public String getNameL3() {
        return nameL3;
    }

    public void setNameL3(String nameL3) {
        this.nameL3 = nameL3;
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
