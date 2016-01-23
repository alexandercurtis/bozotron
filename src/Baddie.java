package Robotron;
import java.awt.*;

/******************************************************************************
 * The Baddie class                                                           *
 * A generic baddie which homes in on 128,128. Not very useful, designed to   *
 * be overridden.                                                             *
 ******************************************************************************/
class Baddie extends Sprite
{
  double m_px,m_py;
  Point m_targetPos = new Point(0,0);
  int m_state;  //0=dead, 1=dying, 2=alive
  double m_speed;
  int m_collectable;  // Index of the collectable which this baddie drops, -1 if none.

  static final int SMART_SPEED = 3; // How fast baddies move off screen after a smart bomb.

/******************************************************************************
 * Baddie() constructor.                                                      *
 ******************************************************************************/
  Baddie(int frameBase, Point startPoint)
  {
    super(frameBase, startPoint);
    reset();
    m_collectable = -1;
  }

/******************************************************************************
 * reset() method.                                                            *
 * Puts the baddie into its just-constructed state.                           *
 ******************************************************************************/
  void reset()
  {
    super.reset();
    m_speed = 4.0;
    m_state = 0;
  }

/******************************************************************************
 * start() method.                                                            *
 ******************************************************************************/
  void start(Point p)
  {
    m_px = (double)p.x;
    m_py = (double)p.y;
    m_state = 2;
    m_startPoint = p;
    boundaryRect.setBounds(m_startPoint.x,m_startPoint.y,SPRITE_SIZE,SPRITE_SIZE);
    m_alive = true;
  }

/******************************************************************************
 * collide() method.                                                          *
 ******************************************************************************/
  boolean collide(Bullet b)
  {
    if(m_alive==false)
      return false;

    if(m_state==2 && boundaryRect.contains(b.getLocation()))
    {
      m_state=1;
      hit(b.getVelocityX(), b.getVelocityY());
      return true;
    }
    return false;
  }


/******************************************************************************
 * smartBomb() method.                                                        *
 * Effects a hit from a smart bomb located at p.                              *
 ******************************************************************************/
  boolean smartBomb(Rectangle rect)
  {
    if(m_alive==false)
      return false;

    if(m_state==2) // state 2 => alive, i.e. not already dying
    {
      m_state=1;
      double dx = (m_px - (rect.x + rect.width/2));
      double dy = (m_py - (rect.y + rect.height/2));
      double r = Math.sqrt(dx*dx + dy*dy);
      hit( SMART_SPEED * dx/r, SMART_SPEED * dy/r);
      return true;
    }
    return false;
  }

/******************************************************************************
 * hit(double dx, double dy) method.                                          *
 * Called when sprite is hit by a bullet. Default behaviour is to start       *
 * explosion.  dx and dy are the direction of bullet.                         *
 ******************************************************************************/
  void hit(double dx, double dy)
  {
    m_spread = 2;
  }

/******************************************************************************
 * setTarget() method.                                                        *
 ******************************************************************************/
  void setTarget(CCGame game)
  {
    // Derived classes will want to override this.
    setTargetRect(new Rectangle(128,128,1,1));
  }

/******************************************************************************
 * setCollectable() method.                                                   *
 ******************************************************************************/
  void setCollectable(int collectable)
  {
    m_collectable = collectable;
  }

/******************************************************************************
 * getCollectable() method.                                                   *
 ******************************************************************************/
  int getCollectable()
  {
    return m_collectable;
  }

/******************************************************************************
 * setTargetRect() method.                                                    *
 * Selects position of target to aim for.                                     *
 ******************************************************************************/
  void setTargetRect(Rectangle r)
  {
    m_targetPos.x = r.x + r.width/2;
    m_targetPos.y = r.y + r.height/2;
  }

/******************************************************************************
 * update() method.                                                           *
 ******************************************************************************/
  void update( boolean frozen )
  {
    if(m_alive==false)
      return;

    animate();

    if(m_state==1)
    {
      m_spread *= 2;
      if(m_spread > 20)
      {
        m_state=0;
      }
    }
    else if(m_state==0)
    {
      m_alive = false;
    }
    else if( !frozen )
    {
        // Calculate velocity vector to aim towards target.
        double dx = m_targetPos.x - m_px;
        double dy = m_targetPos.y - m_py;
        double scale = m_speed / Math.sqrt(dx * dx + dy * dy);
        m_px += dx * scale;
        m_py += dy * scale;

        boundaryRect.setLocation((int)m_px, (int)m_py);
     }

  }

/******************************************************************************
 * animate() method.                                                          *
 * Updates the sprite's frame number in order to animate it.                  *
 ******************************************************************************/
  void animate()
  {
    if(m_state==1)
    {
      m_frame = 1;
    }
    else
    {
      m_frame = 0;
    }
  }

}

