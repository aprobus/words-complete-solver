package org.aaron.wordscomplete.model;

import org.aaron.wordscomplete.util.Counter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * User: aprobus
 * Date: 7/7/13
 * Time: 5:32 PM
 */
public class ScrabbleBoardTest {

   private ScrabbleBoard board;

   @Before
   public void setup() {
      board = new ScrabbleBoard();
   }

   @Test
   public void testCountsEmptyBoard() {
      Counter<LetterTile> tileCounts = board.getRemainingTileCounts(null);

      Assert.assertEquals(9, tileCounts.getCount(LetterTile.newTile('a')));
   }

   @Test
   public void testCountsBlankTileOnBoard() {
      board.setTile(2, 2, LetterTile.newBlankTileWithLetter('a'));

      Counter<LetterTile> tileCounts = board.getRemainingTileCounts(null);

      Assert.assertEquals(9, tileCounts.getCount(LetterTile.newTile('a')));
      Assert.assertEquals(1, tileCounts.getCount(LetterTile.newBlankTile()));
   }

   @Test
   public void testCountsBlankInTileRack() {
      List<LetterTile> tiles = Arrays.asList(LetterTile.newBlankTile());

      TileRack tileRack = new TileRack(tiles);

      Counter<LetterTile> tileCounts = board.getRemainingTileCounts(tileRack);

      Assert.assertEquals(9, tileCounts.getCount(LetterTile.newTile('a')));
      Assert.assertEquals(1, tileCounts.getCount(LetterTile.newBlankTile()));
   }

}
