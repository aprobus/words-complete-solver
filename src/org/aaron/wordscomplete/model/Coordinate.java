package org.aaron.wordscomplete.model;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 7:20 PM
 */
public class Coordinate {

   public int row;
   public int column;

   public Coordinate (int row, int column) {
      this.row = row;
      this.column = column;
   }

   public Coordinate(Coordinate coordinate) {
      this.row = coordinate.row;
      this.column = coordinate.column;
   }

   public Coordinate() {
      this(-1, -1);
   }

   public boolean isValid() {
      return row >= 0 && row < Board.NUM_ROWS && column >= 0 && column < Board.NUM_COLUMNS;
   }

   public boolean equalTo(int row, int column) {
      return this.row == row && this.column == column;
   }

   public Coordinate setCoordinate(int row, int column) {
      this.row = row;
      this.column = column;
      return this;
   }

   public void setCoordinate(Coordinate coordinate) {
      this.row = coordinate.row;
      this.column = coordinate.column;
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
