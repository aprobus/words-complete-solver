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

      for (LetterTile tile : tiles) {
         this.letterTiles.add(tile);
      }
   }

   public List<LetterTile> getLetterTiles () {
      return letterTiles;
   }

   public void removeTile(LetterTile letterTile) {

   }

}
