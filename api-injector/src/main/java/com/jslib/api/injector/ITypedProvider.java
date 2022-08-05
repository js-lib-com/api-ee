package com.jslib.api.injector;

import jakarta.inject.Provider;

public interface ITypedProvider<T> extends Provider<T>
{
  default Class<? extends T> type()
  {
    return null;
  };
}
