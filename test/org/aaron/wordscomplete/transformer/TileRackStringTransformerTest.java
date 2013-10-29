package org.aaron.wordscomplete.transformer;

import org.junit.Assert;
import org.aaron.wordscomplete.model.LetterTile;
import org.aaron.wordscomplete.model.TileRack;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: aprobus
 * Date: 4/27/13
 * Time: 9:23 PM
 */
public class TileRackStringTransformerTest {

   @Test
   public void testToStringTransformer() {
      List<LetterTile> tiles = new ArrayList<LetterTile>();
      tiles.add(new LetterTile('d', true));
      tiles.add(new LetterTile());
      tiles.add(new LetterTile('e', false));

      TileRack tileRack = new TileRack(tiles);

      String tileRackString = TileRackStringTransformer.toString(tileRack);
      Assert.assertEquals("__e", tileRackString);
   }

   @Test
   public void testFromStringTransformer() {
      TileRack tileRack = TileRackStringTransformer.fromString("d_e");
      List<LetterTile> tiles = tileRack.getLetterTiles();

      Assert.assertEquals(3, tiles.size());
      Assert.assertEquals(new LetterTile('d'), tiles.get(0));
      Assert.assertEquals(new LetterTile(), tiles.get(1));
      Assert.assertEquals(new LetterTile('e', false), tiles.get(2));
   }

   @Test
   public void testToStringTransformer_EmptyTileRack() {
      String result = TileRackStringTransformer.toString(new TileRack());

      Assert.assertEquals("", result);
   }

   @Test
   public void testFromStringTransformer_EmptyString() {
      TileRack tileRack = TileRackStringTransformer.fromString("");

      Assert.assertNotNull("Tile rack should not be null", tileRack);
      Assert.assertEquals("Unexpected number of tiles", 0, tileRack.getLetterTiles().size());
   }

}
