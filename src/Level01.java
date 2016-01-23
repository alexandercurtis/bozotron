package Robotron;
import java.awt.*;

/******************************************************************************
 * The Level01 class                                                          *
 * Hardcodes the data for level one.                                          *
 ******************************************************************************/
class Level01 implements Level
{
  // Set up goodies
  public int setupGoodies(Goodie[] gdd)       // TBD why do I need public on these?
  {
    gdd[0] = new Goodie(192, new Point(80*2,100*2), Goodie.eD_LEFT, 50*2,200*2);
    gdd[1] = new Goodie(192, new Point(140*2,150*2), Goodie.eD_RIGHT, 50*2,200*2);

    return 2;
  }

  public int setupBaddies(Baddie[] bdd)
  {
    for(int i=0; i<20; i++)
    {
      bdd[i] = new Prowler(87, new Point(0,0)); // TBC this new Point is redundant.
    }

    return 20;
  }

  // Set up attack waves (numBaddies, baddieFrameDelay, sources)
  public int setupAttackWaves(AttackWave[] aww)
  {
    aww[0] = new AttackWave(20, 6, new Point[] {new Point(-16*2,45*2), new Point(256*2,129*2), new Point(156*2,-16*2), new Point(78*2,272*2)});

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
    cll[0] = new Collectable(128, new Point(0,0), 100, 1); // TBC this new Point is redundant.
    cll[1] = new Collectable(129, new Point(0,0), 200, 2); // TBC this new Point is redundant.
    cll[2] = new Collectable(130, new Point(0,0), 100, 3); // TBC this new Point is redundant.
    cll[3] = new Collectable(131, new Point(0,0), 200, 4); // TBC this new Point is redundant.
    cll[4] = new Collectable(132, new Point(0,0), 100, 5); // TBC this new Point is redundant.
    cll[5] = new Collectable(133, new Point(0,0), 200, 6); // TBC this new Point is redundant.

    return 5;
  }

}


