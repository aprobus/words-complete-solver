package org.aaron.wordscomplete.model;

import java.util.ArrayList;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 7:20 PM
 */
public class Coordinate {

   private static Coordinate[] coordinates = new Coordinate[Board.NUM_COLUMNS * Board.NUM_ROWS];
   private static Coordinate invalidCoordinate = new Coordinate(-1, -1);

   private int row;
   private int column;

   public static Coordinate newCoordinate(int row, int column) {
      if (!inRange(row, 0, Board.NUM_ROWS) || !inRange(column, 0, Board.NUM_COLUMNS)) {
         return invalidCoordinate;
      }

      int index = toIndex(row, column);

      if (coordinates[index] == null) {
         coordinates[index] = new Coordinate(row, column);
      }

      return coordinates[index];
   }

   public static Coordinate newInvalidCoordinate() {
      return invalidCoordinate;
   }

   private static int toIndex(int row, int column) {
      return row * Board.NUM_COLUMNS + column;
   }

   private static boolean inRange(int value, int min, int max) {
      return value >= min && value < max;
   }

   private Coordinate (int row, int column) {
      this.row = row;
      this.column = column;
   }

   public int getRow() {
      return row;
   }

   public int getColumn() {
      return column;
   }

   public Coordinate withRow(int row) {
      return newCoordinate(row, column);
   }

   public Coordinate withColumn(int column) {
      return newCoordinate(row, column);
   }

   public Coordinate withRowIncremented(int rowDelta) {
      return newCoordinate(row + rowDelta, column);
   }

   public Coordinate withColumnIncremented(int columnDelta) {
      return newCoordinate(row, column + columnDelta);
   }

   public boolean isValid() {
      return row >= 0 && row < Board.NUM_ROWS && column >= 0 && column < Board.NUM_COLUMNS;
   }

   public boolean equalTo(int row, int column) {
      return this.row == row && this.column == column;
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof Coordinate)) {
         return false;
      }

      Coordinate other = (Coordinate)obj;
      return row == other.row && column == other.column;
   }

   @Override
   public int hashCode() {
      return row + column;
   }

   @Override
   public String toString() {
      return "(" + row + ", " + column + ")";
   }

}
