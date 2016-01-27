package io.hasura;

public interface Callback<T, E> {
  void onSuccess(T response);
  void onFailure(E e);
}
