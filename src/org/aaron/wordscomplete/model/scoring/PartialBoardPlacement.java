package org.aaron.wordscomplete.model.scoring;

import org.aaron.wordscomplete.model.LetterTile;
import org.aaron.wordscomplete.model.PlacedTile;

import java.util.List;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 8:01 PM
 */
public class PartialBoardPlacement implements BoardPlacement {

   private List<PlacedTile> addedTiles;
   private List<LetterTile> existingTiles;

   public PartialBoardPlacement(List<PlacedTile> addedTiles, List<LetterTile> existingTiles) {
      this.addedTiles = addedTiles;
      this.existingTiles = existingTiles;
   }

   public List<PlacedTile> getAddedTiles() {
      return addedTiles;
   }

   public void setAddedTiles(List<PlacedTile> addedTiles) {
      this.addedTiles = addedTiles;
   }

   public List<LetterTile> getExistingTiles() {
      return existingTiles;
   }

   public void setExistingTiles(List<LetterTile> existingTiles) {
      this.existingTiles = existingTiles;
   }
}
