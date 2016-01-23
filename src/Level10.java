package Robotron;
import java.awt.*;

/******************************************************************************
 * The Level10 class                                                          *
 * Hardcodes the data for level one.                                          *
 * ENSURE :                                                                   *
 * setupGoodies returns the total number of goodies.                          *
 * setupBaddies returns the total number of baddies, from adding up the       *
 * baddies created in setupAttackWaves.                                       *
 * setupAttackWaves returns the total number of attack waves.                 *
 * baddies deposit valid collectable indexes                                  *
 ******************************************************************************/
class Level10 implements Level
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
    for(int i=0; i<40; i++)
    {
      if( i<5 )
        bdd[i] = new Ghost(81, new Point(0,0)); // TBC this new Point is redundant.
      else if( i<10 )
        bdd[i] = new Flame(96, new Point(0,0)); // TBC this new Point is redundant.
      if( i<15 )
        bdd[i] = new Ghost(81, new Point(0,0)); // TBC this new Point is redundant.
      else if( i<20 )
        bdd[i] = new Flame(96, new Point(0,0)); // TBC this new Point is redundant.
      else
        bdd[i] = new Zombie(64, new Point(0,0)); // TBC this new Point is redundant.
    }

    // Give some collectables to the baddies
    bdd[6].setCollectable(0);
    bdd[7].setCollectable(1);
    bdd[9].setCollectable(2);
    bdd[28].setCollectable(3);
    bdd[30].setCollectable(4);
    bdd[32].setCollectable(5);
    bdd[38].setCollectable(6);

    return 40;
  }

  // Set up attack waves (numBaddies, baddieFrameDelay, sources)
  public int setupAttackWaves(AttackWave[] aww)
  {
    aww[0] = new AttackWave(5, 16, new Point[] {new Point(-16*2,45*2)});
    aww[1] = new AttackWave(5, 16, new Point[] {new Point(-16*2,178*2), new Point(256*2,129*2)});
    aww[2] = new AttackWave(10, 8, new Point[] {new Point(156*2,-16*2), new Point(78*2,256*2)});
    aww[3] = new AttackWave(20, 4, new Point[] {new Point(-16*2,45*2), new Point(256*2,129*2), new Point(156*2,-16*2), new Point(78*2,272*2)});

    return 4;
  }
  //middle right : Point(256*2,129*2)
  //top left : Point(-16*2,45*2)
  //bottom left : Point(-16*2,178*2)
  //top middle : Point(156*2,-16*2)
  //bottom middle: Point(78*2,256*2)

  // Set up collectables
  public int setupCollectables(Collectable[] cll)       // TBD why do I need public on these?
  {
    cll[0] = new Collectable(137, new Point(0,0), 500, 0 ); // TBC this new Point is redundant.
    cll[1] = new Collectable(137, new Point(0,0), 500, 0 ); // TBC this new Point is redundant.
    cll[2] = new Collectable(137, new Point(0,0), 500, 0 ); // TBC this new Point is redundant.
    cll[3] = new Collectable(138, new Point(0,0), 600, 0 ); // TBC this new Point is redundant.
    cll[4] = new Collectable(138, new Point(0,0), 600, 0 ); // TBC this new Point is redundant.
    cll[5] = new Collectable(138, new Point(0,0), 600, 0 ); // TBC this new Point is redundant.
    cll[6] = new Collectable(128, new Point(0,0), 0, 1 ); // TBC this new Point is redundant.

    return 7;
  }
}


