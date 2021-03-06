package org.aaron.wordscomplete.solver;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: aprobus
 * Date: 1/26/13
 * Time: 2:00 PM
 */
public class PositionIterator implements Iterator<List<Coordinate>> {
   private int mWordSize;

   private int mCurrentRow;
   private int mCurrentColumn;
   private Direction mDirection;

   public PositionIterator(int wordSize) {
      mWordSize = wordSize;

      mCurrentRow = 0;
      mCurrentColumn = 0;
      mDirection = Direction.Right;
   }

   @Override
   public boolean hasNext() {
      if (mDirection == Direction.Right) {
         return true;
      } else {
         return mCurrentColumn < Board.NUM_COLUMNS && mCurrentRow + mWordSize - 1 < Board.NUM_ROWS;
      }
   }

   @Override
   public List<Coordinate> next() {
      List<Coordinate> coordinates = new ArrayList<Coordinate>(15);

      for (int i = 0; i < mWordSize; i++) {
         if (mDirection == Direction.Right) {
            coordinates.add(Coordinate.newCoordinate(mCurrentRow, mCurrentColumn + i));
         } else {
            coordinates.add(Coordinate.newCoordinate(mCurrentRow + i, mCurrentColumn));
         }
      }

      mCurrentColumn++;
      if (mDirection == Direction.Right && mCurrentColumn + mWordSize > Board.NUM_COLUMNS) {
         mCurrentColumn = 0;
         mCurrentRow++;

         if (mCurrentRow >= Board.NUM_ROWS) {
            mCurrentColumn = 0;
            mCurrentRow = 0;
            mDirection = Direction.Down;
         }
      } else if (mDirection == Direction.Down && mCurrentColumn >= Board.NUM_COLUMNS) {
         mCurrentColumn = 0;
         mCurrentRow++;
      }

      return coordinates;
   }

   @Override
   public void remove() {
   }

   private enum Direction {
      Right,
      Down
   }

}
