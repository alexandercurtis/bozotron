package Robotron;
import java.awt.*;


/******************************************************************************
 * The Flame class                                                           *
 ******************************************************************************/
class Flame extends Baddie
{
 // static final int DEFAULT_SAMPLE_INTERVAL = 10;
  static double FLAME_STUPIDITY = 30.0; // Affects how many frames baddies wait before spotting player's new position.

  int m_targetPosSampleInterval;
  int m_targetPosSampleIntervalReset;

/******************************************************************************
 * Zombie() constructor.                                                      *
 ******************************************************************************/
  Flame(int frameBase, Point startPoint)
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
    m_targetPosSampleInterval = 0;
    m_targetPosSampleIntervalReset = (int)(Math.random() * FLAME_STUPIDITY);
  }

/******************************************************************************
 * setTarget() method.                                                        *
 ******************************************************************************/
  void setTarget(CCGame game)
  {
    if(game.m_playerInvisible) {
      // Make up a target if the player is invisible
      setTarget( (int)(Math.random() * 512.0), (int)(Math.random() * 512.0) );   //TBD use applet size instead
    } else {
      setTargetRect(game.m_plr.getBoundaryRect());
    }
  }

/******************************************************************************
 * setTargetRect() method.                                                    *
 * Selects position of target to aim for.                                     *
 ******************************************************************************/
  void setTargetRect(Rectangle r)
  {
    int targetX = r.x + r.width/2;
    int targetY = r.y + r.height/2;

    setTarget(targetX, targetY);
  }


/******************************************************************************
 * setTarget(int targetX, int targetY) method.                                *
 ******************************************************************************/
  void setTarget(int targetX, int targetY)
  {
    if(m_targetPosSampleInterval == 0 || reachedTarget()==true)
    {
      m_targetPos.x = targetX;
      m_targetPos.y = targetY;
      m_targetPosSampleInterval = m_targetPosSampleIntervalReset;
    }
    else
      m_targetPosSampleInterval--;
  }

/******************************************************************************
 * reachedTarget() method.                                                    *
 * Detects whether the baddie has reached it's destination.                   *
 * TBD Also appears in Prowler. Consider sharing by moving up class hierarchy.*
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
 * hit(int dx, int dy) method.                                                *
 * Called when sprite is hit by a bullet. Default behaviour is to start       *
 * explosion. dx and dy are the direction of bullet.                          *
 ******************************************************************************/
  void hit(double dx, double dy)
  {
      m_frame = 4;
  }

/******************************************************************************
 * animate() method.                                                          *
 * Updates the sprite's frame number in order to animate it.                  *
 ******************************************************************************/
  void animate()
  {
    if(m_frame!=4)
    {
      m_frame = (m_frame+1) % 4;
    }
  }
}

