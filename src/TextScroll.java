package Robotron;
import java.awt.*;

/******************************************************************************
 * The TextScroll class                                                       *
 * Represents one string of text which scrolls                                *
 ******************************************************************************/
class TextScroll extends TextString
{
  int m_offset; // Number of pixels into character at which to start drawing.
  int m_startChar;  // Number of chars into the message at which to start drawing.
  static int SCROLL_LENGTH = 512/12+2;  // Number of characters in a scroll line, including clipped ones.
  static int SCROLL_INCREMENT = 2;  // Number of pixels to scroll by.
  static int BUFFER_SIZE = 512;  // Max number of chars per string.   (Overrides TextString size)

  TextScroll()
  {
    m_text = new int[BUFFER_SIZE];
    clear();
  }

  void clear()
  {
    super.clear();
    m_offset = 0;
    m_startChar = 0;
  }

/******************************************************************************
 * It requires that Sprite.SetImage() has been already called.                *
 ******************************************************************************/
  void draw(Graphics g)
  {
    int x = 0-m_offset;
    int y = m_y;

    int i=m_startChar;
    for(int n=0; n<SCROLL_LENGTH; n++)
    {
      // Check if we've reached the end of the string
      // If so, wrap scroll text.
      if(m_text[i]==0)
      {
        i = 0;
      }

      // Draw the character to the screen
      Sprite.draw(g, m_text[i], x, y, CHAR_SIZE, CHAR_SIZE, 0);

      // Move along the screen to the next character.
      x+=CHAR_WIDTH;
      i++;
    }
  }

/******************************************************************************
 * update() method.                                                           *
 * scrolls the text along one unit.                                           *
 ******************************************************************************/
  void update()
  {
    m_offset += SCROLL_INCREMENT;
    if(m_offset > CHAR_WIDTH)
    {
      m_offset %= CHAR_WIDTH;
      m_startChar++;
      if(m_startChar>=m_length)
      {
        m_startChar = 0;
      }
    }
  }

}

