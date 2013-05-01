package org.aaron.wordscomplete.solver;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * User: aprobus
 * Date: 4/27/13
 * Time: 9:50 PM
 */
public class PositionIteratorTest {

   @Test
   public void testIterations() {
      PositionIterator positionIterator = new PositionIterator(12);
      List<Coordinate> coordinates;

      Assert.assertTrue(positionIterator.hasNext());
      coordinates = positionIterator.next();
      Assert.assertEquals(0, coordinates.get(0).getColumn());
      Assert.assertEquals(0, coordinates.get(0).getRow());

      Assert.assertTrue(positionIterator.hasNext());
      coordinates = positionIterator.next();
      Assert.assertEquals(1, coordinates.get(0).getColumn());
      Assert.assertEquals(0, coordinates.get(0).getRow());

      Assert.assertTrue(positionIterator.hasNext());
      coordinates = positionIterator.next();
      Assert.assertEquals(2, coordinates.get(0).getColumn());
      Assert.assertEquals(0, coordinates.get(0).getRow());

      Assert.assertTrue(positionIterator.hasNext());
      coordinates = positionIterator.next();
      Assert.assertEquals(3, coordinates.get(0).getColumn());
      Assert.assertEquals(0, coordinates.get(0).getRow());

      Assert.assertTrue(positionIterator.hasNext());
      coordinates = positionIterator.next();
      Assert.assertEquals(0, coordinates.get(0).getColumn());
      Assert.assertEquals(1, coordinates.get(0).getRow());

      assertPositionsWithinBounds(positionIterator);
   }

   private void assertPositionsWithinBounds(PositionIterator positionIterator) {
      while(positionIterator.hasNext()) {
         List<Coordinate> coordinates = positionIterator.next();

         for (Coordinate coordinate : coordinates) {
            Assert.assertTrue(coordinate.getRow() >= 0);
            Assert.assertTrue(coordinate.getColumn() >= 0);

            Assert.assertTrue(coordinate.toString() + " is out of bounds!", coordinate.getRow() < Board.NUM_ROWS);
            Assert.assertTrue(coordinate.toString() + " is out of bounds!", coordinate.getColumn() < Board.NUM_COLUMNS);
         }
      }
   }

}
