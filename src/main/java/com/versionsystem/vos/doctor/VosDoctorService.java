package com.versionsystem.vos.doctor;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.versionsystem.common.DataList;
import com.versionsystem.common.DataMap;
import com.versionsystem.common.WhereJoiner;
import com.versionsystem.report.pdf.DataTable;
import com.versionsystem.report.pdf.PdfDocumentImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Service
public class VosDoctorService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private HttpServletResponse response;

	public DataList read(DataMap map) {
		DataList maps = new DataList();
		maps.setConvertFunction(dataMap -> {
			dataMap.merge("speciality", "\n", "cSpecialty");
			dataMap.merge("name", "\n", "cName", "\n", "qual1", "\n", "qual2", "\n", "qual3");
			dataMap.merge("area", "-", "district", "\n", "cArea", "-", "cDistrict");
			dataMap.put("district", dataMap.val("area").replaceAll("-$", ""));
			dataMap.merge("address1", "\n", "address2", "\n", "address3", "\n",
				"cAddress1", "\n", "cAddress2", "\n", "cAddress3", "\n");
			dataMap.put("address", StringUtils.chomp(dataMap.remove("address1").toString().replaceAll("\n+", "\n")));
			dataMap.merge("date1", " ", "clinicHr1", " ", "clinicHr11", " ", "clinicHr12", "\n",
				"date2", " ", "clinicHr2", " ", "clinicHr21", " ", "clinicHr22", "\n",
				"date3", " ", "clinicHr3", " ", "clinicHr31", " ", "clinicHr32", "\n",
				"date4", " ", "clinicHr4", " ", "clinicHr41", " ", "clinicHr42");
			dataMap.convert("speciality", "specialty");
			dataMap.convert("date1", "officeHours");
			if (dataMap.val("panel").equals("VIO")) {
				dataMap.put("panel", "Vio");
				dataMap.put("panelColor", "#990066");
			} else if (dataMap.val("panel").equals("HMMP")) {
				dataMap.put("panelColor", "#00AA00");
			}
			return dataMap;
		});
		map.convert("specialty", "speciality");
		WhereJoiner joiner = new WhereJoiner(map);
		joiner.set("VTC_FLAG='Y'");
		joiner.set("PANEL<>'QHMS'");
		joiner.like("%name%", "UPPER(NAME || C_NAME) LIKE UPPER(?)");
		joiner.add("speciality");
		joiner.add("area");
		joiner.add("district");
		joiner.like("%address%", "(address1 || address2 || address3 || c_address1 || c_address2 || c_address3) LIKE ?");
		joiner.like("%district%");
		joiner.like("%telphone%");
		maps.parse(jdbcTemplate.queryForList("SELECT * FROM VOS_DOCTOR_INFO " + joiner.sql(), joiner.paramValueList()));
		return maps;
	}

	public DataList specialty() {
		DataList maps = new DataList();
		maps.parse(jdbcTemplate.queryForList("SELECT SPECIALITY || ' ' || C_SPECIALTY text,speciality value FROM VOS_DOCTOR_INFO \n" +
			" WHERE VTC_FLAG = 'Y' AND SPECIALITY IS NOT NULL" +
			" GROUP BY SPECIALITY, C_SPECIALTY" +
			" ORDER BY SPECIALITY, C_SPECIALTY"));
		return maps;
	}

	public DataList area() {
		DataList maps = new DataList();
		maps.parse(jdbcTemplate.queryForList("SELECT AREA || ' ' || C_AREA text,area value FROM VOS_DOCTOR_INFO" +
			" WHERE VTC_FLAG = 'Y' AND AREA IS NOT NULL" +
			" GROUP BY AREA, C_AREA" +
			" ORDER BY AREA, C_AREA"));
		return maps;
	}

	public DataList district(DataMap map) {
		DataList maps = new DataList();
		maps.parse(jdbcTemplate.queryForList("SELECT V.DISTRICT || ' ' || V.C_DISTRICT text,district value FROM VOS_DOCTOR_INFO V" +
			" WHERE VTC_FLAG='Y' AND" +
			" V.AREA = NVL(?, V.AREA)" +
			" AND (V.AREA IS NOT NULL OR V.DISTRICT IS NOT NULL)" +
			" GROUP BY V.DISTRICT, V.C_DISTRICT" +
			" ORDER BY V.DISTRICT, V.C_DISTRICT", map.val("area")));
		return maps;
	}

	public void pdf(DataList dataList) throws Exception {
		PdfDocumentImpl pdf = PdfDocumentImpl.get(response.getOutputStream());
		Document document = pdf.getDocument();
		Paragraph title = new Paragraph("Doctor List");
		title.setFontSize(20);
		title.setTextAlignment(TextAlignment.CENTER);
		document.add(title);
		DataTable table = new DataTable(6);
		table.setKeepTogether(true);
		table.setWidth(PageSize.A4.getHeight() - 20);
		table.addHeaderCell("Specialty");
		table.addHeaderCell("Dr Name", 120);
		table.addHeaderCell("Address");
		table.addHeaderCell("Phone No", 60);
		table.addHeaderCell("Area-District");
		table.addHeaderCell("Office Hours");
		table.fill(dataList, e ->
			Arrays.asList(e.val("specialty") + (e.isEmpty("panel") ? "" : "\n(" + e.val("panel") + ")"), e.val("name"), e.val("address"), e.val("telphone"), e.val("district"), e.val("officeHours")));
		Paragraph total = new Paragraph("Record Total: " + table.getNumberOfRows());
		total.setFontSize(14);
		total.setTextAlignment(TextAlignment.CENTER);
		table.apply(document);
		int numberOfPages = pdf.getNumberOfPages();
		document.showTextAligned(new Paragraph(String.format("Page: 1 of %s", numberOfPages)),
			10, PageSize.A4.getWidth() - 55, 1, TextAlignment.LEFT, VerticalAlignment.TOP, 0);
		for (int i = 2; i <= numberOfPages; i++) {
			document.showTextAligned(new Paragraph(String.format("Page: %s of %s", i, numberOfPages)),
				10, PageSize.A4.getWidth() - 10, i, TextAlignment.LEFT, VerticalAlignment.TOP, 0);
		}
		document.add(total);
		document.close();
	}
}
