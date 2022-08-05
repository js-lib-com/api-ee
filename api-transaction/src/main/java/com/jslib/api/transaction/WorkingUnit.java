package com.jslib.api.transaction;

/**
 * Transactional working unit, programmatic alternative to the declarative transaction. This interface is designed to
 * work with {@link TransactionManager#exec(WorkingUnit, Object...)} method.
 * <p>
 * In sample code below, working unit is executed inside transaction boundaries; user code should be aware of that and
 * do not execute excessive long processing.
 * 
 * <pre>
 * Address address = transactionManager.exec(new WorkingUnit&lt;Session, Address&gt;() {
 *   public Address exec(Session session, Object... args) throws Exception {
 *     Person person = (Person)args[0];
 *     SQLQuery sql = session.createSQLQuery("SELECT * FROM address ...");
 *     ...
 *     return (Address)sql.uniqueResult();
 *   }
 * }, person);
 * </pre>
 * 
 * Working unit is parameterized with resource manager type. In example resource manager is a Hibernate session instance
 * but resource manager type depends on persistence engine implementation. In Hibernate is named <code>session</code>,
 * in JDBC is <code>connection</code> - actually a resource manager connection, while in JPA is named
 * <code>entity manager</code>. Anyway, the term <code>resource manager</code> is coined by JEE specification.
 * 
 * @author Iulian Rotaru
 * @param <R> resource manager
 * @param <T> returned value type.
 * 
 * @see org.hibernate.Session
 * @see javax.persistence.EntityManager
 * @see java.sql.Connection
 */
public interface WorkingUnit<R, T>
{
  /**
   * Execute block of code inside transactional boundaries and return the value.
   * 
   * @param resourceManager resource manager,
   * @param args optional, variable numbers of arguments
   * @return working unit result, possible null.
   * @throws Exception any execution exception is bubbled up.
   */
  T exec(R resourceManager, Object... args) throws Exception;
}
