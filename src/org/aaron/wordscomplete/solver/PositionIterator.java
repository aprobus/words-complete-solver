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

   private ThreadLocal<ArrayList<Coordinate>> mLocalCoordinateList;
   private ThreadLocal<ArrayList<Coordinate>> mLocalPreAllocCoordinateList;

   public PositionIterator(int wordSize) {
      mWordSize = wordSize;

      mCurrentRow = 0;
      mCurrentColumn = 0;
      mDirection = Direction.Right;

      mLocalCoordinateList = new ThreadLocal<ArrayList<Coordinate>>();
      mLocalCoordinateList.set(new ArrayList<Coordinate>(16));

      mLocalPreAllocCoordinateList = new ThreadLocal<ArrayList<Coordinate>>();
      mLocalPreAllocCoordinateList.set(new ArrayList<Coordinate>(16));

      ArrayList<Coordinate> preallocCoordinates = mLocalPreAllocCoordinateList.get();
      for (int i = 0; i < 16; i++) {
         preallocCoordinates.add(new Coordinate(1, 1));
      }
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
      List<Coordinate> coordinates = mLocalCoordinateList.get();
      coordinates.clear();

      ArrayList<Coordinate> preallocCoordinates = mLocalPreAllocCoordinateList.get();

      for (int i = 0; i < mWordSize; i++) {
         if (mDirection == Direction.Right) {
            //coordinates.add(new Coordinate(mCurrentRow, mCurrentColumn + i));
            coordinates.add(preallocCoordinates.get(i).setCoordinate(mCurrentRow, mCurrentColumn + i));
         } else {
            //coordinates.add(new Coordinate(mCurrentRow + i, mCurrentColumn));
            coordinates.add(preallocCoordinates.get(i).setCoordinate(mCurrentRow + i, mCurrentColumn));
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
