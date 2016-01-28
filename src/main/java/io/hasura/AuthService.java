package io.hasura;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.CookieManager;

import io.hasura.auth.*;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AuthService {

    private static final Gson gson = new GsonBuilder().create();
    public static final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient httpClient;
    private String dbUrl;
    private CookieManager cookieManager;



    public AuthService(String dbUrl, CookieManager cookieManager) {
        this.dbUrl = dbUrl;
        this.httpClient =
            new OkHttpClient.Builder()
                            .cookieJar(new JavaNetCookieJar(cookieManager))
                            .build();
    }

    public OkHttpClient getClient(){
        return this.httpClient;
    }

    public String getUrl(){
        return this.dbUrl;
    }

    private <T> Call<T, AuthException> mkCall(String url, String jsonBody, Type bodyType) {
        RequestBody reqBody = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
            .url(this.dbUrl + url)
            .post(reqBody)
            .build();
        return new Call<>(httpClient.newCall(request), new AuthResponseConverter<T>(bodyType));
    }

    public Call<User, AuthException> register(RegisterRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<User>() {}.getType();
        return mkCall("/auth/signup", jsonBody, respType);
    }

    public Call<User, AuthException> login(LoginRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<User>() {}.getType();
        return mkCall("/auth/login", jsonBody, respType);
    }



    public Call<Message, AuthException> verifyOTP(VerifyOtp r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<Message>() {}.getType();
        return mkCall("/auth/confirm_mobile", jsonBody, respType);
    }

    public Call<Message, AuthException> resendOTP(ResendOtp r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<Message>() {}.getType();
        return mkCall("/auth/resend_otp_mobile", jsonBody, respType);
    }

    public Call<Message, AuthException> logout() {
        String jsonBody = gson.toJson("");
        Type respType   = new TypeToken<Message>() {}.getType();
        return mkCall("/auth/logout", jsonBody, respType);
    }

    public Call<User, AuthException> credentials() {
        String jsonBody = gson.toJson("");
        Type respType   = new TypeToken<User>() {}.getType();
        return mkCall("/auth/get_credentials", jsonBody, respType);
    }

    public Call<Message, AuthException> changePassword(ChangePassword r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<Message>() {}.getType();
        return mkCall("/auth/change_password", jsonBody, respType);
    }

    public Call<Message, AuthException> forgotPassword(ForgotPassword r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<Message>() {}.getType();
        return mkCall("/auth/forgot_password", jsonBody, respType);
    }

    public Call<Message, AuthException> resetPassword(ResetPassword r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<Message>() {}.getType();
        return mkCall("/auth/reset_password", jsonBody, respType);
    }

    public Call<Message, AuthException> changeMobile(ChangeMobile r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<Message>() {}.getType();
        return mkCall("/auth/change_mobile", jsonBody, respType);
    }

    public Call<Message, AuthException> changeUserName(ChangeUserName r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<Message>() {}.getType();
        return mkCall("/auth/change_username", jsonBody, respType);
    }

    public Call<Message, AuthException> resendEmail(ResendEmail r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<Message>() {}.getType();
        return mkCall("/auth/resend_verify_email", jsonBody, respType);
    }

    public Call<Message, AuthException> confirmEmail(ConfirmEmail r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<Message>() {}.getType();
        return mkCall("/auth/confirm_email", jsonBody, respType);
    }

    public Call<Message, AuthException> changeEmail(ChangeEmail r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<Message>() {}.getType();
        return mkCall("/auth/change_email", jsonBody, respType);
    }

    public Call<Message, AuthException> deleteAccount(DeleteAccount r) {
        String jsonBody = gson.toJson(r);
        Type respType   = new TypeToken<Message>() {}.getType();
        return mkCall("/auth/delete_account", jsonBody, respType);
    }



}
