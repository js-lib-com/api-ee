package js.transaction;

/**
 * Factory for transactional instances. Implementation should return a Java Proxy that executes instance methods in a
 * transational context. Also implementation is required to properly handle {@link Mutable} and {@link Immutable}
 * annotations and to create method transaction accordingly. Factory creates a new transactional instance on every
 * {@link #newInstance(Class, Object...)} call. If instance caching is desired it needs to be implemented externally.
 * <p>
 * To create a new transactional instance one need an implementation for this factory. Transactional instance should
 * have interface and implemntation class, <code>Dao</code> and <code>DaoImpl</code> in sample code.
 * 
 * <pre>
 *  TransactionFactory factory = new TransactionfactoryImpl();
 *  ...
 *  Dao dao = factory.newInstance(DaoImpl.class);
 *  ...
 *  dao.savePerson(person);
 * </pre>
 * 
 * <h3>Implementation Arguments</h3>
 * <p>
 * Transactional implementation may need dependencies that are injected by constructor. Part of dependencies may be
 * resolved by implementation and part can be provided as optional arguments to this factory method. Is recommended that
 * implementation specific arguments to be injected before optional arguments provided to factory method. Anyway,
 * transactional class should learn from this factory implementation about supported arguments and about specific order
 * and to respect constructor signature.
 * <p>
 * Here is an example of implementation that has only arguments provided by implementation.
 * 
 * <pre>
 *  class DaoImpl implements Dao {
 *      DaoImpl(TransactionContext context) {
 *      }
 *  }
 *  ...
 *  Dao dao = factory.newInstance(DaoImpl.class);
 * </pre>
 * <p>
 * And here a transactional class with first argument provided by this factory implementation - the transactional
 * context, and the second provided at run-time, respective the default schema to be used by this particular instance.
 * 
 * <pre>
 *  class DaoImpl implements Dao {
 *      DaoImpl(TransactionContext context, String schema) {
 *      }
 *  }
 *  ...
 *  Dao dao = factory.newInstance(DaoImpl.class, "core");
 * </pre>
 * 
 * @author Iulian Rotaru
 * @version final
 */
public interface TransactionFactory
{
  /**
   * Create new transactional instance of specified implementation class. Given implementation should have an interface
   * assignable to the left hand value. See class description for a discussion about implementation class arguments.
   * 
   * 
   * @param implementationClass implementation class.
   * @param args optional arguments.
   * @return newly created transactional instance.
   */
  <I> I newInstance(Class<? extends I> implementationClass, Object... args);
}
