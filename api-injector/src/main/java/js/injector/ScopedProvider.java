package js.injector;

import java.lang.annotation.Annotation;

import jakarta.inject.Provider;

/**
 * Scoped provider is a cache for instances used when certain conditions are met. It wraps a provisioning provider that
 * is in the end responsible for instance creation. If instance does not exists into scope provider cache it delegates
 * provisioning provider to create a new one.
 * 
 * @author Iulian Rotaru
 */
public abstract class ScopedProvider<T> implements Provider<T>
{
  private Provider<T> provisioningProvider;

  /**
   * Construct this scoped provider instance and initialize its provisioning provider. Because is not allowed to nest
   * the scoped providers, throws illegal argument if given provisioning provider argument is a scoped provider
   * instance.
   * 
   * @param provisioningProvider
   * @throws IllegalArgumentException if provisioning provider argument is a scoped provider instance.
   */
  protected ScopedProvider(Provider<T> provisioningProvider)
  {
    setProvisioningProvider(provisioningProvider);
  }

  /**
   * Set or update provisioning provider used by this scoped provider instance. Because is not allowed to nest the
   * scoped providers, throws illegal argument if given provisioning provider argument is a scoped provider instance.
   * 
   * @param provisioningProvider provisioning provider.
   * @throws IllegalArgumentException if provisioning provider argument is a scoped provider instance.
   */
  public void setProvisioningProvider(Provider<T> provisioningProvider)
  {
    if(provisioningProvider instanceof ScopedProvider) {
      throw new IllegalArgumentException("Cannot nest scoped providers.");
    }
    this.provisioningProvider = provisioningProvider;
  }

  public Provider<T> getProvisioningProvider()
  {
    return provisioningProvider;
  }

  public abstract Class<? extends Annotation> getScope();

  public abstract T getScopeInstance();

}