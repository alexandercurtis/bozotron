package Robotron;
import java.awt.*;

/******************************************************************************
 * The Level03 class                                                          *
 * Hardcodes the data for level one.                                          *
 ******************************************************************************/
class Level03 implements Level
{
  // Set up goodies
  public int setupGoodies(Goodie[] gdd)       // TBD why do I need public on these?
  {
    gdd[0] = new Goodie(192, new Point(20,20), Goodie.eD_RIGHT, 20,462);
    gdd[1] = new Goodie(192, new Point(462,40), Goodie.eD_LEFT, 20,462);
    gdd[2] = new Goodie(192, new Point(20,432), Goodie.eD_RIGHT, 20,462);
    gdd[3] = new Goodie(192, new Point(452,452), Goodie.eD_LEFT, 20,462);

    return 4;
  }

  public int setupBaddies(Baddie[] bdd)
  {
    for(int i=0; i<40; i++)
    {
      if(i<10)
        bdd[i] = new Zombie(64, new Point(0,0)); // TBC this new Point is redundant.
      else if(i<10+10)
        bdd[i] = new Zombie(64, new Point(0,0)); // TBC this new Point is redundant.
      else if(i<10+10+10)
        bdd[i] = new Prowler(87, new Point(0,0)); // TBC this new Point is redundant.
      else
        bdd[i] = new Prowler(87, new Point(0,0)); // TBC this new Point is redundant.
    }

    // Give some collectables to the baddies
    bdd[24].setCollectable(1);
    bdd[35].setCollectable(0);
    bdd[16].setCollectable(2);

    return 40;
  }

  // Set up attack waves (numBaddies, baddieFrameDelay, sources)
  public int setupAttackWaves(AttackWave[] aww)
  {
    aww[0] = new AttackWave(10, 8, new Point[] {new Point(256*2,129*2)});
    aww[1] = new AttackWave(10, 8, new Point[] {new Point(-16*2,178*2)});
    aww[2] = new AttackWave(10, 4, new Point[] {new Point(78*2,256*2), new Point(156*2,-16*2)});
    aww[3] = new AttackWave(10, 2, new Point[] {new Point(-16*2,45*2), new Point(256*2,129*2), new Point(156*2,-16*2), new Point(78*2,272*2)});

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
    cll[0] = new Collectable(133, new Point(0,0), 100, 0 ); // TBC this new Point is redundant.
    cll[1] = new Collectable(133, new Point(0,0), 100, 0 ); // TBC this new Point is redundant.
    cll[2] = new Collectable(133, new Point(0,0), 100, 0 ); // TBC this new Point is redundant.

    return 3;
  }
}


