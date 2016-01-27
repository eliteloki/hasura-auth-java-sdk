package io.hasura.auth;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;


public class VerifyOtp {

    @SerializedName("mobile")
    String mobile;
    @SerializedName("otp")
    long otp;
    @SerializedName("info")
    JsonObject info;

    public void setInfo(JsonObject info) {
        this.info = info;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setOtp(long otp) {
        this.otp = otp;
    }
}
