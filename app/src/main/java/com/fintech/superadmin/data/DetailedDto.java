package com.fintech.superadmin.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetailedDto implements Serializable {

	@SerializedName("report_title")
	private String reportTitle;

	@SerializedName("report_data")
	private String reportData;

	@SerializedName("trans_data")
	private String transData;

	@SerializedName("comm_data")
	private String commData;

	@SerializedName("api_response")
	private Object apiResponse;

	public void setReportTitle(String reportTitle){
		this.reportTitle = reportTitle;
	}

	public String getReportTitle(){
		return reportTitle;
	}

	public void setReportData(String reportData){
		this.reportData = reportData;
	}

	public String getReportData(){
		return reportData;
	}

	public void setTransData(String transData){
		this.transData = transData;
	}

	public String getTransData(){
		return transData;
	}

	public void setCommData(String commData){
		this.commData = commData;
	}

	public String getCommData(){
		return commData;
	}

	public void setApiResponse(Object apiResponse){
		this.apiResponse = apiResponse;
	}

	public Object getApiResponse(){
		return apiResponse;
	}

	@Override
 	public String toString(){
		return 
			"DetailedDto{" + 
			"report_title = '" + reportTitle + '\'' + 
			",report_data = '" + reportData + '\'' + 
			",trans_data = '" + transData + '\'' + 
			",comm_data = '" + commData + '\'' + 
			",api_response = '" + apiResponse + '\'' + 
			"}";
		}
}