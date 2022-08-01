package js.injector;

import jakarta.inject.Provider;

public interface IProvisionInvocation<T>
{

  static <T> IProvisionInvocation<T> create(final Provider<? extends T> provider, final T instance)
  {
    return new IProvisionInvocation<T>()
    {
      @Override
      public Provider<? extends T> provider()
      {
        return provider;
      }

      @Override
      public T instance()
      {
        return instance;
      }
    };
  }

  Provider<? extends T> provider();

  T instance();

}
