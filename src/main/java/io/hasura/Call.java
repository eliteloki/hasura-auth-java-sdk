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

    public T execute() throws HasuraException {
        try {
            return parseResponse(rawCall.execute());
        }
        catch (IOException e) {
            throw new HasuraException(1, "Connection error : ", e);
        }
    }

    T parseResponse(okhttp3.Response rawResponse) throws HasuraException {
        int code = rawResponse.code();

        if (code == 200) {
            return parseJson(rawResponse, bodyType);
        }
        else {
            HasuraErrorResponse err = parseJson(rawResponse, HasuraErrorResponse.class);
            throw new HasuraException(code, err.getMessage());
        }
    }

    <R> R parseJson(okhttp3.Response response, Type bodyType) throws HasuraException {
        int code = response.code();
        try {
            String rawBody = response.body().string();
            System.out.println(rawBody);
            return gson.fromJson(rawBody, bodyType);
        }
        catch (JsonSyntaxException e) {
            String msg = "FATAL : JSON strucutre not as expected. Schema changed maybe? : " + e.getMessage();
            throw new HasuraException(code, msg, e);
        }
        catch (JsonParseException e) {
            String msg = "FATAL : Server didn't return vaild JSON : " + e.getMessage();
            throw new HasuraException(code, msg, e);
        }
        catch (IOException e) {
            String msg = "FATAL : Decoding response body failed : " + e.getMessage();
            throw new HasuraException(code, msg, e);
        }
    }

    public void cancel() {
        rawCall.cancel();
    }

    public boolean isCancelled() {
        return rawCall.isCanceled();
    }
}
