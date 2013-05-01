package org.aaron.wordscomplete.model;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 7:14 PM
 */
public class LetterTile {

   public static LetterTile newBlankTile() {
      return new LetterTile('a', true);
   }

   private char letter;
   private boolean isBlank = true;

   public LetterTile (char tileLetter, boolean isBlank) {
      this.letter = tileLetter;
      this.isBlank = isBlank;
   }

   public LetterTile (char letter) {
      this(letter, false);
   }

   public LetterTile () {
      this('a', true);
   }

   public char getLetter() {
      return letter;
   }

   public void setLetter (char letter) {
      this.letter = letter;
   }

   public boolean isBlank() {
      return isBlank;
   }

   public void setBlank(boolean isBlank) {
      this.isBlank = isBlank;
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof LetterTile)) {
         return false;
      }

      LetterTile otherTile = (LetterTile)obj;

      return letter == otherTile.letter && isBlank == otherTile.isBlank;
   }

   @Override
   public int hashCode() {
      return letter;
   }

}
