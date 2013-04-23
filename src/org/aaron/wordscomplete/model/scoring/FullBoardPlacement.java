package org.aaron.wordscomplete.model.scoring;

import org.aaron.wordscomplete.model.LetterTile;
import org.aaron.wordscomplete.model.PlacedTile;

import java.util.List;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 8:06 PM
 */
public class FullBoardPlacement implements BoardPlacement {

   private String word;
   private List<PlacedTile> addedTiles;
   private List<LetterTile> existingTiles;
   private List<PartialBoardPlacement> secondaryPlacements;

   public FullBoardPlacement(List<PlacedTile> addedTiles, List<LetterTile> existingTiles, String word, List<PartialBoardPlacement> secondaryPlacements) {

      this.word = word;
      this.secondaryPlacements = secondaryPlacements;
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

   public String getWord() {
      return word;
   }

   public void setWord(String word) {
      this.word = word;
   }

   public List<PartialBoardPlacement> getSecondaryPlacements() {
      return secondaryPlacements;
   }

   public void setSecondaryPlacements(List<PartialBoardPlacement> secondaryPlacements) {
      this.secondaryPlacements = secondaryPlacements;
   }

}
