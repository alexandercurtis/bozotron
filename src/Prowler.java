package Robotron;
import java.awt.*;


/******************************************************************************
 * The Prowler class                                                          *
 ******************************************************************************/
class Prowler extends Baddie
{
 // static final int DEFAULT_SAMPLE_INTERVAL = 10;
  static final double PROWLER_STUPIDITY = 80.0; // Affects how many frames to wait before aiming somewhere else.

  int m_frameHalf;
  int m_targetPosSampleInterval;
  int m_targetPosSampleIntervalReset;
  int m_deadDx, m_deadDy;   // Velocity of sprite once killed.

/******************************************************************************
 * Prowler() constructor.                                                     *
 ******************************************************************************/
  Prowler(int frameBase, Point startPoint)
  {
    super(frameBase, startPoint);
    reset();
  }

/******************************************************************************
 * reset() method.                                                            *
 * Puts the baddie into its just-constructed state.                           *
 ******************************************************************************/
  void reset()
  {
    super.reset();
    m_speed = 2.0;
    m_targetPosSampleInterval = 0;
    m_targetPosSampleIntervalReset = (int)(Math.random() * PROWLER_STUPIDITY);
    m_deadDx = 0;
    m_deadDy = 0;
    m_frameHalf = 0;
  }

/******************************************************************************
 * setTarget() method.                                                    *
 ******************************************************************************/
  void setTarget(CCGame game)
  {
    setTargetLocation();
  }

/******************************************************************************
 * setTargetLocation() method.                                                *
 * Selects random place to aim for.                                           *
 ******************************************************************************/
  void setTargetLocation()
  {
    if(m_targetPosSampleInterval == 0 || reachedTarget()==true)
    {
      // Choose a new place to aim for
      m_targetPos.x = (int)(Math.random() * 512.0);   //TBD use applet size instead
      m_targetPos.y = (int)(Math.random() * 512.0);   //TBD use applet size instead
      m_targetPosSampleInterval = m_targetPosSampleIntervalReset;
    }
    else
      m_targetPosSampleInterval--;
  }

/******************************************************************************
 * reachedTarget() method.                                                    *
 * Detects whether the baddie has reached it's destination.                   *
 * TBD Also appears in Zombie. Consider sharing by moving up class hierarchy. *
 ******************************************************************************/
  boolean reachedTarget()
  {
    boolean reachedTarget = false;

    // Check if baddie thinks he has hit player
    if(m_targetPosSampleInterval != 0)
    {
      int dx = (int)m_px - m_targetPos.x;
      int dy = (int)m_py - m_targetPos.y;

      if(dx<0) dx = -dx;
      if(dy<0) dy = -dy;

      if(dx<SPRITE_SIZE && dy<SPRITE_SIZE)
      {
         reachedTarget = true;
      }
    }

    return reachedTarget;
  }

/******************************************************************************
 * animate() method.                                                          *
 * Updates the sprite's frame number in order to animate it.                  *
 * This one goes L:0,1,2,3 R:4,5,6,7                                          *
 ******************************************************************************/
  void animate()
  {
    if(m_state==1)
    {
      m_frame = 4;
    }
    else
    {
      if( m_frameHalf < 4 ) {
        ++m_frameHalf;
      } else {
        m_frameHalf = 0;
        if(m_targetPos.x > m_px)         // Could simplify the following logic TBC
        {
          if(m_frame<4)
            m_frame = 4;
          else
          {
            m_frame++;
            if(m_frame==8)
            {
              m_frame = 4;
            }
          }
        }
        else
        {
          if(m_frame>3)
            m_frame = 0;
          else
          {
            m_frame++;
            if(m_frame==4)
            {
              m_frame = 0;
            }
          }
        }
      //  m_frame = (int)m_px % 4;
      //  if(m_targetPos.x > m_px)
      //    m_frame += 4;
      }
    }
  }

/******************************************************************************
 * hit(int dx, int dy) method.                                                *
 * Called when sprite is hit by a bullet. Default behaviour is to start       *
 * explosion. dx and dy are the direction of bullet.                          *
 ******************************************************************************/
  /*void hit(double dx, double dy)
  {
    m_deadDx = (int)dx * 4;
    m_deadDy = (int)dy * 4;
  }*/

/******************************************************************************
 * update() method.                                                           *
 ******************************************************************************/
 /* Use default (exploding) behaviour
  void update( boolean frozen )
  {
    if(m_alive==false)
      return;

    if(m_state==1)
    {
      m_px += m_deadDx;
      m_py += m_deadDy;
      boundaryRect.setLocation((int)m_px, (int)m_py);
      if(m_px < 0 || m_px > 512 || m_py < 0 || m_py > 512)  // TBD no hardcoding, plus make sure sprite is off screen fully.
      {
        m_state = 0;
        m_alive = false;
      }
    }
    else
    {
      super.update( frozen );
    }
  }*/
}

