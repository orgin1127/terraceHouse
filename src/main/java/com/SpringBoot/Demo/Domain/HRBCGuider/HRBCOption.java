package com.SpringBoot.Demo.Domain.HRBCGuider;

import java.util.ArrayList;

public class HRBCOption {

	String OptionName;
	String alias;
	ArrayList<String> optionValueList;
	ArrayList<String> aliasValueList;

	public HRBCOption(String optionName, String alias, ArrayList<String> optionValueList,
			ArrayList<String> aliasValueList) {
		super();
		OptionName = optionName;
		this.alias = alias;
		this.optionValueList = optionValueList;
		this.aliasValueList = aliasValueList;
	}

	public HRBCOption() {
		super();
	}

	public String getOptionName() {
		return OptionName;
	}

	public void setOptionName(String optionName) {
		OptionName = optionName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public ArrayList<String> getOptionValueList() {
		return optionValueList;
	}

	public void setOptionValueList(ArrayList<String> optionValueList) {
		this.optionValueList = optionValueList;
	}

	public ArrayList<String> getAliasValueList() {
		return aliasValueList;
	}

	public void setAliasValueList(ArrayList<String> aliasValueList) {
		this.aliasValueList = aliasValueList;
	}

	@Override
	public String toString() {
		return "HRBCOption [OptionName=" + OptionName + ", alias=" + alias + ", optionValueList=" + optionValueList
				+ ", aliasValueList=" + aliasValueList + "]";
	}

}
