package Robotron;
import java.awt.*;

/******************************************************************************
 * The Level04 class                                                          *
 * Hardcodes the data for level one.                                          *
 * ENSURE :                                                                   *
 * setupGoodies returns the total number of goodies.                          *
 * setupBaddies returns the total number of baddies, from adding up the       *
 * baddies created in setupAttackWaves.                                       *
 * setupAttackWaves returns the total number of attack waves.                 *
 * baddies deposit valid collectable indexes                                  *
 ******************************************************************************/
class Level04 implements Level
{
  // Set up goodies
  public int setupGoodies(Goodie[] gdd)       // TBD why do I need public on these?
  {
    gdd[0] = new Goodie(192, new Point(20,20), Goodie.eD_RIGHT, 20,60);
    gdd[1] = new Goodie(192, new Point(20,40), Goodie.eD_DOWN, 20,60);
    gdd[2] = new Goodie(192, new Point(472,432), Goodie.eD_UP, 422,452);
    gdd[3] = new Goodie(192, new Point(472,452), Goodie.eD_LEFT, 432,472);

    return 4;
  }

  public int setupBaddies(Baddie[] bdd)
  {
    for(int i=0; i<40; i++)
    {
      bdd[i] = new Zombie(69, new Point(0,0)); // TBC this new Point is redundant.
    }

    // Give some collectables to the baddies
    bdd[4].setCollectable(1);
    bdd[5].setCollectable(4);
    bdd[10].setCollectable(2);
    bdd[11].setCollectable(3);
    bdd[30].setCollectable(0);

    return 40;
  }

  // Set up attack waves (numBaddies, baddieFrameDelay, sources)
  public int setupAttackWaves(AttackWave[] aww)
  {

    aww[0] = new AttackWave(40, 8, new Point[] {new Point(156*2,-16*2), new Point(78*2,256*2)});
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
    cll[0] = new Collectable(128, new Point(0,0), 0, 1 ); // TBC this new Point is redundant.
    cll[1] = new Collectable(134, new Point(0,0), 200, 0 ); // TBC this new Point is redundant.
    cll[2] = new Collectable(134, new Point(0,0), 200, 0 ); // TBC this new Point is redundant.
    cll[3] = new Collectable(134, new Point(0,0), 200, 0 ); // TBC this new Point is redundant.
    cll[4] = new Collectable(134, new Point(0,0), 200, 0 ); // TBC this new Point is redundant.

    return 5;
  }
}


