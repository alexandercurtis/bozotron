package Robotron;
import java.awt.*;


/******************************************************************************
 * The Bullet class                                                           *
 ******************************************************************************/
class Bullet
{
  double m_px,m_py;
  Point m_position;

  double m_vx,m_vy;
  int m_opx, m_opy;

  final double SPEED = 6.0;

  static final Color BODY_COLOUR = new Color(255, 255, 255);
  static final Color TRAIL_COLOUR = new Color(255, 20, 10);
  static final double TRAIL_SCALE = 0.7;
  static final int START_LIFE = 100;

  boolean m_canBounce;
  int m_life;

  Rectangle dirtyRect = new Rectangle();

/******************************************************************************
 * Bullet() constructor.                                                      *
 ******************************************************************************/
  Bullet()
  {
    reset();
  }

/******************************************************************************
 * clear() method.                                                            *
 * Puts the bullet into its just-constructed state.                           *
 ******************************************************************************/
  void reset()
  {
    m_canBounce = false;
    m_life = 0;
  }

/******************************************************************************
 * start() method.                                                            *
 ******************************************************************************/
  void start(Rectangle start, Point dest, boolean canbounce)
  {
    // Calculate velocity vector
    double dx = dest.x - start.x - start.width/2;
    double dy = dest.y - start.y - start.height/2;
    double scale = SPEED / Math.sqrt(dx * dx + dy * dy);

    m_vx = dx * scale;
    m_vy = dy * scale;

    // Calculate position
    m_px = (double)start.x + start.width/2 + m_vx;
    m_py = (double)start.y + start.height/2 + m_vy;

    m_opx = (int)m_px;
    m_opy = (int)m_py;

    m_position = new Point((int)m_px, (int)m_py);

    m_canBounce = canbounce;

    m_life = START_LIFE;
  }

/******************************************************************************
 * draw() method.                                                             *
 ******************************************************************************/
  void draw(Graphics g)
  {
    if( m_life == 0 )
      return;

    g.setColor(TRAIL_COLOUR);
    g.fillRect(m_opx, m_opy, 2, 2);
    g.setColor(BODY_COLOUR);
    g.fillRect(m_position.x, m_position.y, 2, 2);

  }

/******************************************************************************
 * getLocation() accessor.                                                    *
 ******************************************************************************/
  Point getLocation()
  {
    return  m_position;
  }


/******************************************************************************
 * getVelocityX,Y() methods.                                                  *
 ******************************************************************************/
 double getVelocityX()
 {
   return m_vx;
 }
 double getVelocityY()
 {
   return m_vy;
 }

/******************************************************************************
 * update() method.                                                           *
 ******************************************************************************/
  void update()
  {
    if( m_life == 0 )
      return;

    --m_life;

    double minx = m_px;
    double miny = m_py;
    double maxx = m_px;
    double maxy = m_py;

    m_opx = (int)(m_px + m_vx * TRAIL_SCALE);
    m_opy = (int)(m_py + m_vy * TRAIL_SCALE);

    m_px += m_vx;
    m_py += m_vy;
    if(m_px > 512.0 || m_px < 0.0)
      if(m_canBounce == true)
        m_vx = -m_vx;
      else
        m_life = 0;
    if(m_py > 512.0 || m_py < 0.0)
      if(m_canBounce == true)
        m_vy = -m_vy;
      else
        m_life = 0;

    // Commented out dirty rect stuff:
    //if(m_px<minx)
    //  minx = m_px;
    //else if(m_px > maxx)
    //  maxx = m_px;

    //if(m_py<miny)
    //  miny = m_py;
    //else if(m_py>maxy)
    //  maxy = m_py;

    //dirtyRect.setBounds(minx, miny, maxx-minx+width, maxy-miny+height);
    // image not yet loaded, so hard code 16 for now: TBD
    //int iminx = (int)minx;
    //int iminy = (int)miny;
    //int imaxx = (int)maxx;
    //int imaxy = (int)maxy;
    //dirtyRect.setBounds(iminx, iminy, imaxx-iminx+16, imaxy-iminy+16);

    m_position.setLocation((int)m_px, (int)m_py);
  }

/******************************************************************************
 * alive() accessor.                                                          *
 ******************************************************************************/
  boolean alive()
  {
    return (m_life != 0);
  }

/******************************************************************************
 * kill() method.                                                             *
 ******************************************************************************/
  void kill()
  {
    m_life = 0;
  }

}

