package js.injector;

import jakarta.inject.Provider;

/**
 * A mapping between an instance key and an instance provider.
 * 
 * @author Iulian Rotaru
 */
public interface IBinding<T>
{

  Key<T> key();

  Provider<T> provider();

}
