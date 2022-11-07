package com.versionsystem.app.menu.model;

import java.util.List;

public class MenuUI {
	private Long id = null;
	private Long roleMenuId = null;//非ADMIN角色的菜單ID，用於控制access用
	private String menuLabel = "";
	private String text = null;
	private String widget=null;
	private boolean hasChildren = false;
	private String cls = null;
	private int order;
	private String accessLock;
	private String sysRole;
	private String seqNo;
	private boolean checked;
	private String spriteCssClass;
	
	List<MenuUI> items;
	private boolean expanded;
	
	private String cnText;
	private String hkText;
	private String allowedAction;

	public Long getRoleMenuId() {
		return roleMenuId;
	}
	public void setRoleMenuId(Long roleMenuId) {
		this.roleMenuId = roleMenuId;
	}
	public String getAllowedAction() {
		return allowedAction;
	}
	public void setAllowedAction(String allowedAction) {
		this.allowedAction = allowedAction;
	}
	public String getCnText() {
		return cnText;
	}
	public void setCnText(String cnText) {
		this.cnText = cnText;
	}
	public String getHkText() {
		return hkText;
	}
	public void setHkText(String hkText) {
		this.hkText = hkText;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getSpriteCssClass() {
		return spriteCssClass;
	}
	public void setSpriteCssClass(String spriteCssClass) {
		this.spriteCssClass = spriteCssClass;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getAccessLock() {
		return accessLock;
	}
	public void setAccessLock(String accessLock) {
		this.accessLock = accessLock;
	}
	public String getSysRole() {
		return sysRole;
	}
	public void setSysRole(String sysRole) {
		this.sysRole = sysRole;
	}
	public String getWidget() {
		return widget;
	}
	public void setWidget(String widget) {
		this.widget = widget;
	}


	public List<MenuUI> getItems() {
		return items;
	}
	public void setItems(List<MenuUI> items) {
		this.items = items;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getMenuLabel() {
		return menuLabel;
	}

	public void setMenuLabel(String menuLabel) {
		this.menuLabel = menuLabel;
	}

	@Override
	public String toString() {
		return "MenuUI [id=" + id + ", text=" + text + ", widget=" + widget
				+ ", hasChildren=" + hasChildren + ", cls=" + cls + ", order=" + order
				+ ", accessLock=" + accessLock + ", sysRole=" + sysRole
				+ ", seqNo=" + seqNo + ", items=" + items + ", expanded="
				+ expanded + "]";
	}	

}
