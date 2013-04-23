package org.aaron.wordscomplete.transformer;

import org.aaron.wordscomplete.model.LetterTile;
import org.aaron.wordscomplete.model.TileRack;

import java.util.LinkedList;
import java.util.List;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 10:27 PM
 */
public class TileRackStringTransformer {

   private static final char BLANK_TILE_CHAR = '_';
   private static final int MAX_TILES = 7;

   public static String toString(TileRack tileRack) {
      StringBuilder builder = new StringBuilder();

      for (LetterTile tile : tileRack.getLetterTiles()) {
         if (tile.isBlank()) {
            builder.append(BLANK_TILE_CHAR);
         } else {
            builder.append(tile.getLetter());
         }
      }

      return builder.toString();
   }

   public static TileRack fromString(String tileRackString) {
      if (tileRackString == null || tileRackString.length() == 0 || tileRackString.length() > MAX_TILES) {
         return null;
      }

      List<LetterTile> tiles = new LinkedList<LetterTile>();
      for (int i = 0; i < tileRackString.length(); i++) {
         char tileChar = tileRackString.charAt(i);

         if (tileChar == BLANK_TILE_CHAR) {
            tiles.add(new LetterTile());
         } else if (Character.isLetter(tileChar)) {
            tiles.add(new LetterTile(Character.toLowerCase(tileChar)));
         } else {
            return null;
         }
      }

      TileRack tileRack = new TileRack(tiles);

      return tileRack;
   }
}
