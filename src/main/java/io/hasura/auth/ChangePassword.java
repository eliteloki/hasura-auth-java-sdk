package io.hasura.auth;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ChangePassword {
    @SerializedName("password")
    String password;
    @SerializedName("new_password")
    String new_password;
    @SerializedName("info")
    JsonObject info;

    public void setInfo(JsonObject info) {
        this.info = info;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}
