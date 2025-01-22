package com.jslib.api.injector;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Classes
{
  /**
   * Load service of requested interface throwing exception if provider not found. It is a convenient variant of
   * {@link #loadService(Class)} usable when a missing service implementation is a run-time stopper.
   * 
   * @param serviceInterface service interface.
   * @param <S> service type
   * @return service instance.
   * @throws IllegalStateException if service provider not found on run-time.
   */
  public static <S> S loadService(Class<S> serviceInterface)
  {
    S service = loadOptionalService(serviceInterface);
    if(service == null) {
      throw new IllegalStateException("Required service not found: " + serviceInterface);
    }
    return service;
  }

  /**
   * Load service of requested interface returning null if service provider not found. Caller should test returned value
   * and take appropriate actions.
   * 
   * @param serviceInterface service interface.
   * @param <S> service type.
   * @return service instance or null.
   */
  private static <S> S loadOptionalService(Class<S> serviceInterface)
  {
    S service = null;
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if(classLoader != null) {
      service = loadService(serviceInterface, classLoader);
    }
    if(service == null) {
      service = loadService(serviceInterface, Classes.class.getClassLoader());
    }
    return service;
  }

  /**
   * Load service of requested interface using given class loader. Returns null if service not found.
   * 
   * @param serviceInterface service interface,
   * @param classLoader class loader.
   * @param <S> service type.
   * @return service instance or null.
   */
  private static <S> S loadService(Class<S> serviceInterface, ClassLoader classLoader)
  {
    Iterator<S> services = ServiceLoader.load(serviceInterface, classLoader).iterator();
    if(services.hasNext()) {
      return services.next();
    }
    return null;
  }
}
