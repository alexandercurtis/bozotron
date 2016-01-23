package Robotron;
import java.awt.*;

/******************************************************************************
 * The DebugDisplay class                                                     *
 * Used to print debug information to the screen.                             *
 ******************************************************************************/
class DebugDisplay
{
  int m_x;  // Coordinates to draw next line of info.
  int m_y;
  static final int LINE_SPACING = 20;
  StringBuffer m_text;

  DebugDisplay()
  {
    m_text = new StringBuffer();
    clear();
  }

  void print(String msg)
  {
    m_text.append(msg);
  }

  void clear()
  {
    m_x = 16;
    m_y = 40;
    //m_text.delete(0,m_text.length()); // Clear the string buffer.
    m_text.setLength(0); // Clear the string buffer. (delete didn't work for some reason)

  }

  void draw(Graphics g)
  {
    g.setFont( new Font("SansSerif", Font.PLAIN, 12) );
    g.setColor( Color.white );
    g.drawString(m_text.toString(), m_x, m_y);
  }
}


