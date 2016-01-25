package io.hasura.core;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import com.google.gson.*;
import java.lang.reflect.Type;

public class Call<T> {

    /* Underlying okhttp call */
    private okhttp3.Call rawCall;
    /* The type of the body */
    private final Type bodyType;

    private static final Gson gson = new GsonBuilder().create();

    public Call(okhttp3.Call rawCall, Type bodyType) {
        this.bodyType = bodyType;
        this.rawCall = rawCall;
    }

    public Request request() {
        return rawCall.request();
    }

    public void enqueue(final Callback<T> callback) {
        rawCall.enqueue(new okhttp3.Callback() {
                @Override public void onResponse(okhttp3.Call call, okhttp3.Response rawResponse)
                    throws IOException {
                    T response;
                    try {
                        response = parseResponse(rawResponse);
                    } catch (HasuraException he) {
                        callFailure(he);
                        return;
                    } catch (Throwable e) {
                        callFailure(new HasuraException(e));
                        return;
                    }
                    callSuccess(response);
                }

                @Override public void onFailure(okhttp3.Call call, IOException e) {
                    try {
                        callFailure(new HasuraException(e));
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }

                private void callFailure(HasuraException he) {
                    try {
                        callback.onFailure(he);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }

                private void callSuccess(T response) {
                    try {
                        callback.onSuccess(response);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            });
    }

    public boolean isExecuted() {
        return rawCall.isExecuted();
    }

    public T execute() throws HasuraException, IOException {
        return parseResponse(rawCall.execute());
    }

    T parseResponse(okhttp3.Response rawResponse) throws HasuraException, IOException {
        String rawBody = rawResponse.body().string();
        System.out.println(rawBody);

        int code = rawResponse.code();
        if (code == 200) {
            // TODO : try and catch json exceptions
            return gson.fromJson(rawBody, bodyType);
        }
        else {
            HasuraErrorResponse err = gson.fromJson(rawBody, HasuraErrorResponse.class);
            throw new HasuraException(err.getCode(), err.getMessage());
        }
    }

    public void cancel() {
        rawCall.cancel();
    }

    public boolean isCancelled() {
        return rawCall.isCanceled();
    }
}
