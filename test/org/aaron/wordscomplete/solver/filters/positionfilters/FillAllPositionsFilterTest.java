package org.aaron.wordscomplete.solver.filters.positionfilters;

import org.aaron.wordscomplete.model.*;
import org.junit.Assert;
import org.aaron.wordscomplete.transformer.TileRackStringTransformer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: aprobus
 * Date: 5/7/13
 * Time: 10:07 PM
 */
public class FillAllPositionsFilterTest {

   private List<Coordinate> coordinates;

   @Before
   public void setUp() {
      coordinates = new ArrayList<Coordinate>();
   }

   @Test
   public void testValidOpen() {
      Board board = Board.ofType(BoardType.Scrabble);

      TileRack tileRack = TileRackStringTransformer.fromString("aa");

      coordinates.add(Coordinate.newCoordinate(0, 0));
      coordinates.add(Coordinate.newCoordinate(0, 1));

      FillAllPositionsFilter fillAllPositionsFilter = new FillAllPositionsFilter(board, tileRack);

      Assert.assertTrue(fillAllPositionsFilter.isValidPosition(coordinates));
   }

   @Test
   public void testInvalidOpen() {
      Board board = Board.ofType(BoardType.Scrabble);

      TileRack tileRack = TileRackStringTransformer.fromString("a");

      coordinates.add(Coordinate.newCoordinate(0, 0));
      coordinates.add(Coordinate.newCoordinate(0, 1));

      FillAllPositionsFilter fillAllPositionsFilter = new FillAllPositionsFilter(board, tileRack);

      Assert.assertFalse(fillAllPositionsFilter.isValidPosition(coordinates));
   }

   @Test
   public void testValidUsed() {
      Board board = Board.ofType(BoardType.Scrabble);
      board.setTile(0, 1, LetterTile.newTile('b'));

      TileRack tileRack = TileRackStringTransformer.fromString("aa");

      coordinates.add(Coordinate.newCoordinate(0, 0));
      coordinates.add(Coordinate.newCoordinate(0, 1));
      coordinates.add(Coordinate.newCoordinate(0, 2));

      FillAllPositionsFilter fillAllPositionsFilter = new FillAllPositionsFilter(board, tileRack);

      Assert.assertTrue(fillAllPositionsFilter.isValidPosition(coordinates));
   }

   @Test
   public void testInvalidUsed() {
      Board board = Board.ofType(BoardType.Scrabble);
      board.setTile(0, 1, LetterTile.newTile('b'));

      TileRack tileRack = TileRackStringTransformer.fromString("a");

      coordinates.add(Coordinate.newCoordinate(0, 0));
      coordinates.add(Coordinate.newCoordinate(0, 1));
      coordinates.add(Coordinate.newCoordinate(0, 2));

      FillAllPositionsFilter fillAllPositionsFilter = new FillAllPositionsFilter(board, tileRack);

      Assert.assertFalse(fillAllPositionsFilter.isValidPosition(coordinates));
   }

}
