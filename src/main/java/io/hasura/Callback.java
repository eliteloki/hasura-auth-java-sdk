package io.hasura.core;

public interface Callback<T> {
  void onSuccess(T response);
  void onFailure(HasuraException e);
}
