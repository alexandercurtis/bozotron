package Robotron;
import java.awt.*;

/******************************************************************************
 * The Level09 class                                                          *
 * Hardcodes the data for level one.                                          *
 * ENSURE :                                                                   *
 * setupGoodies returns the total number of goodies.                          *
 * setupBaddies returns the total number of baddies, from adding up the       *
 * baddies created in setupAttackWaves.                                       *
 * setupAttackWaves returns the total number of attack waves.                 *
 * baddies deposit valid collectable indexes                                  *
 ******************************************************************************/
class Level09 implements Level
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
    for(int i=0; i<25; i++)
    {
      if( i<5 )
        bdd[i] = new Flame(96, new Point(0,0)); // TBC this new Point is redundant.
      else if( i<15 )
        bdd[i] = new Zombie(74, new Point(0,0)); // TBC this new Point is redundant.
      else
        bdd[i] = new Zombie(64, new Point(0,0)); // TBC this new Point is redundant.
    }

    // Give some collectables to the baddies
    bdd[6].setCollectable(3);
    bdd[11].setCollectable(0);
    bdd[19].setCollectable(2);
    bdd[15].setCollectable(1);
    bdd[22].setCollectable(4);

    return 25;
  }

  // Set up attack waves (numBaddies, baddieFrameDelay, sources)
  public int setupAttackWaves(AttackWave[] aww)
  {
    aww[0] = new AttackWave(5, 16, new Point[] {new Point(-16*2,45*2)});
    aww[1] = new AttackWave(5, 8, new Point[] {new Point(-16*2,178*2), new Point(256*2,129*2)});
    aww[2] = new AttackWave(5, 8, new Point[] {new Point(156*2,-16*2), new Point(78*2,256*2)});
    aww[3] = new AttackWave(10, 16, new Point[] {new Point(-16*2,45*2), new Point(256*2,129*2), new Point(156*2,-16*2), new Point(78*2,272*2)});

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
    cll[0] = new Collectable(137, new Point(0,0), 600, 0 ); // TBC this new Point is redundant.
    cll[1] = new Collectable(135, new Point(0,0), 300, 0 ); // TBC this new Point is redundant.
    cll[2] = new Collectable(135, new Point(0,0), 300, 0 ); // TBC this new Point is redundant.
    cll[3] = new Collectable(132, new Point(0,0), 0, 5 ); // TBC this new Point is redundant.
    cll[4] = new Collectable(128, new Point(0,0), 0, 1 ); // TBC this new Point is redundant.

    return 5;
  }
}


