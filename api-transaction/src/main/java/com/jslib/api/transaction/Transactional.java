package com.jslib.api.transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Execute managed method into transactional scope. This annotation can be applied to individual methods or on entire
 * class, in which case all public methods become transactional. Transactional annotation on protected or private method
 * is silently ignored.
 * 
 * <pre>
 *  &#064;Transactional
 *  class DaoImpl implements Dao {
 *      ...
 *      public User getUserByLogin(Login login) {
 *      }
 *  }
 * </pre>
 * 
 * In above sample code all public DAO methods are transactional. Please note that by default transactional managed
 * class is mutable. One can use {@link Immutable} to reverse this behavior and {@link Mutable} to select only desired
 * methods.
 * <p>
 * A transactional resource may support multiple schemas. A schema simply groups together a number of resource objects.
 * This allows to limit the scope of resource objects accessible from a transaction boundaries. Depending on
 * implementation, transactional schema can have alternative names, e.g. JPA name it persistence unit.
 * 
 * @author Iulian Rotaru
 * @version final
 */
@Target(
{
    ElementType.TYPE, ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {
  /**
   * Transactional resource may support multiple schemas. This allows to limit the scope of resource objects accessible
   * from transaction boundaries. Transactional schema is optional.
   */
  String schema() default "";
}
