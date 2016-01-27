package io.hasura.auth;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 34crossandriod on 20/01/16.
 */
public class Message {
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }
}
