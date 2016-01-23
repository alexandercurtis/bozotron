package Robotron;
import java.awt.*;


/******************************************************************************
 * The Collectable class                                                      *
 * Represents pick-up/power-up items.                                         *
 ******************************************************************************/
class Collectable extends Sprite
{
  double m_px,m_py;
  double m_vx,m_vy;
  int m_score; // How many points the collectable is worth
  int m_effect; // The effect of picking up the collectable

/******************************************************************************
 * Collectable() constructor.                                                 *
 ******************************************************************************/
  Collectable(int baseFrame, Point p, int score, int effect)
  {
    super(baseFrame, p);
    reset();
    m_score = score;
    m_effect = effect;
  }

/******************************************************************************
 * reset() method.                                                            *
 * Puts the goodie into its just-constructed state.                           *
 ******************************************************************************/
  void reset()
  {
    super.reset();
    m_alive = false;
  }

/******************************************************************************
 * start() method.                                                            *
 ******************************************************************************/
  void start(Point p, double vx, double vy)
  {
    m_alive = true;

    m_startPoint = p;
    boundaryRect.setBounds(m_startPoint.x,m_startPoint.y,SPRITE_SIZE,SPRITE_SIZE);
    m_px = (double)p.x;
    m_py = (double)p.y;
    m_vx = vx;
    m_vy = vy;
  }

/******************************************************************************
 * update() method.                                                           *
 ******************************************************************************/
  void update()
  {
   if(m_alive==false)
     return;

   m_px += m_vx;
   m_py += m_vy;
   boundaryRect.x = (int)m_px;
   boundaryRect.y = (int)m_py;

   // Check whether collectable has gone off screen.
   if(boundaryRect.x < -16
    || boundaryRect.x > 512
    || boundaryRect.y < -16
    || boundaryRect.y > 512)
    {
      m_alive = false;
    }
  }


/******************************************************************************
 * score() method.                                                            *
 ******************************************************************************/
  int score()
  {
    return m_score;
  }


/******************************************************************************
 * effect() method.                                                            *
 ******************************************************************************/
  int effect()
  {
    return m_effect;
  }

}


