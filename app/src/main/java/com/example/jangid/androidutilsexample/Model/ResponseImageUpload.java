package com.example.jangid.androidutilsexample.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseImageUpload{

	@SerializedName("data")
	@Expose
	private ImageUploadData data;

	@SerializedName("message")
	@Expose
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(ImageUploadData data){
		this.data = data;
	}

	public ImageUploadData getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}