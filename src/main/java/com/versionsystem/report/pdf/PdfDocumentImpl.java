package com.versionsystem.report.pdf;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class PdfDocumentImpl extends PdfDocument {

	private Document document;

	private static final Logger logger = LogManager.getLogger(PdfDocumentImpl.class);

	public PdfDocumentImpl(PdfWriter writer) {
		super(writer);
		document = new Document(this, PageSize.A4.rotate(), false);
		document.setMargins(10, 10, 10, 10);
		try {
			PdfFont font = PdfFontFactory.createFont("AdobeHeitiStd-Regular.otf", PdfEncodings.IDENTITY_H);
			document.setFont(font);
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	public static PdfDocumentImpl get(OutputStream output) {
		return new PdfDocumentImpl(new PdfWriter(output));
	}

	//	@Override
//	public PdfPage addNewPage() {
//		PdfPage pdfPage = super.addNewPage();
//		document.add(new Paragraph("Page: " + getNumberOfPages() + " of $PDF_PARAM_TOTAL_PAGE"));
//		return pdfPage;
//	}

	public Document getDocument() {
		return document;
	}

}
