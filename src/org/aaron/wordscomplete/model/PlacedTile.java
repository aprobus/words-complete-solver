package org.aaron.wordscomplete.model;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 7:25 PM
 */
public class PlacedTile {

   private Coordinate coordinate;
   private LetterTile tile;

   public PlacedTile () {
      setPlacedTile(null, -1, -1);
   }

   public PlacedTile(LetterTile tile, Coordinate coordinate) {
      this.tile = tile;
      this.coordinate = coordinate;
   }

   public void setPlacedTile (LetterTile tile, int row, int column) {
      this.tile = tile;
      this.coordinate = new Coordinate(row, column);
   }

   public Coordinate getCoordinate () {
      return coordinate;
   }

   public LetterTile getTile () {
      return tile;
   }

}
