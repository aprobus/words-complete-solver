package org.aaron.wordscomplete.model;

import java.util.LinkedList;
import java.util.List;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 6:54 PM
 */
public class TileRack {

   private List<LetterTile> letterTiles;

   public TileRack () {
      letterTiles = new LinkedList<LetterTile>();
   }

   public TileRack (List<LetterTile> tiles) {
      letterTiles = new LinkedList<LetterTile>();
      letterTiles.addAll(tiles);
   }

   public List<LetterTile> getLetterTiles () {
      return letterTiles;
   }

   public void removeTile(LetterTile letterTile) {
      List<LetterTile> newLetterTiles = new LinkedList<LetterTile>();
      boolean hasRemovedTile = false;

      for (LetterTile tile : letterTiles) {
         if (!hasRemovedTile && tile.equals(letterTile)) {
            hasRemovedTile = true;
            continue;
         }

         newLetterTiles.add(tile);
      }

      letterTiles = newLetterTiles;
   }

}
