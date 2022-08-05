package com.jslib.api.transaction;

/**
 * A transaction instance supplies methods to mark transaction boundaries. Transactional working unit is executed
 * between this transaction instance creation and commit. Transaction instance is obtained from transaction manager, see
 * {@link TransactionManager#createTransaction(String)}. If working unit or commit fails transaction is rolled back.
 * After working unit completes, successful or erroneous, transaction instance should be closed.
 * <p>
 * Here is standard use case.
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
 * <p>
 * Read only transaction does not explicit use commit or rollback. Just create transaction and close after finishing
 * working unit.
 * <p>
 * This interface is intended to be used by transactional containers and is part of service provider interface.
 * Implementation is required to support nested transactions so that a transactional method can be invoked from another
 * transactional method. Both {@link #commit()} and {@link #rollback()} should be actually executed on transactional
 * resource only when outermost transactional method completes. Also {@link #close()} should release resources also only
 * for outermost transaction.
 * 
 * @author Iulian Rotaru
 */
public interface Transaction
{
  /**
   * Commit current transactional working unit. Changes performed by working unit on the transactional resource do
   * actually take effect.
   * 
   * @throws IllegalStateException if attempt to commit a read-only transaction.
   * @throws TransactionException if commit fails.
   */
  void commit();

  /**
   * Rollback current transaction if working unit fails.
   * 
   * @throws IllegalStateException if attempt to rollback a read-only transaction.
   * @throws TransactionException if rollback fails.
   */
  void rollback();

  /**
   * Close transaction after commit or even roolback to ensure that resources used by transaction are released.
   * Implementation should track nested transactions and actually release resources only for the outermost transaction.
   * 
   * @return true if transaction was closed and resources released; return false if this transaction is a nested one.
   * @throws TransactionException if close fails.
   */
  boolean close();

  /**
   * Get related resource manager used to actually operates on the transactional resource. In this context,
   * <code>resource manager</code> term is used in a broad sense; it denotes meanings that facilitates interaction with
   * transactional resource via service API. Anyway, <code>resource manager</code> term is coined by JEE specification.
   * <p>
   * Resource manager type depends on persistence engine implementation, e.g. on Hibernate is named
   * <code>session</code>, on JDBC is <code>connection</code> while on JPA is named <code>entity manager</code>.
   * 
   * @return related resource manager.
   * @param <R> auto-cast to resource manager type.
   * 
   * @see org.hibernate.Session
   * @see javax.persistence.EntityManager
   * @see java.sql.Connection
   */
  <R> R getResourceManager();
}
