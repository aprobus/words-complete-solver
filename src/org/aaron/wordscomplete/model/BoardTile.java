package org.aaron.wordscomplete.model;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 7:19 PM
 */
public class BoardTile {

   private LetterTile tile;
   private Board.ScoreBonus bonus;

   public BoardTile (LetterTile tile, Board.ScoreBonus tileBonus) {
      this.tile = tile;
      bonus = tileBonus;
   }

   public BoardTile (Board.ScoreBonus tileBonus) {
      this(null, tileBonus);
   }

   public BoardTile () {
      this(null, Board.ScoreBonus.None);
   }

   public void setTile (LetterTile tile) {
      this.tile = tile;
   }

   public LetterTile getTile () {
      return tile;
   }

   public void setScoreBonus (Board.ScoreBonus bonus) {
      this.bonus = bonus;
   }

   public Board.ScoreBonus getScoreBonus () {
      return bonus;
   }

}
