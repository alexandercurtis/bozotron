package Robotron;
import java.awt.*;


/******************************************************************************
 * The Prowler class                                                          *
 ******************************************************************************/
class Grunt extends Baddie
{
  static final double GRUNT_STUPIDITY = 30.0; // Affects how many frames to wait before aiming somewhere else.

  int m_targetPosSampleInterval;
  int m_targetPosSampleIntervalReset;

/******************************************************************************
 * Prowler() constructor.                                                     *
 ******************************************************************************/
  Grunt(int frameBase, Point startPoint)
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
    m_targetPosSampleIntervalReset = (int)(Math.random() * GRUNT_STUPIDITY);
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
 ******************************************************************************/
  void animate()
  {
    if(m_state!=1)
    {
      m_frame = (m_frame+1) %3;
    }
  }
}

