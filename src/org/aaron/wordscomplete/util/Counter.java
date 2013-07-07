package org.aaron.wordscomplete.util;

/**
 * User: aprobus
 * Date: 7/7/13
 * Time: 5:07 PM
 */
public interface Counter<T> {

   void increment(T obj);

   void deincrement(T obj);

   int getCount(T obj);

   void setCount(T obj, int count);
}
