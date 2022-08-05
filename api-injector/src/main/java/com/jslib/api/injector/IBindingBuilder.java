package com.jslib.api.injector;

import java.lang.annotation.Annotation;
import java.net.URI;

import jakarta.inject.Provider;
import jakarta.inject.Scope;

/**
 * Chained builder used by module configuration to collect binding parameters.
 *
 * @param <T> binding generic type.
 * @author Iulian Rotaru
 */
public interface IBindingBuilder<T>
{

  /** Compatible version for {@link #with(Annotation)}. */
  default IBindingBuilder<T> annotatedWith(Annotation qualifier)
  {
    return with(qualifier);
  }

  /** Compatible version for {@link #with(Class)}. */
  default IBindingBuilder<T> annotatedWith(Class<? extends Annotation> qualifierType)
  {
    return with(qualifierType);
  }

  /**
   * Add binding type qualifier. Annotation value is used together with binding type to define instance key, see
   * {@link Key#get(Class, Annotation)}. This attribute is optional with default to null.
   * 
   * Given annotation qualifier should implement hash code and equal as described by {@link Annotation#hashCode()},
   * respective {@link Annotation#equals(Object)}.
   * 
   * @param qualifier qualifier annotation.
   * @return this pointer.
   */
  IBindingBuilder<T> with(Annotation qualifier);

  /** Variant of {@link #with(Annotation)} when type qualifier value is the annotation type itself. */
  IBindingBuilder<T> with(Class<? extends Annotation> qualifierType);

  /** Convenient variant for {@link #with(Annotation)} when qualifier annotation is <code>Names.named(name)</code>. */
  default IBindingBuilder<T> named(String name)
  {
    return with(Names.named(name));
  }

  /**
   * Set binding implementation class.
   * 
   * @param implementationClass
   * @return
   */
  IBindingBuilder<T> to(Class<? extends T> implementationClass);

  /** Compatible variant for {@link #to(Class)}. */
  default IBindingBuilder<T> toInstance(T instance)
  {
    return instance(instance);
  }

  IBindingBuilder<T> instance(T instance);

  /** Compatible variant for {@link #provider(Provider)}. */
  default IBindingBuilder<T> toProvider(Provider<T> provider)
  {
    return provider(provider);
  }

  IBindingBuilder<T> provider(Provider<T> provider);

  IBindingBuilder<T> provider(ITypedProvider<T> provider);

  IBindingBuilder<T> service();

  IBindingBuilder<T> on(URI implementationURL);

  /** Convenient variant for {@link #on(URI)} using URI string representation. */
  default IBindingBuilder<T> on(String implementationURL)
  {
    return on(URI.create(implementationURL));
  }

  /**
   * This attribute declares the instance life span. When using this attribute, a new {@link ScopedProvider} is created
   * that wraps current provider, as returned by {@link #getProvider()}. Parameter <code>scope</code> acts as a key to
   * select the scoped provider; the injector keeps a registry of scope providers with scope annotation as key.
   * 
   * Implementation should check if <code>scope</code> parameter has the {@link Scope} annotation on itself and throw
   * illegal argument if not.
   * 
   * It is not allowed to nest the scopes, so that if current provider is already a scoped provider, implementation
   * should throw illegal state.
   * 
   * @param scope scope annotation that has no attribute.
   * @return this pointer.
   * @throws IllegalStateException if current provider as returned by {@link #getProvider()} is already a scoped
   *           provider.
   */
  IBindingBuilder<T> in(Class<? extends Annotation> scope) throws IllegalStateException;

  Provider<T> getProvider();

  IBinding<T> getBinding();

  default IBindingBuilder<T> build()
  {
    return this;
  }

}
