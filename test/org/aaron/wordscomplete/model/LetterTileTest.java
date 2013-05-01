package org.aaron.wordscomplete.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: aprobus
 * Date: 5/1/13
 * Time: 6:46 PM
 */
public class LetterTileTest {

   @Test
   public void testEqualsLetter() {
      Assert.assertEquals(new LetterTile('c'), new LetterTile('c'));
   }

   @Test
   public void testEqualsBlank() {
      Assert.assertEquals(LetterTile.newBlankTile(), LetterTile.newBlankTile());
   }

   @Test
   public void testNotEqualsLetterBlank() {
      Assert.assertNotEquals(new LetterTile('c'), LetterTile.newBlankTile());
   }

   @Test
   public void testNotEqualsLetters() {
      Assert.assertNotEquals(new LetterTile('c'), new LetterTile('e'));
   }

}
