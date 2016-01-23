package Robotron;
import java.awt.*;

/******************************************************************************
 * The Level05 class                                                          *
 * Hardcodes the data for level one.                                          *
 * ENSURE :                                                                   *
 * setupGoodies returns the total number of goodies.                          *
 * setupBaddies returns the total number of baddies, from adding up the       *
 * baddies created in setupAttackWaves.                                       *
 * setupAttackWaves returns the total number of attack waves.                 *
 * baddies deposit valid collectable indexes                                  *
 ******************************************************************************/
class Level06 implements Level
{
  // Set up goodies
  public int setupGoodies(Goodie[] gdd)       // TBD why do I need public on these?
  {
    gdd[0] = new Goodie(192, new Point(40*2,40*2), Goodie.eD_LEFT, 20*2,60*2);
    gdd[1] = new Goodie(192, new Point(200*2,40*2), Goodie.eD_UP, 20*2,60*2);
    gdd[2] = new Goodie(192, new Point(40*2,200*2), Goodie.eD_RIGHT, 20*2,60*2);
    gdd[3] = new Goodie(192, new Point(200*2,200*2), Goodie.eD_DOWN, 180*2,220*2);

    return 4;
  }

  public int setupBaddies(Baddie[] bdd)
  {
    for(int i=0; i<30; i++)
    {
      bdd[i] = new Grunt(160, new Point(0,0)); // TBC this new Point is redundant.
    }

    // Give some collectables to the baddies
    bdd[4].setCollectable(0);
    bdd[7].setCollectable(1);
    bdd[20].setCollectable(2);
    bdd[12].setCollectable(3);

    return 30;
  }

  // Set up attack waves (numBaddies, baddieFrameDelay, sources)
  public int setupAttackWaves(AttackWave[] aww)
  {
    aww[0] = new AttackWave(30, 4, new Point[] {new Point(-16*2,45*2), new Point(256*2,129*2), new Point(156*2,-16*2), new Point(78*2,272*2)});

    return 1;
  }
  //middle right : Point(256*2,129*2)
  //top left : Point(-16*2,45*2)
  //bottom left : Point(-16*2,178*2)
  //top middle : Point(156*2,-16*2)
  //bottom middle: Point(78*2,256*2)

  // Set up collectables
  public int setupCollectables(Collectable[] cll)       // TBD why do I need public on these?
  {
    cll[0] = new Collectable(136, new Point(0,0), 400, 0 ); // TBC this new Point is redundant.
    cll[1] = new Collectable(136, new Point(0,0), 400, 0 ); // TBC this new Point is redundant.
    cll[2] = new Collectable(128, new Point(0,0), 0, 1 ); // TBC this new Point is redundant.
    cll[3] = new Collectable(131, new Point(0,0), 0, 4 ); // TBC this new Point is redundant.

    return 4;
  }
}


