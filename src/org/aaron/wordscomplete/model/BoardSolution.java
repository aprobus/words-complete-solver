package org.aaron.wordscomplete.model;

import java.util.List;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 7:30 PM
 */
public class BoardSolution {

   private String word;
   private int score;
   private List<PlacedTile> placedTiles;

   public BoardSolution(String word, int score, List<PlacedTile> placedTiles) {
      this.word = word;
      this.score = score;
      this.placedTiles = placedTiles;
   }

   public String getWord() {
      return word;
   }

   public void setWord(String word) {
      this.word = word;
   }

   public int getScore() {
      return score;
   }

   public void setScore(int score) {
      this.score = score;
   }

   public List<PlacedTile> getPlacedTiles() {
      return placedTiles;
   }

   public void setPlacedTiles(List<PlacedTile> placedTiles) {
      this.placedTiles = placedTiles;
   }
}
