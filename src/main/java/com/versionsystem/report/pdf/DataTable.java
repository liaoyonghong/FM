package com.versionsystem.report.pdf;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.versionsystem.common.DataList;
import com.versionsystem.common.DataMap;

import java.util.List;
import java.util.function.Function;

public class DataTable extends Table {

	private Document document;
	private int numberOfPages;

	public DataTable(int numColumns) {
		super(numColumns);
	}

	@Override
	public Table getHeader() {
		Table header = super.getHeader();
		if (header != null) {
			header.setMarginTop(20);
		}
		return header;
	}

	public void apply(Document document) {
		this.document = document;
		document.add(this);
	}

	public void fill(DataList list, Function<DataMap, List<String>> fn) {
		list.forEach(e -> fn.apply(e).forEach(item -> {
			Paragraph paragraph = new Paragraph(item);
			paragraph.setFontSize(10);
			paragraph.setKeepTogether(true);
			addCell(new Cell().add(paragraph));
		}));
	}

	public void addHeaderCell(String value, int width) {
		Cell cell = new Cell();
		cell.add(new Paragraph(value));
		cell.setWidth(width);
		addHeaderCell(cell);
	}
}
