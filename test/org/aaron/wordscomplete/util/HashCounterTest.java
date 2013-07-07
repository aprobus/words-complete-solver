package org.aaron.wordscomplete.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * User: aprobus
 * Date: 7/7/13
 * Time: 5:19 PM
 */
public class HashCounterTest {

   private HashCounter<String> stringCounter;

   @Before
   public void setup() {
      stringCounter = new HashCounter<String>();
   }

   @Test
   public void testInitialValue() {
      Assert.assertEquals(0, stringCounter.getCount("unknown"));
   }

   @Test
   public void testIncrement() {
      Assert.assertEquals(0, stringCounter.getCount("value"));
      stringCounter.increment("value");
      Assert.assertEquals(1, stringCounter.getCount("value"));
   }

   @Test
   public void testDeincrement() {
      Assert.assertEquals(0, stringCounter.getCount("value"));
      stringCounter.deincrement("value");
      Assert.assertEquals(-1, stringCounter.getCount("value"));
   }

   @Test
   public void testValuesIndependent() {
      stringCounter.increment("value1");

      stringCounter.increment("value2");
      stringCounter.increment("value2");

      Assert.assertEquals(1, stringCounter.getCount("value1"));
      Assert.assertEquals(2, stringCounter.getCount("value2"));
   }

   @Test
   public void testSetCount() {
      stringCounter.setCount("value", 5);
      Assert.assertEquals(5, stringCounter.getCount("value"));
   }

}
