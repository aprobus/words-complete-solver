package org.aaron.wordscomplete.model;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 7:25 PM
 */
public class PlacedTile {

   private Coordinate coordinate;
   private LetterTile tile;

   public PlacedTile(LetterTile tile, Coordinate coordinate) {
      this.tile = tile;
      this.coordinate = coordinate;
   }

   public Coordinate getCoordinate () {
      return coordinate;
   }

   public LetterTile getTile () {
      return tile;
   }

}
