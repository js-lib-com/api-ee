package js.transaction;

/**
 * Transaction context provides access to currently executing resource manager on the transactional resource.
 * Transaction context is designed to work only with transactional container and the only means to obtain it is via
 * dependency injection.
 * <p>
 * A transactional resource offers two kinds of APIs: transaction API exposes by {@link Transaction} implementation and
 * worker API exposed by session instance, executed inside boundaries specified by transaction API. Worker API is used
 * to actually fulfill the working unit.
 * <p>
 * In sample code below, transaction context is injected into a DAO implementation and facilitate access to currently
 * executing resource manager - in our concrete case Hibernate Session. Session on its turn is used to perform the
 * actual transactional working unit.
 * 
 * <pre>
 * class DaoImpl implements Dao
 * {
 * 	{@literal @}DependencyInjection
 * 	private TransactionContext context;
 * 	...
 * 	User getUser(int userId) { 
 * 		Session session = context.getResourceManager();
 * 		Query query = session.createQuery(...);
 * 		...
 * 	}
 * }
 * </pre>
 * <p>
 * In this context, the term <code>resource manager</code> is just an API exposed by some implementation. On Hibernate 4
 * is named <code>session</code>, on JDBC <code>connection</code> whereas on JPA is named <code>entity manager</code>.
 * 
 * @author Iulian Rotaru
 */
public interface TransactionContext
{
  /**
   * Get currently executing resource manager on transactional resource. Resource manager exposes transactional resource
   * API used to fulfill transactional working unit.
   * <p>
   * Resource manager type depends on implementation, e.g. on Hibernate is named indeed <code>session</code> while on
   * JDBC is <code>connection</code>.
   * 
   * @return currently executing resource manager.
   * @param <R> auto-cast to resource manager type.
   */
  <R> R getResourceManager();
}
