package io.hasura.auth;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("user_id")
    int hasuraId;

    @SerializedName("hasura_role")
    String hasuraRole;

    public int getHasuraId() {
        return hasuraId;
    }

    public String getHasuraRole() {
        return hasuraRole;
    }
}
