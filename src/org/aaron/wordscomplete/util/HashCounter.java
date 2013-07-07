package org.aaron.wordscomplete.util;

import java.util.HashMap;
import java.util.Map;

/**
 * User: aprobus
 * Date: 7/7/13
 * Time: 5:09 PM
 */
public class HashCounter<T> implements Counter<T> {

   private Map<T, Integer> mCounter = new HashMap<T, Integer>();

   @Override
   public void increment(T obj) {
      incrementBy(obj, 1);
   }

   @Override
   public void deincrement(T obj) {
      incrementBy(obj, -1);
   }

   private void incrementBy(T obj, int incBy) {
      if (!mCounter.containsKey(obj)) {
         mCounter.put(obj, Integer.valueOf(0));
      }

      int count = mCounter.get(obj).intValue();
      mCounter.put(obj, Integer.valueOf(count + incBy));
   }

   @Override
   public int getCount(T obj) {
      if (mCounter.containsKey(obj)) {
         return mCounter.get(obj).intValue();
      } else {
         return 0;
      }
   }

   @Override
   public void setCount(T obj, int count) {
      mCounter.put(obj, Integer.valueOf(count));
   }
}
