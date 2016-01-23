package Robotron;
import java.awt.*;


/******************************************************************************
 * The Ghost class                                                            *
 ******************************************************************************/
class Ghost extends Zombie
{
  static double GHOST_STUPIDITY = 200.0; // Affects how many frames baddies wait before spotting player's new position.
  static int GHOST_POOR_AIM = 128; // How much deviation a ghost has from player.

/******************************************************************************
 * Ghost() constructor.                                                       *
 ******************************************************************************/
  Ghost(int frameBase, Point startPoint)
  {
    super(frameBase, startPoint);
    reset();
  }

/******************************************************************************
 * animate() method.                                                          *
 * Updates the sprite's frame number in order to animate it.                  *
 * This one goes R:0 L:1 D:2 U:3 hit:4                                        *
 ******************************************************************************/
  void animate()
  {
    if( m_state == 1 ) {
      // Hit (or is it?) TBD
      m_frame = 5;
      //m_state = 0;
      return;
    }

    int dx = m_targetPos.x - (int)m_px;
    int dy = m_targetPos.y - (int)m_py;
    int adx = dx>0?dx:-dx;
    int ady = dy>0?dy:-dy;

    if( adx > ady ) {
      // Either left or right
      if( dx>0 ) {
        // Right
        m_frame = 0;
      } else {
        // Left
        m_frame = 1;
      }
    } else {
      // Either up or down
      if( dy>0 ) {
        // Down
        m_frame = 2;
      } else {
        // Up
        m_frame = 3;
      }
    }
  }

/******************************************************************************
 * hit(double dx, double dy) method.                                          *
 * Overrides baddie.hit().                                                    *
 ******************************************************************************/
  void hit(double dx, double dy)
  {
      super.hit(dx,dy);
      m_spread = 0;
      m_px += dx * m_speed;
      m_py += dy * m_speed;
      boundaryRect.setLocation((int)m_px, (int)m_py);
      m_state = 1; // Dying
  }

/******************************************************************************
 * update() method.                                                           *
 ******************************************************************************/
  void update( boolean frozen )
  {
    // Ghosts can't die
    if(m_state!=2)
    {
      m_state = 2;
    }

    super.update( frozen );
  }

/******************************************************************************
 * reset() method.                                                            *
 * Puts the baddie into its just-constructed state.                           *
 ******************************************************************************/
  void reset()
  {
    super.reset();
    m_speed = 2.0;
  }

/******************************************************************************
 * setTargetRect() method.                                                    *
 * Selects position of target to aim for.                                     *
 ******************************************************************************/
  void setTargetRect(Rectangle r)
  {
    int targetX = r.x + r.width/2;
    int targetY = r.y + r.height/2;

    // Ghosts can't see player very well so they jitter about a bit.
    targetX += (int)(Math.random()*2.0 - 1.0) * GHOST_POOR_AIM;
    targetY += (int)(Math.random()*2.0 - 1.0) * GHOST_POOR_AIM;

    setTarget(targetX, targetY);
  }
}

