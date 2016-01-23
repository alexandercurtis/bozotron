package Robotron;
import java.awt.*;

/******************************************************************************
 * The CCGame class                                                           *
 ******************************************************************************/
class CCGame
{
  Robotron m_app; // The owning applet.

  Bullet[] m_bll;
  Baddie[] m_bdd;
  Goodie[] m_gdd;
  Collectable[] m_cll;
  Player m_plr;

  int m_numGoodies; // How many Goodies actually required by current level.
  int m_numBaddies; // How many Baddies actually required by current level.
  int m_numAttackWaves; // How many AttackWaves actually required by current level.
  int m_numCollectables;  // How many collectables are available on current level.

  AttackWave[] m_attackWaves;    // Details of the attack formations.
  Level m_level;  // Used to create the level.
  int m_levelNumber;  // Number of the current level. 0 based.

  TextDisplay m_textDisplay;  // The object which draws text messages to the screen.
  int m_scoreTextID;    // The index of the text string which displays the score.
  int m_score;  // The player's score.
  int m_livesTextID;    // The index of the text string which displays the lives.
  int m_lives; // The player's number of lives.
  int m_levelTextID;  // The index of the text string which displays the level introduction.

  int m_waveNumber; // The index of the current wave.
  int m_baddieFrameTimer; // How many frames since a baddie was last created in the wave.
  int m_numBaddiesCreated;  // Number of baddies created so far this wave.
  int m_nextFreeBaddie; // Absolute index of the next baddie to be created.
  int m_sourceNumber; // Which source we are currently creating baddies from.

  // Game state variables
  boolean m_playerAlive;  // Whether player is able to move about and be killed.
  boolean m_goodiesSaved; // Whether all the goodies have been rescued.
  boolean m_allBaddiesDead; // Whether all the baddies created so far have been obliterated.
  boolean m_attackFinished; // Whether the attack waves are all done.
  int m_framesTilExit; // Count of number of frames left until game exits.  (-1 means infinity)
  int m_framesTilStart; // Count of the number of frames left before game can start.
  boolean m_attractMode;  // If true then the game plays itself to attract players.
  boolean m_beginGame;  // Semaphore used to signal the start of a proper game.

  // Power up variables
  boolean m_playerInvisible;
  boolean m_bouncingBullets;
  boolean m_frozenBaddies;
  int m_effectFrame;

