package com.jslib.api.injector;

import java.lang.annotation.Annotation;

import jakarta.inject.Named;
import jakarta.inject.Provider;

/**
 * Injector facade deals with bindings configuration, instances creation and provisioning events.
 * 
 * @author Iulian Rotaru
 */
public interface IInjector
{

  static IInjector create(IModule... modules)
  {
    IInjector injector = Classes.loadService(IInjector.class);
    if(modules.length > 0) {
      injector.configure(modules);
    }
    return injector;
  }

  /**
   * Configure injector bindings from modules. Although public this interface is designed for internal use by factory
   * method, see {@link #create(IModule...)}.
   * 
   * Once configured, an injector instance is immutable. Implementation should throw illegal state if attempt to
   * configure an injector instance multiple times.
   * 
   * @param modules variable number of modules.
   * @return this pointer.
   * @throws IllegalStateException if attempt to configure an injector instance multiple times.
   */
  IInjector configure(IModule... modules);

  <T> IBindingBuilder<T> getBindingBuilder(Class<T> type);

  <T> IBindingBuilder<T> getBindingBuilder(Class<T> type, T instance);

  /**
   * Gets instance identified by given instance key. Depending on declared bindings, returned instance can be newly
   * created or reused from some scope cache. Anyway, a binding for requested instance key must exist; otherwise
   * unchecked provision exception is thrown.
   * 
   * @param key instance key composed from type and optional qualifier.
   * @return instance, newly created or reused from scope cache.
   * @param <T> generic instance type.
   * @throws ProvisionException if there is no bindings for requested instance key.
   */
  <T> T getInstance(Key<T> key);

  /**
   * Convenient alternative of {@link #getInstance(Key)} when instance qualifier is missing.
   * 
   * @param type instance type.
   * @return instance, newly created or reused from scope cache.
   * @param <T> generic instance type.
   * @throws ProvisionException if there is no bindings for requested instance type.
   */
  <T> T getInstance(Class<T> type);

  /**
   * Convenient alternative of {@link #getInstance(Key)} when instance qualifier is {@link Named}.
   * 
   * @param type instance type,
   * @param name instance name as declared by {@link Named} annotation value.
   * @return instance, newly created or reused from scope cache.
   * @param <T> generic instance type.
   * @throws ProvisionException if there is no bindings for requested instance type and name.
   */
  <T> T getInstance(Class<T> type, String name);

  <T> Provider<T> getProvider(Class<T> type);

  void bindListener(IProvisionListener provisionListener);

  void unbindListener(IProvisionListener provisionListener);

  <T> void fireEvent(IProvisionInvocation<T> provisionInvocation);

  <T> void bindScopeFactory(Class<? extends Annotation> scope, IScopeFactory<T> scopeFactory);

  <T> IScopeFactory<T> getScopeFactory(Class<? extends Annotation> scope);

}
