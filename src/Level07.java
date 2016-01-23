package Robotron;
import java.awt.*;

/******************************************************************************
 * The Level07 class                                                          *
 * Hardcodes the data for level one.                                          *
 * ENSURE :                                                                   *
 * setupGoodies returns the total number of goodies.                          *
 * setupBaddies returns the total number of baddies, from adding up the       *
 * baddies created in setupAttackWaves.                                       *
 * setupAttackWaves returns the total number of attack waves.                 *
 * baddies deposit valid collectable indexes                                  *
 ******************************************************************************/
class Level07 implements Level
{
  // Set up goodies
  public int setupGoodies(Goodie[] gdd)       // TBD why do I need public on these?
  {
    gdd[0] = new Goodie(192, new Point(256,20), Goodie.eD_LEFT, 236,276);
    gdd[1] = new Goodie(192, new Point(256,452), Goodie.eD_RIGHT, 236,276);
    gdd[2] = new Goodie(192, new Point(20,256), Goodie.eD_UP, 236,276);
    gdd[3] = new Goodie(192, new Point(472,256), Goodie.eD_DOWN, 236,276);

    return 4;
  }

  public int setupBaddies(Baddie[] bdd)
  {
    for(int i=0; i<50; i++)
    {
      if( i < 10 )
        bdd[i] = new Prowler(87, new Point(0,0)); // TBC this new Point is redundant.
      else if( i<35 )
        bdd[i] = new Grunt(160, new Point(0,0)); // TBC this new Point is redundant.
      else
        bdd[i] = new Zombie(64, new Point(0,0)); // TBC this new Point is redundant.
    }

    // Give some collectables to the baddies
    bdd[30].setCollectable(0);

    return 50;
  }

  // Set up attack waves (numBaddies, baddieFrameDelay, sources)
  public int setupAttackWaves(AttackWave[] aww)
  {
    aww[0] = new AttackWave(35, 8, new Point[] {new Point(156*2,-16*2), new Point(78*2,256*2)});
    aww[1] = new AttackWave(15, 4, new Point[] {new Point(-16*2,45*2), new Point(256*2,129*2), new Point(156*2,-16*2), new Point(78*2,272*2)});

    return 2;
  }
  //middle right : Point(256*2,129*2)
  //top left : Point(-16*2,45*2)
  //bottom left : Point(-16*2,178*2)
  //top middle : Point(156*2,-16*2)
  //bottom middle: Point(78*2,256*2)

  // Set up collectables
  public int setupCollectables(Collectable[] cll)       // TBD why do I need public on these?
  {
    cll[0] = new Collectable(131, new Point(0,0), 400, 4 ); // TBC this new Point is redundant.

    return 1;
  }
}


