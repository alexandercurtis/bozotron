package Robotron;
import java.awt.*;

/******************************************************************************
 * The TextDisplay class                                                      *
 * Displays sprites from the vram image as if they were characters in a font. *
 * The text is grouped into strings, each represented as a TextString class.  *
 * Hopefully it will also support moving text and other fx.                   *
 ******************************************************************************/
class TextDisplay
{
  static final int MAX_STRINGS = 4;  // How many individual strings to control.
  TextString[] m_textStrings;  // The strings themselves.
  int m_nextFreeString; // Index of the next free string.

  TextDisplay()
  {
    m_textStrings = new TextString[MAX_STRINGS];
    for(int i=0; i<MAX_STRINGS; i++)
    {
      m_textStrings[i] = new TextString();
    }

    clear();
  }
  int print(int value, int padSize, int x, int y)
  {
    int stringID = allocate();
    m_textStrings[stringID].set(value,padSize,x,y);
    return stringID;
  }

  int print(String msg, int x, int y)
  {
    int stringID = allocate();
    m_textStrings[stringID].set(msg,x,y);
    return stringID;
  }

  int allocate()
  {
    int stringID = m_nextFreeString;

    m_nextFreeString++;

    // Wrap the string index so that over allocation is easily spotted.
    if(m_nextFreeString == MAX_STRINGS)
    {
      m_nextFreeString = 0;
    }

    return stringID;
  }

  void set(int stringID, String msg, int x, int y)
  {
    m_textStrings[stringID].set(msg, x, y);
  }

  void set(int stringID, String msg)
  {
    m_textStrings[stringID].set(msg);
  }

  void set(int stringID, int value, int padSize, int x, int y)
  {
    m_textStrings[stringID].set(value, padSize, x, y);
  }

  void reduceLength(int stringID, int newLength)
  {
    m_textStrings[stringID].reduceLength(newLength);
  }

  void clear()
  {
    for(int i=0; i<MAX_STRINGS; i++)
    {
      m_textStrings[i].clear();
    }
    m_nextFreeString = 0;
  }

  void nullify(int i)
  {
    m_textStrings[i].clear();
  }

  void draw(Graphics g)
  {
    for(int i=0; i<MAX_STRINGS; i++)
    {
      m_textStrings[i].draw(g);
    }
  }
}
