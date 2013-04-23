package org.aaron.wordscomplete.solver.filters.positionfilters;

import org.aaron.wordscomplete.dictionary.Dictionary;
import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;

import java.util.List;

/**
 * User: aprobus
 * Date: 1/29/13
 * Time: 4:48 PM
 */
public class SecondaryWordPositionFilter extends PositionFilter {

   private static final byte VALID_HORI_FLAG = 0x1;
   private static final byte VALID_VERT_FLAG = 0x2;

   private byte[] mValidPositionFlags;

   public SecondaryWordPositionFilter(Board board, Dictionary dictionary) {
      mValidPositionFlags = new byte[Board.NUM_COLUMNS * Board.NUM_ROWS];

      for (int i = 0; i < mValidPositionFlags.length; i++) {
         mValidPositionFlags[i] = 0;
      }

      initializeFlags(board, dictionary);
   }

   @Override
   public boolean isValidPosition(Board board, List<Coordinate> coordinates) {
      boolean isHori = coordinates.get(0).column < coordinates.get(1).column;

      for (int i = 0; i < coordinates.size(); i++) {
         Coordinate coordinate = coordinates.get(i);

         if (isHori && !isValidHori(coordinate)) {
            return false;
         } else if (!isHori && !isValidVert(coordinate)) {
            return false;
         }
      }

      return true;
   }

   private boolean isValidHori(Coordinate coordinate) {
      return (mValidPositionFlags[coordinate.row * Board.NUM_ROWS + coordinate.column] & VALID_HORI_FLAG) > 0;
   }

   private boolean isValidVert(Coordinate coordinate) {
      return (mValidPositionFlags[coordinate.row * Board.NUM_ROWS + coordinate.column] & VALID_VERT_FLAG) > 0;
   }

   private void setFlagForLoc(int row, int column, byte flag) {
      mValidPositionFlags[row * Board.NUM_ROWS + column] |= flag;
   }

   private void initializeFlags(Board board, Dictionary dictionary) {
      for (int row = 0; row < Board.NUM_ROWS; row++) {
         for (int column = 0; column < Board.NUM_COLUMNS; column++) {
            if (board.hasTile(row, column)) {
               initializeTileFlags(row, column);
            } else {
               initializeEmptyTileFlags(board, row, column, dictionary);
            }
         }
      }
   }

   private void initializeTileFlags(int row, int column) {
      byte maskValidBothWays = VALID_HORI_FLAG | VALID_VERT_FLAG;
      setFlagForLoc(row, column, maskValidBothWays);
   }

   private void initializeEmptyTileFlags(Board board, int row, int column, Dictionary dictionary) {
      //Check hori -> Lay tiles horizontally and check vertical words formed
      String prefix = null;
      String suffix = null;

      int topTileRow = row;
      while (topTileRow > 0 && board.hasTile(topTileRow - 1, column)) {
         topTileRow--;
      }

      if (topTileRow != row) {
         prefix = getWordInDirection(board, topTileRow, column, 1, 0);
      }
      suffix = getWordInDirection(board, row + 1, column, 1, 0);

      if (prefix == null && suffix == null) {
         setFlagForLoc(row, column, VALID_HORI_FLAG);
      } else if (dictionary.hasWordForWildCard(prefix, board.getTileRack(), suffix)) {
         setFlagForLoc(row, column, VALID_HORI_FLAG);
      }

      //Check vert
      prefix = null;
      suffix = null;

      int leftTileColumn = column;
      while (leftTileColumn > 0 && board.hasTile(row, leftTileColumn - 1)) {
         leftTileColumn--;
      }

      if (leftTileColumn != column) {
         prefix = getWordInDirection(board, row, leftTileColumn, 0, 1);
      }
      suffix = getWordInDirection(board, row, column, 0, 1);

      if (prefix == null && suffix == null) {
         setFlagForLoc(row, column, VALID_VERT_FLAG);
      } else if (dictionary.hasWordForWildCard(prefix, board.getTileRack(), suffix)) {
         setFlagForLoc(row, column, VALID_VERT_FLAG);
      }
   }

   private static String getWordInDirection(Board board, int row, int column, int rowIncrementer, int columnIncrementer) {
      if (row >= Board.NUM_ROWS || column >= Board.NUM_COLUMNS || !board.hasTile(row, column)) {
         return null;
      }

      StringBuilder wordBuilder = new StringBuilder();

      int currentRow = row;
      int currentColumn = column;
      while (board.hasTile(currentRow, currentColumn)) {
         wordBuilder.append(board.getTile(currentRow, currentColumn).getLetter());

         currentRow += rowIncrementer;
         currentColumn += columnIncrementer;
      }

      return wordBuilder.toString();
   }

}
