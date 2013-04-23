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

   public Coordinate setCoordinate(int row, int column) {
      this.row = row;
      this.column = column;
      return this;
   }

}
