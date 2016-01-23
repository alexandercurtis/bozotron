package Robotron;

/******************************************************************************
 * The Level interface.                                                       *
 * Template for the code to create a level.                                   *
 ******************************************************************************/
interface Level
{
  // Set up goodies
  int setupGoodies(Goodie[] gdd);


  int setupBaddies(Baddie[] bdd);


  // Set up attack waves (numBaddies, baddieFrameDelay, sources)
  int setupAttackWaves(AttackWave[] aww);

  // Set up collectables
  int setupCollectables(Collectable[] cll);

}


