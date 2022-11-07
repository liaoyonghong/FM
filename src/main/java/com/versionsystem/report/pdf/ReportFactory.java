package com.versionsystem.report.pdf;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.stereotype.Component;

/**
 * waiting complete
 */
@Component
public class ReportFactory {

	public static void main(String[] args) throws Exception {
		PdfDocumentImpl pdf = new PdfDocumentImpl(new PdfWriter("asd.pdf"));
		Document document = pdf.getDocument();
		Paragraph title = new Paragraph("Doctor List");
		title.setFontSize(20);
		title.setTextAlignment(TextAlignment.CENTER);
		document.add(title);
		DataTable table = new DataTable(6);
		table.setKeepTogether(true);
		table.setWidth(PageSize.A4.getHeight() - 20);
		table.addHeaderCell("Specialty");
		table.addHeaderCell("Dr Name");
		table.addHeaderCell("Address");
		table.addHeaderCell("Phone No");
		table.addHeaderCell("Area-District");
		table.addHeaderCell("Office Hours");
		for (int i = 0; i < 318; i++) {
			StrBuilder cell = new StrBuilder();
			for (int j = 0; j < Math.random() * 100; j++) {
				cell.append(j + "" + (char) ('啊' + j));
			}
			Paragraph paragraph = new Paragraph(cell.toString());
			paragraph.setKeepTogether(true);
			table.addCell(new Cell().add(paragraph));
		}
		Paragraph total = new Paragraph("Record Total: " + table.getNumberOfRows());
		total.setFontSize(16);
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
