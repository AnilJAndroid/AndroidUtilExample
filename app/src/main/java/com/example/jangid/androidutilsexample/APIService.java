package com.example.jangid.androidutilsexample;

import com.example.jangid.androidutilsexample.Model.ParamImageUpload;
import com.example.jangid.androidutilsexample.Model.ResponseImageUpload;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {
    @Multipart
    @POST(Constant.UPLOAD_IMAGE_URL)
    Call<String> UploadUserProfile(@Part MultipartBody.Part file, @Part("restUserid") RequestBody id);

    @Multipart
    @POST(Constant.UPLOAD_IMAGE_URL)
    Call<ResponseImageUpload> UploadProfileImage(@Part RequestBody image);

    @GET(Constant.getSampleGetRequest)
    Call<String> getDataList();

    @GET("group/{id}/users")
    Call<String> groupList(@Path("id") int groupId);


}