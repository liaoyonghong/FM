package com.versionsystem.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "CLINIC")
@NamedQuery(name = "Clinic.findAll", query = "SELECT c FROM Clinic c")
public class Clinic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "CODE")
	private String code;

	@Column(name = "NAME")
	private String name;

	@Column(name = "NAME_ZH")
	private String nameZh;

	@Column(name = "PAY_TO_ADDR")
	private String payToAddr;

	@Column(name = "IS_PANEL")
	private Boolean isPanel;

	@Column(name = "IS_HUM")
	private Boolean isHum;

	@Column(name = "CALL_B4_FAX")
	private Boolean callB4Fax;

	@Column(name = "CONSULTATION")
	private Boolean consultation;

	@Column(name = "STATUS")
	private Boolean status;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "CREATE_USER")
	private String createUser;

	@Column(name = "LAST_UPDATE_DATE")
	private Timestamp lastUpdateDate;

	@Column(name = "LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "DATE_FROM")
	private Timestamp dateFrom;

	@Column(name = "DATE_TO")
	private Timestamp dateTo;

	@Column(name = "AGREEMENT_SIGN_BY")
	private String agreementSignBy;

	@Column(name = "BG_COLOR")
	private String bgColor;

	@Column(name = "CLINIC_TYPE")
	private String diffClinicType;
	
	@Column(name = "BOOKING_FLAG")
	private Boolean bookingFlag = true;
	
	@Column(name = "REMIND_DAYS")
	private Integer remindDays;
	
	@Column(name = "CLINIC_IP")
	private String clinicIP;

	@Column(name = "INVOICE_PRE")
	private String invoicePre;
	
	@Column(name = "AUTO_GEN_CONSULT_FEE")
	private Boolean autoGenConsultFee;
	
	@Column(name = "CONSULT_FEE_CODE")
	private String consultFeeCode;

	@Column(name = "LABEL_DESTINATION")
	private String labelDestination;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameZh() {
		return nameZh;
	}

	public void setNameZh(String nameZh) {
		this.nameZh = nameZh;
	}

	public String getPayToAddr() {
		return payToAddr;
	}

	public void setPayToAddr(String payToAddr) {
		this.payToAddr = payToAddr;
	}

	public Boolean getIsPanel() {
		return isPanel;
	}

	public void setIsPanel(Boolean isPanel) {
		this.isPanel = isPanel;
	}

	public Boolean getIsHum() {
		return isHum;
	}

	public void setIsHum(Boolean isHum) {
		this.isHum = isHum;
	}

	public Boolean getCallB4Fax() {
		return callB4Fax;
	}

	public void setCallB4Fax(Boolean callB4Fax) {
		this.callB4Fax = callB4Fax;
	}

	public Boolean getConsultation() {
		return consultation;
	}

	public void setConsultation(Boolean consultation) {
		this.consultation = consultation;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Timestamp dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Timestamp getDateTo() {
		return dateTo;
	}

	public void setDateTo(Timestamp dateTo) {
		this.dateTo = dateTo;
	}

	public String getAgreementSignBy() {
		return agreementSignBy;
	}

	public void setAgreementSignBy(String agreementSignBy) {
		this.agreementSignBy = agreementSignBy;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getDiffClinicType() {
		return diffClinicType;
	}

	public void setDiffClinicType(String diffClinicType) {
		this.diffClinicType = diffClinicType;
	}

	public Boolean getBookingFlag() {
		return bookingFlag;
	}

	public void setBookingFlag(Boolean bookingFlag) {
		this.bookingFlag = bookingFlag;
	}

	public Integer getRemindDays() {
		return remindDays;
	}

	public void setRemindDays(Integer remindDays) {
		this.remindDays = remindDays;
	}

	public String getClinicIP() {
		return clinicIP;
	}

	public void setClinicIP(String clinicIP) {
		this.clinicIP = clinicIP;
	}

	public String getInvoicePre() {
		return invoicePre;
	}

	public void setInvoicePre(String invoicePre) {
		this.invoicePre = invoicePre;
	}

	public Boolean getAutoGenConsultFee() {
		return autoGenConsultFee;
	}

	public void setAutoGenConsultFee(Boolean autoGenConsultFee) {
		this.autoGenConsultFee = autoGenConsultFee;
	}

	public String getConsultFeeCode() {
		return consultFeeCode;
	}

	public void setConsultFeeCode(String consultFeeCode) {
		this.consultFeeCode = consultFeeCode;
	}

	public String getLabelDestination() {
		return labelDestination;
	}

	public void setLabelDestination(String labelDestination) {
		this.labelDestination = labelDestination;
	}
}