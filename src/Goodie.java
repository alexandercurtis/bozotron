package Robotron;
import java.awt.*;


/******************************************************************************
 * The Goodie class                                                           *
 ******************************************************************************/
class Goodie extends Sprite
{
  int m_negWalkLimit, m_posWalkLimit;
  int m_startNegWalkLimit, m_startPosWalkLimit;

  static final int SPEED = 1;

  // TBD use an enum here?
  public static final int eD_LEFT = 0;
  public static final int eD_RIGHT = 1;
  public static final int eD_UP = 2;
  public static final int eD_DOWN = 4;

  int m_direction, m_startDirection;
  int m_framecount;

/******************************************************************************
 * Goodie() constructor.                                                      *
 ******************************************************************************/
  Goodie(int baseFrame, Point p, int d, int negLimit, int posLimit)
  {
    super(baseFrame, p);

    m_startDirection = d;
    m_startPosWalkLimit = posLimit;
    m_startNegWalkLimit = negLimit;
    m_framecount = 0;

    reset();
  }

/******************************************************************************
 * reset() method.                                                            *
 * Puts the goodie into its just-constructed state.                           *
 ******************************************************************************/
  void reset()
  {
    super.reset();

    m_direction = m_startDirection;
    m_posWalkLimit = m_startPosWalkLimit;
    m_negWalkLimit = m_startNegWalkLimit;

    m_alive = true;
  }

/******************************************************************************
 * start() method.                                                            *
 ******************************************************************************/
/*  void start(Point p, int d, int negLimit, int posLimit)
  {
    super.start(p);
    m_direction = d;
    m_posWalkLimit = posLimit;
    m_negWalkLimit = negLimit;

  }*/

/******************************************************************************
 * update() method.                                                           *
 ******************************************************************************/
  void update()
  {
   if(m_alive==false)
     return;

   switch(m_direction)
    {
    case eD_RIGHT:
      if(boundaryRect.x >= m_posWalkLimit)
      {
        m_direction = eD_LEFT;
        m_frame = 0;
      }
      else
       boundaryRect.x += SPEED;
      break;
    case eD_LEFT:
      if(boundaryRect.x < m_negWalkLimit)
      {
        m_direction = eD_RIGHT;
        m_frame = 4;
      }
      else
       boundaryRect.x -= SPEED;
      break;
    case eD_UP:
      if(boundaryRect.y < m_negWalkLimit)
      {
        m_direction = eD_DOWN;
        m_frame = 4;
      }
      else
        boundaryRect.y -= SPEED;
      break;
    case eD_DOWN:
      if(boundaryRect.y >= m_posWalkLimit)
      {
        m_direction = eD_UP;
        m_frame = 0;
      }
      else
        boundaryRect.y += SPEED;
      break;
    }

    animate();
  }



/******************************************************************************
 * animate() method.                                                          *
 * Updates the sprite's frame number in order to animate it.                  *
 ******************************************************************************/
  void animate()
  {
    ++m_framecount;
    if( m_framecount == 4 * 7 ) { // 4 screen frames per anim stage. 7 anim stages per cycle.
      m_framecount = 0;
    }

    // Convert a framecount to an actual frame
    m_frame = m_framecount / 4;
    if( m_frame >= 4 ) {
      m_frame = 6 - m_frame; // Since frames 4,5,6 are same graphics as 0,1,2.
    }

    // Finally choose the block of graphics for the proper direction
    if( m_direction == eD_RIGHT || m_direction == eD_DOWN ) {
      m_frame += 4;
    }
  }

/******************************************************************************
 * collide() method.                                                          *
 ******************************************************************************/
  boolean collide(Player p)
  {
    if(m_alive==true && boundaryRect.intersects(p.getBoundaryRect())==true)
    {
      m_alive = false;
      return true;
    }

    return false;
  }

}


