package org.aaron.wordscomplete.model;

import org.aaron.wordscomplete.transformer.TileRackStringTransformer;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * User: aprobus
 * Date: 5/1/13
 * Time: 6:39 PM
 */
public class TileRackTest {

   @Test
   public void testEmptyConstructor() {
      TileRack tileRack = new TileRack();

      Assert.assertEquals(0, tileRack.getLetterTiles().size());
   }

   @Test
   public void testTileConstructor() {
      ArrayList<LetterTile> tiles = new ArrayList<LetterTile>();
      tiles.add(new LetterTile('a'));

      TileRack tileRack = new TileRack(tiles);
      Assert.assertEquals(1, tileRack.getLetterTiles().size());
   }

   @Test
   public void testRemoveTile() {
      TileRack tileRack = TileRackStringTransformer.fromString("ab");

      tileRack.removeTile(new LetterTile('b'));

      Assert.assertEquals(1, tileRack.getLetterTiles().size());
      Assert.assertEquals('a', tileRack.getLetterTiles().get(0).getLetter());
   }

   @Test
   public void testRemoveTileDuplicates() {
      TileRack tileRack = TileRackStringTransformer.fromString("aba");

      tileRack.removeTile(new LetterTile('a'));

      Assert.assertEquals(2, tileRack.getLetterTiles().size());
      Assert.assertEquals('b', tileRack.getLetterTiles().get(0).getLetter());
      Assert.assertEquals('a', tileRack.getLetterTiles().get(1).getLetter());
   }

   @Test
   public void testRemoveBlankTile() {
      TileRack tileRack = TileRackStringTransformer.fromString("ab_");

      tileRack.removeTile(LetterTile.newBlankTile());

      Assert.assertEquals(2, tileRack.getLetterTiles().size());
      Assert.assertEquals('a', tileRack.getLetterTiles().get(0).getLetter());
      Assert.assertEquals('b', tileRack.getLetterTiles().get(1).getLetter());
   }

}
