package org.aaron.wordscomplete.model;

import java.util.ArrayList;
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
      letterTiles = new ArrayList<LetterTile>(7);
   }

   public TileRack (List<LetterTile> tiles) {
      letterTiles = new ArrayList<LetterTile>(7);
      letterTiles.addAll(tiles);
   }

   public List<LetterTile> getLetterTiles () {
      return letterTiles;
   }

   public void removeTile(LetterTile letterTile) {
      List<LetterTile> newLetterTiles = new ArrayList<LetterTile>(7);
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
