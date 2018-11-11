package com.example.jangid.androidutilsexample.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageUploadData {

	@SerializedName("restUserId")
	@Expose
	private String restUserId;

	@SerializedName("restaurantBanner")
	@Expose
	private String restaurantBanner;

	public void setRestUserId(String restUserId){
		this.restUserId = restUserId;
	}

	public String getRestUserId(){
		return restUserId;
	}

	public void setRestaurantBanner(String restaurantBanner){
		this.restaurantBanner = restaurantBanner;
	}

	public String getRestaurantBanner(){
		return restaurantBanner;
	}
}