  int m_nextBullet;  // Index of next available bullet in bullet array.
  int m_bulletDelay;    // Counter variable to space bullets out a bit.
  static final int BULLET_DELAY = 2; // Reset-value of m_bulletDelay counter. Alters spacing of bullets.
  static final int MAX_BULLETS = 100;
  static final int MAX_BADDIES = 100;
  static final int MAX_GOODIES = 4;
  static final int MAX_COLLECTABLES = 8;
  static final int MAX_WAVES = 4; // Max number of attack waves ever available.
  static final int LIFE_LOST_DELAY = 50;  // Number of frames after player is killed before level restarts.
  static final int GAME_OVER_DELAY = 120;  // Number of frames after player lost all lives before game exits.
  static final int GOODIES_SAVED_DELAY = 20; // Number of frames after all goodies rescued before game exits.
  static final int BADDIE_SCORE = 50; // Score for killing a baddie.
  static final int GOODIE_SCORE = 500;  // Score for collecting a goodie.
  static final int MAX_LIVES = 4; // Number of lives to start the game.
  static final int POWERUP_FRAMES = 500; // Number of frames power up in effect for

/******************************************************************************
 * CCGame() constructor.                                                      *
 ******************************************************************************/
  CCGame(Robotron app)    // TBD why can't I put (Applet app)?
  {
    m_app = app;
    restart();
  }


/******************************************************************************
 * attractMode(boolean active) accessor.                                      *
 * Sets attract mode on or off according to the argument passed.              *
 ******************************************************************************/
  void attractMode(boolean active)
  {
    m_attractMode = active;
  }

/******************************************************************************
 * attractMode() accessor.                                                    *
 * Returns current value of attract mode setting.                             *
 ******************************************************************************/
  boolean attractMode()
  {
    return m_attractMode;
  }

/******************************************************************************
 * initialiseLevel(int levelNumber) method.                                   *
 * Starts a specified level of the game.                                      *
 ******************************************************************************/
  void initialiseLevel(int levelNumber)
  {
    m_levelNumber = levelNumber;
    initialiseLevel();
  }

/******************************************************************************
 * initialiseLevel() method.                                                  *
 * Starts current level of the game.                                          *
 ******************************************************************************/
  void initialiseLevel()
  {
    // Create bullets
    m_bll = new Bullet[MAX_BULLETS];
    for(int i=0; i<MAX_BULLETS; i++)
    {
      m_bll[i] = new Bullet();
    }

    // Create goodies array
    m_gdd = new Goodie[MAX_GOODIES];

    // Create collectables array
    m_cll = new Collectable[MAX_COLLECTABLES];

    // Create baddies array
    m_bdd = new Baddie[MAX_BADDIES];

    // Create attack waves array
    m_attackWaves = new AttackWave[MAX_WAVES];

    // Create player
    m_plr = new Player(204, 236, new Point(512/2-16,512/2-16), 8, 0, 472, 462 ); // TBD use applet size or something to determine domain of player

    // Load the level
    switch(m_levelNumber)
    {
    case 0:
      m_level = new Level01();
      break;
    case 1:
      m_level = new Level02();
      break;
    case 2:
      m_level = new Level03();
      break;
    case 3:
      m_level = new Level04();
      break;
    case 4:
      m_level = new Level05();
      break;
    case 5:
      m_level = new Level06();
      break;
    case 6:
      m_level = new Level07();
      break;
    case 7:
      m_level = new Level08();
      break;
    case 8:
      m_level = new Level09();
      break;
    case 9:
      m_level = new Level10();
      break;
    }

    // Create the display object to show scores, lives etc.
    m_textDisplay = new TextDisplay();

    // Set up level characters
    m_numGoodies = m_level.setupGoodies(m_gdd);
    m_numBaddies = m_level.setupBaddies(m_bdd);
    m_numAttackWaves = m_level.setupAttackWaves(m_attackWaves);
    m_numCollectables = m_level.setupCollectables(m_cll);

  }

/******************************************************************************
 * redraw() method.                                                           *
 * This does the actual drawing                                               *
 ******************************************************************************/
  public void redraw(Graphics backBufferGraphics)
  {
    int i;  // general loop counter

    if(m_attractMode==false)
    {
      for(i=0; i<MAX_BULLETS; i++)
      {
        m_bll[i].draw(backBufferGraphics);
      }

      for(i=0; i<m_numCollectables; i++)
      {
        m_cll[i].draw(backBufferGraphics);
      }
    }

    for(i=0; i<m_numBaddies; i++)
    {
      m_bdd[i].draw(backBufferGraphics);
    }

    if(m_attractMode==false)
    {
      for(i=0; i<m_numGoodies; i++)
      {
        m_gdd[i].draw(backBufferGraphics);
      }

      m_plr.draw(backBufferGraphics);

      m_textDisplay.draw(backBufferGraphics);
    }
  }

/******************************************************************************
 * updateBaddies() method.                                                    *
 * Returns true if no baddies exist.                                          *
 ******************************************************************************/
  void updateBaddies()
  {
    m_allBaddiesDead = true;

    for(int i=0; i<m_numBaddies; i++)
    {
      if(m_bdd[i].alive()==true)
      {
        m_allBaddiesDead = false;

        if(m_attractMode==false && m_playerAlive==true && m_plr.collide(m_bdd[i])==true)
        {
          m_playerAlive = false;
          // Decrement lives
          m_lives--;
          if(m_lives>=0)
          {
            m_framesTilExit = LIFE_LOST_DELAY;
            //m_textDisplay.print("LIFE LOST!", (512-12*10)/2, 112);
          }
          else
          {
            m_framesTilExit = GAME_OVER_DELAY;
            m_textDisplay.print("GAME OVER", (512-9*10)/2, 112);
          }

        }
        m_bdd[i].setTarget(this);
        m_bdd[i].update( m_frozenBaddies );
      }
    }
  }

/******************************************************************************
 * updateBullets() method.                                                    *
 ******************************************************************************/
  void updateBullets()
  {
    for(int i=0; i<MAX_BULLETS; i++)
    {
      if(m_bll[i].alive()==true)
      {
        m_bll[i].update();
        for(int j=0; j<m_numBaddies; j++)
        {
          if(m_bdd[j].collide(m_bll[i]))
          {
            m_bll[i].kill();
            // Increment score
            updateScoreAndDisplay(m_score + BADDIE_SCORE);

            // Maybe create a collectable
            int collectable = m_bdd[j].getCollectable();
            if(collectable >= 0)
            {
              double vx = 2.0*Math.random()-1.0;
              double vy = 2.0*Math.random()-1.0;
              Point p = m_bdd[j].getBoundaryRect().getLocation();
              m_cll[collectable].start(p, vx, vy);
            }

            break; // to next bullet.
          }
        }
      }
    }
  }

/******************************************************************************
 * updateGoodies() method.                                                    *
 * Returns true if no goodies exist.                                          *
 ******************************************************************************/
  void updateGoodies()
  {
    m_goodiesSaved = true;

    for(int i=0; i<m_numGoodies; i++)
    {
      if(m_gdd[i].alive())
      {
        m_goodiesSaved = false;

        if(m_gdd[i].collide(m_plr)==true)
        {
          // Increment score
          updateScoreAndDisplay(m_score + GOODIE_SCORE);
        }
        m_gdd[i].update();
      }
    }
  }

/******************************************************************************
 * updateCollectables() method.                                               *
 ******************************************************************************/
  void updateCollectables()
  {
    for(int i=0; i<m_numCollectables; i++)
    {
      if(m_cll[i].alive())
      {
        if(m_cll[i].collide(m_plr)==true)
        {
          // Act on collectable
          startEffect( m_cll[i].effect(), POWERUP_FRAMES );
          updateScoreAndDisplay( m_score + m_cll[i].score() );
          //m_app.m_debug.print("pick up!\n");
        }
        m_cll[i].update();
      }
    }
  }

/******************************************************************************
 * updateAttack() method.                                                     *
 * Returns true if all waves finished.                                        *
 ******************************************************************************/
  void updateAttack()
  {
    m_attackFinished = false;

    // Create some baddies
    // Are there still baddies left in this wave?
    if(m_attackWaves[m_waveNumber].waveEnded()==true)
    {
      // No. Move on to next wave.
      m_waveNumber++;
      if(m_waveNumber<m_numAttackWaves)
      {
        m_attackWaves[m_waveNumber].start();
      }
      else
      {
        m_attackFinished = true;
      }
    }
    else
    {
      // Yes, try and start another baddie.
      boolean emitted = m_attackWaves[m_waveNumber].emitBaddie(m_bdd[m_nextFreeBaddie]);
      if(emitted==true)
      {
        m_nextFreeBaddie++;
      }
    }
  }

/******************************************************************************
 * updatePlayer() method.                                                     *
 ******************************************************************************/
  void updatePlayer(boolean left, boolean right, boolean up, boolean down, Point mousePoint )
  {
    // Move the player
    m_plr.update(left, right, up, down, mousePoint);
  }

/******************************************************************************
 * createBullet() method.                                                     *
 ******************************************************************************/
  boolean createBullet(Point target, boolean bouncing)
  {
    int firstBullet = m_nextBullet;
    boolean foundOne = false;
    do
    {
      if(m_bll[m_nextBullet].alive()==false)
      {
        m_bll[m_nextBullet].start(m_plr.getBoundaryRect(), target, bouncing);
        foundOne = true;
      }
      m_nextBullet++;
      if(m_nextBullet == MAX_BULLETS)
      {
        m_nextBullet = 0;
      }
    }
    while((m_nextBullet != firstBullet) && (!foundOne));

    return foundOne;
  }

/******************************************************************************
 * updateGame() method.                                                       *
 * This does one frame of the main game loop.                                 *
 ******************************************************************************/
  boolean updateGame(UserInput input)
  {
    //m_app.m_debug.print("m_attractMode = " + m_attractMode);
    //m_app.m_debug.print("m_beginGame = " + m_beginGame);

    // Wait several frames before game starts
    if(m_framesTilStart>0)
    {
      m_framesTilStart--;
      // Hide the level text just as game starts
      if(m_framesTilStart==0)
      {
        m_textDisplay.nullify(m_levelTextID);
      }
      return true;
    }

    // Are we about to exit attract mode?
    if(m_attractMode==true)
    {
      if(m_beginGame==true)
      {
        return false;
      }
    }
    else
    // Not in attract mode
    {
      updateCollectables();

      updateGoodies();
      if(m_goodiesSaved==true && m_framesTilExit<0)
      {
        m_framesTilExit = GOODIES_SAVED_DELAY;
        m_textDisplay.print("LEVEL " + (m_levelNumber+1) + " COMPLETE", (512-12*16)/2, 112);
      }

    }

    // Only move the game on if the level isn't finished.
    if(m_goodiesSaved==false)
    {
      updateBullets();

      updateBaddies();

      // Only create if there is still an attack wave to come.
      if(m_waveNumber<m_numAttackWaves && !m_frozenBaddies)
      {
        updateAttack();
      }

      if(m_attractMode==false)
      {
        updatePlayer(input.leftPressed, input.rightPressed, input.upPressed, input.downPressed, input.mousePoint);

        if(m_plr.canShoot()==true)
        {
          if(m_bulletDelay == 0)
          {
            if(input.mouseDown)
            {
              if(createBullet(input.mousePoint, m_bouncingBullets))
                m_bulletDelay = BULLET_DELAY;
            }
          }
          else
          {
            m_bulletDelay--;
          }
        }
      }
    }

    if(m_framesTilExit > 0)
    {
      m_framesTilExit--;
    }

    if( m_effectFrame > 0 ) {
      if( --m_effectFrame == 0 ) {
        stopEffects();
      }
    }

    //m_app.m_debug.print("m_waveNumber = " + m_waveNumber );

    return(m_framesTilExit != 0);
  }

/******************************************************************************
 * resetBulletDelay() method.                                                 *
 * Used to allow a bullet to fire immediately. Called on mouse-down event.    *
 ******************************************************************************/
  void resetBulletDelay()
  {
    m_bulletDelay = 0;
  }


/******************************************************************************
 * reset() method.                                                            *
 * Prepares for a new level.                                                  *
 ******************************************************************************/
  void reset()
  {
    for(int i=0; i<m_numBaddies; i++)
    {
      m_bdd[i].reset();
    }
    for(int i=0; i<m_numGoodies; i++)
    {
      m_gdd[i].reset();
    }
    for(int i=0; i<MAX_BULLETS; i++)
    {
      m_bll[i].reset();
    }
    for(int i=0; i<m_numAttackWaves; i++)
    {
      m_attackWaves[i].reset();
    }
    for(int i=0; i<m_numCollectables; i++)
    {
      m_cll[i].reset();
    }

    m_plr.reset();

    m_framesTilExit = -1; // So that game isn't about to exit.

    m_textDisplay.clear();
    m_textDisplay.print("SCORE", 512-11*11-32, 8);
    m_scoreTextID = m_textDisplay.allocate();
    updateScoreAndDisplay(m_score);

    m_livesTextID = m_textDisplay.allocate();
    updateLivesAndDisplay(m_lives);

    // Introduce level number and give player a delay before start to read it.
    m_levelTextID = m_textDisplay.print("LEVEL " + (m_levelNumber+1), (512-12*7)/2, 112);
    m_framesTilStart = 40;

    // Set up game state ready for the first attack wave
    m_waveNumber = 0;
    m_nextFreeBaddie = 0;
    m_nextBullet = 0;
    m_bulletDelay = 0;

    stopEffects();

    m_playerAlive = true;
    m_goodiesSaved = false;
    m_allBaddiesDead = false;
    m_attackFinished = false;
    m_waveNumber = 0;
  }


/******************************************************************************
 * start() method.                                                      *
 * Sets a power-up effect in action for a limited number of frames.           *
 ******************************************************************************/
  void startEffect(int effect, int nFrames)
  {
    if( effect != 0 ) // Fruit doesn't stop effects
      stopEffects(); // Stop any effect which is already happening

    m_effectFrame = nFrames;
    switch( effect ) {
      case 0: // No effect
        break;
      case 1: // Bouncing bullets
        m_bouncingBullets = true;
        break;
      case 2: // Attract goodies
        m_frozenBaddies = true;
        break;
      case 3: // Extra life
        updateLivesAndDisplay( ++m_lives );
        break;
      case 4: // Smart bomb
        for(int j=0; j<m_numBaddies; j++)
        {
          if( m_bdd[j].smartBomb(m_plr.getBoundaryRect()) ) {
            // Increment score
            m_score += BADDIE_SCORE; // The calling function should re-display score.

            // Maybe create a collectable
            int collectable = m_bdd[j].getCollectable();
            if(collectable >= 0)
            {
              double vx = 2.0*Math.random()-1.0;
              double vy = 2.0*Math.random()-1.0;
              Point p = m_bdd[j].getBoundaryRect().getLocation();
              m_cll[collectable].start(p, vx, vy);
            }
          }
        }
        break;
      case 5: // Invisible player
        m_playerInvisible = true;
        break;
    }
  }

/******************************************************************************
 * stopEffects() method.                                                      *
 * Stops all currently happening power-up effects                             *
 ******************************************************************************/
  void stopEffects()
  {
    m_effectFrame = 0;
    m_bouncingBullets = false;
    m_frozenBaddies = false;
    m_playerInvisible = false;
  }

/******************************************************************************
 * updateScoreAndDisplay() method.                                            *
 * Sets the player's score and displays it on the screen.                     *
 ******************************************************************************/
  void updateScoreAndDisplay(int score)
  {
    m_score = score;
    m_textDisplay.set(m_scoreTextID, m_score, 5, 512-5*11-32, 8);
  }

/******************************************************************************
 * updateLivesAndDisplay() method.                                            *
 * Sets the player's lives and displays them on the screen.                   *
 ******************************************************************************/
  void updateLivesAndDisplay(int lives)
  {
    m_lives = lives;
    m_textDisplay.set(m_livesTextID, "@@@@@@@", 16, 8);

    // m_lives of -1 is a valid value, it means game over, but the lives display obviously can't manage it.
    if(lives<0)
    {
      lives = 0;
    }

    m_textDisplay.reduceLength(m_livesTextID, lives);
  }

/******************************************************************************
 * playerAlive() accessor.                                                    *
 ******************************************************************************/
  boolean playerAlive()
  {
    return m_playerAlive;  // Whether player is able to move about and be killed.
  }

/******************************************************************************
 * goodiesSaved() accessor.                                                   *
 ******************************************************************************/
  boolean goodiesSaved()
  {
    return m_goodiesSaved; // Whether all the goodies have been rescued.
  }

/******************************************************************************
 * baddiesDead() accessor.                                                    *
 ******************************************************************************/
  boolean baddiesDead()
  {
    return m_allBaddiesDead; // Whether all the baddies created so far have been obliterated.
  }

/******************************************************************************
 * attackFinished() accessor.                                                 *
 ******************************************************************************/
  boolean attackFinished()
  {
    return m_attackFinished; // Whether the attack waves are all done.
  }

/******************************************************************************
 * livesLost() accessor.                                                      *
 ******************************************************************************/
  boolean livesLost()
  {
    return (m_lives==-1); // Whether the player is totally dead.
  }

/******************************************************************************
 * advanceLevel() method.                                                     *
 ******************************************************************************/
  void advanceLevel()
  {
    m_levelNumber++;
    if( m_levelNumber == 10 )
      m_levelNumber = 0;
  }

/******************************************************************************
 * setLevel(int level) method.                                                *
 ******************************************************************************/
  void setLevel(int level)
  {
    m_levelNumber = level;
  }

/******************************************************************************
 * restart() method.                                                          *
 ******************************************************************************/
  void restart()
  {
    m_score = 0;
    m_lives = MAX_LIVES;
    m_levelNumber = 0;
    m_beginGame = false;
  }

/******************************************************************************
 * beginGame(boolean status) method.                                          *
 ******************************************************************************/
  void beginGame(boolean status)
  {
    if(m_attractMode==true)
    {
      m_beginGame = status;
    }
  }

/******************************************************************************
 * boolean beginGame() method.                                                *
 ******************************************************************************/
  boolean beginGame()
  {
    return m_beginGame;
  }
}

