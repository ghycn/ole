package com.etiansoft.ole.page;

import java.util.Collections;
import java.util.List;

public class Page {

	private int total;
	private List<?> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRows() {
		if (rows == null) {
			return Collections.emptyList();
		}
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
