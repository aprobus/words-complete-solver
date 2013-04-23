package org.aaron.wordscomplete.model;

import org.aaron.wordscomplete.model.scoring.FullBoardPlacement;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 7:18 PM
 */
public abstract class Board {

   protected BoardTile[][] board;
   protected boolean isEmpty = true;

   public static final int NUM_ROWS = 15;
   public static final int NUM_COLUMNS = 15;

   private TileRack tileRack;

   public Board () {
      board = new BoardTile[NUM_COLUMNS][NUM_ROWS];

      for (int row = 0; row < NUM_ROWS; row++) {
         for (int column = 0; column < NUM_COLUMNS; column++) {
            board[column][row] = new BoardTile();
         }
      }
   }

   public abstract int getScore(FullBoardPlacement placementToScore);

   public abstract BoardType getBoardType();

   public TileRack getTileRack() {
      return tileRack;
   }

   public void setTileRack(TileRack tileRack) {
      this.tileRack = tileRack;
   }

   public boolean hasTile (int row, int column) {
      if (row < 0 || column < 0 || row >= NUM_ROWS || column >= NUM_COLUMNS) {
         return false;
      } else {
         return board[column][row].getTile() != null;
      }
   }

   public LetterTile getTile (int row, int column) {
      if (row < 0 || column < 0 || row >= NUM_ROWS || column >= NUM_COLUMNS) {
         return null;
      } else {
         return board[column][row].getTile();
      }
   }

   public boolean hasTile(Coordinate coordinate) {
      return hasTile(coordinate.row, coordinate.column);
   }

   public void clearTile (int row, int column) {
      if (row >= 0 && column >= 0 && row < NUM_ROWS && column < NUM_COLUMNS) {
         board[column][row].setTile(null);
      }
   }

   public BoardTile getBoardTile (int row, int column) {
      if (row < 0 || column < 0 || row >= NUM_ROWS || column >= NUM_COLUMNS) {
         return null;
      } else {
         return board[column][row];
      }
   }

   public BoardTile getBoardTile(Coordinate coordinate) {
      return getBoardTile(coordinate.row, coordinate.column);
   }

   public LetterTile getTile(Coordinate coordinate) {
      return getTile(coordinate.row, coordinate.column);
   }

   public void setTile (int row, int column, LetterTile tile) {
      this.isEmpty = false;
      board[column][row].setTile(tile);
   }

   public boolean isEmpty() {
      return isEmpty;
   }

   @Override
   public String toString () {
      StringBuilder boardString = new StringBuilder();

      for (int row = 0; row < NUM_ROWS; row++) {
         for (int column = 0; column < NUM_COLUMNS; column++) {
            if (this.hasTile(row, column)) {
               boardString.append(this.getTile(row, column).getLetter());
            } else {
               boardString.append(' ');
            }
         }

         boardString.append('\n');
      }

      return boardString.toString();
   }

   public enum ScoreBonus {
      DoubleLetterScore,
      TripleLetterScore,
      DoubleWordScore,
      TripleWordScore,
      None
   }

}
