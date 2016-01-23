package Robotron;
import java.applet.*;
import java.awt.*;

/******************************************************************************
 * The Sprite base class                                                      *
 ******************************************************************************/
class Sprite
{
  int m_frame;
  int m_frameBase;
  boolean m_alive;  // Whether this object is active in the game.
  int m_spread;  // Used to separate lines of the sprite, for explosion effects.

  static Image m_img; // The picture of the player
  static Applet m_app; // The parent applet (needed becuase it implements ImageObserver)
  static final int SPRITE_SIZE = 32; // All sprites are square, with this dimension.
  static final int VRAM_WIDTH = 32; // Width (in SPRITE_SIZE units) of the vram image.
  Rectangle boundaryRect; // The sprite's position on the screen.
  Point m_startPoint; // Where the sprite goes back to if reset

/******************************************************************************
 * Sprite() constructor.                                                      *
 ******************************************************************************/
  Sprite(int frameBase, Point startPoint)
  {
    boundaryRect = new Rectangle();
    m_startPoint = startPoint;
    m_frameBase = frameBase;
    reset();
  }

/******************************************************************************
 * reset() method.                                                            *
 * Puts the sprite into its just-constructed state.                           *
 ******************************************************************************/
  void reset()
  {
    boundaryRect.setBounds(m_startPoint.x,m_startPoint.y,SPRITE_SIZE,SPRITE_SIZE);
    m_frame = 0;
    m_alive = false;
    m_spread = 0;
  }

/******************************************************************************
 * start() method.                                                            *
 ******************************************************************************/
/*  void start()
  {
    m_alive = true;
  }     */

/******************************************************************************
 * setUpImage() method.                                                       *
 ******************************************************************************/
  static void setUpImage(Image i, Applet a)
  {
    m_img = i;
    m_app = a;

    // Maybe also set applet boundary size here too.
  }

/******************************************************************************
 * draw() method.                                                             *
 ******************************************************************************/
  void draw(Graphics g)
  {
    if(m_alive==false)
      return;

    draw(g,m_frameBase+m_frame, boundaryRect.x, boundaryRect.y, SPRITE_SIZE, SPRITE_SIZE, m_spread);
  }

  static void draw(Graphics g, int frame, int x, int y, int spread)
  {
    draw(g, frame, x, y, SPRITE_SIZE, SPRITE_SIZE, spread);
  }

  static void draw(Graphics g, int frame, int x, int y, int sourceSize, int destSize, int spread)
  {
    // Calculate coordinates of the frame in the 'vram' image
    int imgX = frame;
    int imgY = frame;
    imgX %= VRAM_WIDTH;
    imgY /= VRAM_WIDTH;
    imgX *= sourceSize;
    imgY *= sourceSize;

    // Blit the relevant part of the vram image to the specified backbuffer.
    if(spread==0)
    {
      g.drawImage(m_img, x, y,
      (x+sourceSize), (y+sourceSize),
      imgX, imgY, imgX+destSize, imgY+destSize, m_app);
    }
    else
    {
      int dy = y - spread * destSize / 2;
      int sy = imgY;
      for(int i = 0; i < destSize; i++)
      {
        g.drawImage(m_img, x, dy,
         (x+sourceSize), dy+1,
         imgX, sy, imgX+destSize, sy+1, m_app);
        dy += spread;
        sy += 1;
      }
    }
  }

/******************************************************************************
 * getBoundaryRect() accessor.                                                *
 ******************************************************************************/
  Rectangle getBoundaryRect() {
    return boundaryRect;
  }

/******************************************************************************
 * alive() accessor.                                                          *
 ******************************************************************************/
  boolean alive()
  {
    return m_alive;
  }

/******************************************************************************
 * kill() method.                                                            *
 ******************************************************************************/
  void kill()
  {
    m_alive = false;
  }

/******************************************************************************
 * collide() method.                                                          *
 ******************************************************************************/
  boolean collide(Sprite p)
  {
    if(m_alive==true && boundaryRect.intersects(p.getBoundaryRect())==true)
    {
      m_alive = false;
      return true;
    }

    return false;
  }

}


