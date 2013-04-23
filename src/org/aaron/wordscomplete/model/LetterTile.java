package org.aaron.wordscomplete.model;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 7:14 PM
 */
public class LetterTile {

   private char letter;
   private boolean isBlank = true;

   public LetterTile (char tileLetter, boolean isBlank) {
      this.letter = tileLetter;
      this.isBlank = isBlank;
   }

   public LetterTile (char letter) {
      this(letter, false);
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

}
