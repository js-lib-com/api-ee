package js.transaction;

/**
 * Transaction manager is a factory for transaction instances. Transaction manager is more a service provider interface
 * used by containers to provide declarative transactions. Usually user space code has no need to interact with
 * transaction manager directly. Anyway, implementation is encouraged to expose transaction manager as a Java service.
 * Also implementation should be thread safe and reusable.
 * <p>
 * Transaction manager is configurable via {@link #config(js.lang.Config)} method. Configuration object is
 * implementation specific. When create transaction manager need to configure it. Since transaction manager instance
 * creation is costly it is recommended to cache an reuse it; recommended way is to create application singleton.
 * 
 * <pre>
 * // implementation specific configuration
 * Config config = new Config();
 * config.setProperty(...);
 * ...
 * // get manager instance and configure it
 * // manager instance should be cached and reused since is costly to create
 * TransactionManager manager = ServiceLoader.load(TransactionManager.class).iterator().next();
 * manager.config(config);
 * </pre>
 * 
 * When application exits transaction manager instance should be destroyed, see {@link #destroy()} in order to release
 * external transactional resources.
 * 
 * <h3>Manual Transaction</h3>
 * <p>
 * As stated, user code should use declarative transactions provided by containers. Anyway, transaction manager provides
 * means for programmatic transactions, see {@link #exec(WorkingUnit, Object...)}.
 * <p>
 * Here is a sample transactional working unit executed programmatically.
 * 
 * <pre>
 * Address address = manager.exec(new WorkingUnit&lt;Session, Address&gt;()
 * {
 *   public Address exec(Session session, Object... args) throws Exception
 *   {
 *     Person person = (Person)args[0];
 *     SQLQuery sql = session.createSQLQuery("SELECT * FROM address ...");
 *     ...
 *     return (Address)sql.uniqueResult();
 *   }
 * }, person);
 * </pre>
 * 
 * Working unit is parameterized with resource manager type. In example resource manager is a Hibernate session object
 * but resource manager type depends on persistence engine implementation. For example on Hibernate is named
 * <code>session</code>, on JDBC is <code>connection</code> - actually a resource manager connection, while on JPA is
 * named <code>entity manager</code>.
 * 
 * <p>
 * Finally, for really special cases one can handle transactions manually but must follow next usage pattern. Note close
 * from final block.
 * 
 * <pre>
 * Transaction t = manager.createTransaction();
 * try {
 *   // execute transactional working unit
 *   t.commit();
 * }
 * catch(Exception e) {
 *   t.rollback();
 * }
 * finally {
 *   t.close();
 * }
 * </pre>
 * 
 * @author Iulian Rotaru
 */
public interface TransactionManager
{
  /**
   * Create a new transaction instance. Creating a transaction instance begins the transactional scope and is mandatory
   * to be concluded by close, as in sample code. Creating transactions and failing to close them will exhaust
   * transactional resource.
   * <p>
   * Also, although not mandatory, need to invoke commit or rollback at appropriate time.
   * 
   * <pre>
   * Transaction t = manager.createTransaction(); // begin transactional scope
   * try {
   *   // execute component working unit
   *   t.commit();
   * }
   * catch(Exception e) {
   *   t.rollback();
   * }
   * finally {
   *   t.close(); // mandatory to close transactional scope
   * }
   * </pre>
   * <p>
   * When create a transaction is possible to request a specific transactional schema. This limits the scope of
   * transactional resource objects that can be accessed from transaction. If not provided created transaction used
   * implicit / global schema. Depending on implementation, transactional schema can have alternative names, e.g. JPA
   * name it persistence unit.
   * 
   * @param schema optional transactional schema, null if not used.
   * @return newly created transaction instance.
   * @throws TransactionException if transaction creation fails.
   */
  Transaction createTransaction(String schema);

  /**
   * Create a read-only transaction instance. This is a variant of {@link #createTransaction(String)} optimized for
   * read-only transactions.
   * 
   * @param schema optional transactional schema, null if not used.
   * @return newly create transaction instance.
   * @throws TransactionException if transaction creation fails.
   */
  Transaction createReadOnlyTransaction(String schema);

  /**
   * Helper method used to execute a transactional block of code programmatically. In sample code below, working unit is
   * executed inside transaction boundaries; user code should be aware of that and do not execute excessive long
   * processing.
   * 
   * <pre>
   * Address address = TransactionManager.exec("core", new WorkingUnit<Session, Address>()
   * {
   *   public Address exec(Session session, Object... args) throws Exception
   *   {
   *     Person person = (Person)args[0];
   *     Address address = session.get(Address.class, person.getAddressId());
   *     return address;
   *   }
   * }, person);
   * </pre>
   * 
   * Working unit is parameterized with resource manager type. In example resource manager is a Hibernate session object
   * but resource manager type depends on persistence engine implementation. For example on Hibernate is named
   * <code>session</code>, on JDBC is <code>connection</code> while on JPA is named <code>entity manager</code>. See
   * {@link WorkingUnit} interface.
   * <p>
   * When create a transaction is possible to request a specific transactional schema. This limits the scope of
   * transactional resource objects that can be accessed from transaction. If not provided created transaction used
   * implicit / global schema. Depending on implementation, transactional schema can have alternative names, e.g. JPA
   * name it persistence unit.
   * 
   * @param schema optional transactional schema, null if not used.
   * @param workingUnit workingUnit to be executed transactional,
   * @param args variable arguments list to be passed to {@link WorkingUnit#exec(Object, Object...)}.
   * @param <R> resource manager type for working unit.
   * @param <T> the type of the working unit returned value.
   * @return the value returned by executed working unit.
   * @throws TransactionException if working unit execution fails in some way. Note that the root cause is set to the
   *           actual working unit exception.
   */
  <R, T> T exec(String schema, WorkingUnit<R, T> workingUnit, Object... args) throws TransactionException;

  /**
   * Convenient alternative of {@link #exec(String, WorkingUnit, Object...)} when use default / global transactional
   * schema.
   * 
   * @param workingUnit workingUnit to be executed transactional,
   * @param args variable arguments list to be passed to {@link WorkingUnit#exec(Object, Object...)}.
   * @param <R> resource manager type for working unit.
   * @param <T> the type of the working unit returned value.
   * @return the object returned by executed working unit.
   * @throws TransactionException if working unit execution fails in some way. Note that the root cause is set to the
   *           actual working unit exception.
   */
  <R, T> T exec(WorkingUnit<R, T> workingUnit, Object... args) throws TransactionException;

  /** Release transactional resources. */
  void destroy();
}
