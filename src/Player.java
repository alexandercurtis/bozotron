package Robotron;
import java.awt.*;

/******************************************************************************
 * The Player class                                                           *
 ******************************************************************************/
class Player extends Sprite
{
  static final int SPEED = 3;
  int m_state;  //0=dead, 1=dying, 2=alive
  int m_legpos; // Frame position of legs
  int m_frameBaseLeft; // First frame in walk left sequence
  int m_frameBaseRight; // First frame in walk right sequence
  int m_domainXmin, m_domainYmin; // The limits on the players movement
  int m_domainXmax, m_domainYmax; // The limits on the players movement

/******************************************************************************
 * Player() constructor.                                                      *
 ******************************************************************************/
  Player(int baseFrameLeft, int baseFrameRight, Point p, int domainXmin, int domainYmin, int domainXmax, int domainYmax )
  {
    super(baseFrameLeft, p);

    m_frameBaseLeft = baseFrameLeft;
    m_frameBaseRight = baseFrameRight;

    m_domainXmin = domainXmin;
    m_domainYmin = domainYmin;
    m_domainXmax = domainXmax;
    m_domainYmax = domainYmax;

    reset();
  }

/******************************************************************************
 * start() method.    deprecated use reset                                    *
 ******************************************************************************/
/*  void start(Point p)
  {
    super.start(p);
    m_state = 2;
  }   */

/******************************************************************************
 * reset() method.                                                            *
 ******************************************************************************/
  void reset()
  {
    super.reset();
    m_alive = true;
    m_state = 2;
    m_legpos = 0;
  }

/******************************************************************************
 * canShoot() method.                                                         *
 ******************************************************************************/
  boolean canShoot()
  {
    return(m_alive == true && m_state == 2);
  }

/******************************************************************************
 * update() method.                                                           *
 ******************************************************************************/
  void update(boolean left, boolean right, boolean up, boolean down, Point aim)
  {
    if(m_alive==false)
      return;

    // Calculate which point of the compass the gun is pointing to.
    int gunframe = 0;
    int dx = aim.x - boundaryRect.x - 16;
    int dy = aim.y - boundaryRect.y - 16;
    if( dx == 0 ) {
      gunframe = (dy>0)?0:4; // Gun is vertical
    } else {
      int t = (dy*10)/(dx<0?-dx:dx);
      if( t > 20 ) { gunframe = 4; }
      else if( t > 5 ) { gunframe = 3; }
      else if( t > -5 ) { gunframe = 2; }
      else if( t > -20 ) { gunframe = 1; }
    }

    if(m_state==1)
    {
      //m_state=0;
      //m_alive = false;
      m_frame = -2;
      m_frameBase = m_frameBaseLeft;
    }
    else
    {
      if(left) {
        boundaryRect.x -= SPEED;
      }
      else if(right) {
        boundaryRect.x += SPEED;
      }
      if(up) {
        boundaryRect.y -= SPEED;
      }
      else if(down) {
        boundaryRect.y += SPEED;
      }
	if( left || up ) {
	  m_legpos--;
      } else if( right || down ) {
        m_legpos--;
	}
	if( dx < 0 ) {
        m_frameBase = m_frameBaseLeft;
	} else if( dx > 0 ) {
        m_frameBase = m_frameBaseRight;
	}


      if( m_legpos < 0 ) { m_legpos = 6; }
      else if( m_legpos > 6 ) { m_legpos = 0; }

      if(boundaryRect.x >= m_domainXmax )  // TBD use applet width
        boundaryRect.x = m_domainXmax -1;
      else if(boundaryRect.y >= m_domainYmax )  // TBD use applet width
        boundaryRect.y = m_domainYmax -1;
      if(boundaryRect.x < m_domainXmin)  // TBD use applet height
        boundaryRect.x = m_domainXmin;
      else if(boundaryRect.y < m_domainYmin)  // TBD use applet height
        boundaryRect.y = m_domainYmin;
      // Calculate sprite frame
      m_frame = (m_legpos>=4?6-m_legpos:m_legpos) * 5 + gunframe; // 0<=gunframe<5

    }
  }


/******************************************************************************
 * collide(Sprite s) method.                                                  *
 * Checks whether the sprite given has overlapped the player, and kills the   *
 * player if it has.                                                          *
 ******************************************************************************/
  boolean collide(Baddie bdd)
  {
    if(m_alive==true && boundaryRect.intersects(bdd.getBoundaryRect())==true)
    {
      m_state = 1;
      return true;
    }

    return false;
  }

/******************************************************************************
 * getPoint() method.                                                         *
 * Returns coordinates of centre of player.                                   *
 ****************************************************************************** not used atm
  Point getPoint()
  {
    return new Point(boundaryRect.x + boundaryRect.width/2, boundaryRect.y + boundaryRect.height/2);
  }*/
}

