package Robotron;
import java.awt.*;

/******************************************************************************
 * The Level02 class                                                          *
 * Hardcodes the data for level one.                                          *
 ******************************************************************************/
class Level02 implements Level
{
  // Set up goodies
  public int setupGoodies(Goodie[] gdd)       // TBD why do I need public on these?
  {
    gdd[0] = new Goodie(192, new Point(80*2,100*2), Goodie.eD_LEFT, 50*2,200*2);
    gdd[1] = new Goodie(192, new Point(100*2,125*2), Goodie.eD_UP, 50*2,200*2);
    gdd[2] = new Goodie(192, new Point(140*2,150*2), Goodie.eD_RIGHT, 50*2,200*2);
    gdd[3] = new Goodie(192, new Point(150*2,100*2), Goodie.eD_DOWN, 50*2,200*2);

    return 4;
  }

  public int setupBaddies(Baddie[] bdd)
  {
    for(int i=0; i<20; i++)
    {
      if( i < 15 )
        bdd[i] = new Prowler(87, new Point(0,0)); // TBC this new Point is redundant.
      else
        bdd[i] = new Zombie(64, new Point(0,0)); // TBC this new Point is redundant.
    }

    // Give some collectables to the baddies
    bdd[19].setCollectable(0);

    return 20;
  }

  // Set up attack waves (numBaddies, baddieFrameDelay, sources)
  public int setupAttackWaves(AttackWave[] aww)
  {
    aww[0] = new AttackWave(5, 5, new Point[] {new Point(78*2,256*2)});
    aww[1] = new AttackWave(10, 3, new Point[] {new Point(156*2,-16*2)});
    aww[2] = new AttackWave(5, 2, new Point[] {new Point(78*2,256*2), new Point(156*2,-16*2)});

    return 3;
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

    return 1;
  }
}


