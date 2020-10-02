package com.sinosoft.sinoep.wordTransfer.entity;

public enum WordTransferType {
	TEXT("text"),
	HTML("html"),
	PDF("pdf");
	
	public final String value;
	WordTransferType(String value){
		this.value =value;
	}
}
