package Robotron;
import java.awt.*;

/******************************************************************************
 * The TextString class                                                       *
 * Represents one string of text, with it's location and any fx state.        *
 ******************************************************************************/
class TextString
{
  int m_x, m_y; // Location of the text string on the screen.
  static int BUFFER_SIZE = 64;  // Max number of chars per string.
  static final int CHAR_WIDTH = 12; // Width in pixels of a character image ie. spacing. Used to clip sprites, and possibly kern.
  static final int CHAR_SIZE = 16; // All text character sprites are square, with this dimension, on a grid of this spacing in vram image.
  int[] m_text; // The actual text string.
  int m_length; // Number of chars in string , excluding NULL.

  TextString()
  {
    m_text = new int[BUFFER_SIZE];
    clear();
  }


/******************************************************************************
 * set(int value, int padSize, int x, int y) method.                          *
 * Converts an integer to a TextString, padding with leading zeroes up to the *
 * specified padSize.                                                         *
 ******************************************************************************/
  void set(int value, int padSize, int x, int y)
  {
    m_x = x;
    m_y = y;

    set(value, padSize);
  }

  void set(int value, int padSize)
  {
    if(padSize > BUFFER_SIZE)
    {
      padSize = BUFFER_SIZE;
    }

    int characterIndex;
    int column = padSize;

    while(column >= 0)
    {
      m_text[column] = (int)('0') + (value % 10);
      column--;
      value /= 10;
    }

    m_length = padSize;
  }

  void set(String msg, int x, int y)
  {
    m_x = x;
    m_y = y;

    set(msg);
  }

  void reduceLength(int newLength)
  {
    if(newLength < BUFFER_SIZE && newLength >= 0)
    {
      m_text[newLength] = 0;
      m_length = newLength;
    }
  }

/******************************************************************************
 * This is potentially platform dependent. Look at using getBytes or something*
 * Also it doesn't check that the index generated is within the bounds of the *
 * vram image.                                                                *
 ******************************************************************************/
  void set(String msg)
  {
    // Convert the string into sprite indices.
    int i;
    for(i=0; i<msg.length(); i++)
    {
      m_text[i] = (int)(msg.charAt(i));
    }
    // Terminate with 0.
    m_text[i] = 0;
    m_length = i;

  }

  void clear()
  {
    m_x = 0;
    m_y = 0;
    m_text[0] = 0;
    m_length = 0;
  }

/******************************************************************************
 * It requires that Sprite.SetImage() has been already called.                *
 ******************************************************************************/
  void draw(Graphics g)
  {
    int x = m_x;
    int y = m_y;

    for(int i=0; i<m_length; i++)
    {
      // Check if we've reached the end of the string
      // Shouldn't happen if m_length is working.
      if(m_text[i]==0)
        break;

      // Draw the character to the screen
      Sprite.draw(g, m_text[i], x, y, CHAR_SIZE, CHAR_SIZE, 0);

      // Move along the screen to the next character.
      x+=CHAR_WIDTH;
    }
  }

}

