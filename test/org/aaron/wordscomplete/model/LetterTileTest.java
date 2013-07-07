package org.aaron.wordscomplete.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: aprobus
 * Date: 5/1/13
 * Time: 6:46 PM
 */
public class LetterTileTest {

   @Test
   public void testConvertsToLower() {
      LetterTile tile = LetterTile.newTile('A');
      Assert.assertEquals('a', tile.getLetter());
   }

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

   @Test
   public void testSortOrderWithBlank() {
      List<LetterTile> tiles = new ArrayList<LetterTile>(Arrays.asList(LetterTile.newTile('a'), LetterTile.newBlankTile()));

      Collections.sort(tiles);

      Assert.assertFalse(tiles.get(0).isBlank());
      Assert.assertTrue(tiles.get(1).isBlank());
   }

   @Test
   public void testSortOrderLetters() {
      List<LetterTile> tiles = new ArrayList<LetterTile>(Arrays.asList(LetterTile.newTile('b'), LetterTile.newTile('a')));

      Collections.sort(tiles);

      Assert.assertEquals('a', tiles.get(0).getLetter());
      Assert.assertEquals('b', tiles.get(1).getLetter());
   }

}
