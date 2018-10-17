package com.SpringBoot.Demo.Domain.HRBCGuider;

public class HRBCField {

	String fieldName;
	String dataType;

	public HRBCField(String fieldName, String dataType) {
		super();
		this.fieldName = fieldName;
		this.dataType = dataType;
	}

	public HRBCField() {
		super();
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return "HRBCField [fieldName=" + fieldName + ", dataType=" + dataType + "]";
	}

}
