package org.aaron.wordscomplete.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: aprobus
 * Date: 4/30/13
 * Time: 5:23 PM
 */
public class CoordinateTest {

   @Test
   public void testValidCoordinate() {
      Coordinate coordinate = Coordinate.newCoordinate(0, 1);

      Assert.assertEquals(0, coordinate.getRow());
      Assert.assertEquals(1, coordinate.getColumn());
   }

   @Test
   public void testSameCoordinate() {
      Coordinate coordinate1 = Coordinate.newCoordinate(0, 0);
      Coordinate coordinate2 = Coordinate.newCoordinate(0, 0);

      Assert.assertTrue(coordinate1.equalTo(0, 0));
      Assert.assertTrue(coordinate1 == coordinate2); //Ensure same object
   }

   @Test
   public void testMaxCoordinate() {
      Coordinate coordinate1 = Coordinate.newCoordinate(Board.NUM_ROWS - 1, Board.NUM_COLUMNS - 1);
      Coordinate coordinate2 = Coordinate.newCoordinate(Board.NUM_ROWS - 1, Board.NUM_COLUMNS - 1);

      Assert.assertTrue(coordinate1.equalTo(Board.NUM_ROWS - 1, Board.NUM_COLUMNS - 1));
      Assert.assertTrue(coordinate1 == coordinate2); //Ensure same object
   }

   @Test
   public void testValidRowMax() {
      Assert.assertTrue(Coordinate.newCoordinate(Board.NUM_ROWS - 1, 0).isValid());
   }

   @Test
   public void testValidColumnMax() {
      Assert.assertTrue(Coordinate.newCoordinate(Board.NUM_COLUMNS - 1, 0).isValid());
   }

   @Test
   public void testInvalidRowMax() {
      Coordinate coordinate = Coordinate.newCoordinate(15, 0);

      Assert.assertFalse(coordinate.isValid());
   }

   @Test
   public void testInvalidRowMin() {
      Coordinate coordinate = Coordinate.newCoordinate(-1, 0);

      Assert.assertFalse(coordinate.isValid());
   }

   @Test
   public void testInvalidColumnMax() {
      Coordinate coordinate = Coordinate.newCoordinate(15, 0);

      Assert.assertFalse(coordinate.isValid());
   }

   @Test
   public void testInvalidColumnMin() {
      Coordinate coordinate = Coordinate.newCoordinate(-1, 0);

      Assert.assertFalse(coordinate.isValid());
   }

}
