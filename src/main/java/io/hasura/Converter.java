package io.hasura.core;

import java.io.IOException;

public interface Converter<T, E extends Exception> {
    public T fromResponse(okhttp3.Response r) throws E;
    public E fromIOException(IOException e);
    public E castException(Exception e);
}
