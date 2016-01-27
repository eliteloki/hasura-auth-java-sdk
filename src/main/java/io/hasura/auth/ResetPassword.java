package io.hasura.auth;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 34crossandriod on 14/01/16.
 */
public class ResetPassword {
    @SerializedName("password")
    String password;
    @SerializedName("token")
    String token;
    @SerializedName("info")
    JsonObject info;

    public void setInfo(JsonObject info) {
        this.info = info;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
