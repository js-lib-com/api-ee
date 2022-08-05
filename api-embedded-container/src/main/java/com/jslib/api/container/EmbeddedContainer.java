package com.jslib.api.container;

import java.util.ServiceLoader;

/**
 * Facade API for application embedded containers. An embedded container should be able to create instances with
 * dependency injection based on <code>javax.inject</code> API and additional container services declared on business
 * classes using annotations. After using embedded container it should be closed in order to release system resources.
 * 
 * This interface also provides a bootstrap method that discover {@link EmbeddedContainerProvider} deployed on runtime and
 * load it via Java services loader. If more applications containers found choose silently one. It is provider
 * implementation responsibility to ensure that returned application container is initialized.
 * 
 * @author Iulian Rotaru
 */
public interface EmbeddedContainer extends AutoCloseable
{

  /**
   * Discover application container provider and execute {@link EmbeddedContainerProvider#createAppContainer(Object...)}.
   * Arguments are passed as they are to provider factory method.
   * 
   * @param arguments variable number of arguments.
   * @return application container instance, fully initialized.
   */
  static EmbeddedContainer create(Object... arguments)
  {
    for(EmbeddedContainerProvider provider : ServiceLoader.load(EmbeddedContainerProvider.class)) {
      return provider.createAppContainer(arguments);
    }
    throw new EmbeddedContainerNotFoundException();
  }

  /**
   * 
   * @param <T>
   * @param interfaceClass
   * @return
   */
  <T> T getInstance(Class<T> interfaceClass);

}
