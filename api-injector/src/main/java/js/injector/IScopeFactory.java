package js.injector;

import jakarta.inject.Provider;

/**
 * Factory for scoped providers that create reusable instances.
 * 
 * A scope has a cache and controls created instances life span. It is applied as a decorator on a provisioning
 * provider; remember that a provisioning provider always creates a new instance.
 * 
 * @author Iulian Rotaru
 * @param <T> generic instance type.
 */
public interface IScopeFactory<T>
{

  /**
   * Create a scoped provider by decorating given provisioning binding. A provisioning binding is one that define the
   * actual instance creation.
   * 
   * Because is not allowed to nest the scoped providers, this factory method throws illegal argument if given
   * provisioning binding define a provider that is already a scoped provider.
   * 
   * @param injector parent injector,
   * @param provisioningBinding provisioning binding, used for actual instances creation.
   * @return scoped provider.
   * @throws IllegalArgumentException if provider defined by provisioning binding argument is already a scoped provider.
   */
  Provider<T> getScopedProvider(IInjector injector, IBinding<T> provisioningBinding);

}
