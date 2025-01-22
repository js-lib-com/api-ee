package com.jslib.api.injector;

/**
 * Strings manipulation utility. This utility class allows for sub-classing. See {@link com.jslib.util} for utility
 * sub-classing description.
 * 
 * @author Iulian Rotaru
 */
class Strings
{
  /**
   * Concatenates variable number of objects converted to string, separated by colon. Return empty string if this method
   * is invoked with no arguments at all. Note that this method uses private helper {@link #toString(Object)} to
   * actually convert every given object to string.
   * 
   * @param objects variable number of objects.
   * @return objects string representation.
   */
  public static String toString(Object... objects)
  {
    if(objects.length == 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    sb.append(toString(objects[0]));
    for(int i = 1; i < objects.length; i++) {
      sb.append(":");
      sb.append(toString(objects[i]));
    }
    return sb.toString();
  }

  /**
   * Convert object to string representation. This method applies next heuristic to convert given object to string
   * representation:
   * <ul>
   * <li>if <code>object</code> is null returns "null",
   * <li>if it is a {@link String} returns it as it is,
   * <li>if is an instance of {@link Class} returns {@link Class#getName()},
   * <li>if is an instance of {@link Throwable} returns causes trace - limited to 8, from cause to cause; if no cause at
   * all returns {@link Throwable#toString()},
   * <li>if none of above returns {@link Object#toString()}.
   * </ul>
   * 
   * @param object object to stringify.
   * @return object string representation.
   */
  private static String toString(Object object)
  {
    if(object == null) {
      return null;
    }

    if(object instanceof String) {
      return (String)object;
    }

    if(object instanceof Class) {
      return ((Class<?>)object).getName();
    }

    if(!(object instanceof Throwable)) {
      return object.toString();
    }

    Throwable t = (Throwable)object;
    if(t.getCause() == null) {
      return t.toString();
    }

    int level = 0;
    StringBuilder sb = new StringBuilder();
    for(;;) {
      sb.append(t.getClass().getName());
      sb.append(":");
      sb.append(" ");
      if(++level == 8) {
        sb.append("...");
        break;
      }
      if(t.getCause() == null) {
        String s = t.getMessage();
        if(s == null) {
          t.getClass().getName();
        }
        sb.append(s);
        break;
      }
      t = t.getCause();
    }
    return sb.toString();
  }

  /**
   * Concatenates a variable number of objects, as strings. For every given argument, convert it to string using
   * {@link Object#toString()} overload and append to concatenated result. If a given argument happens to be null, skip
   * it. Return empty string if this method is invoked with no arguments at all.
   * 
   * @param objects variable number of objects.
   * @return concatenated objects.
   */
  public static String concat(Object... objects)
  {
    StringBuilder sb = new StringBuilder();
    for(Object object : objects) {
      if(object != null) {
        sb.append(object);
      }
    }
    return sb.toString();
  }

}
