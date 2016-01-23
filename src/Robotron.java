// BOZOTRON
// This file, and all other source files and assets for this game are copyright (c) 2001 Alex Curtis, All Rights Reserved

// To Do:
// ------
//
// DONE: Animate the sprites, possibly amalgamating all the graphics into one VRAM style image.
// Use BufferedImage instead of Image.  See if it's faster.
// DONE: Add Player getting zapped, and re-spawning
// DONE: Add text display (score, game-over message etc.)
// DONE: Add lives, score, game-over etc. states.
// Sound
// Different baddie types.
// Collectibles
// DONE: Rescueables
// DONE: Proper class hierarcy to eliminate duplicated code.
// DONE: Random aimless dawdling of baddies.
// DONE: All members to start with m_
// DONE: All methods to start with lower case
// DONE: All braces on their own lines
// Handle exceptions properly
// Use enums
// Make Sprite abstract?
// Classes to start with C?
// Load wave from file?
// DONE: Add formal Applet methods such at getWhatParamsAre, getInfo and toString.
// Add javadoc stuff
// Add more levels
// Add text special effects
// BROKEN: DONE: Implement Applet.stop to suspend the game [stop() not called]
// DONE: Implement Applet.destroy to kill the animation thread
// Make use of Applet parameters from the HTML
// Use a faster sqrt?
// BROKEN: Replace hard coded 512s with Applet.getWidth() and getHeight(). [getWidth() hangs]
// DONE: Make everything bigger.
// Improve redraw time by not blitting entire background every frame. Most of it is black. Could also use dirty rects.
// Bundle everything into a jar file.
// Use buffered image to speed up spread type explosions.
// Horizontal as well as vertical spread explosions.
// Tidy up main loop in run() (there are 2 atm , repeated code)
// Make title overlay (welcome.gif) smaller
// Add dialog to switch options on/off: background gif, large/small applet, delay time etc.
// DONE: Add attract mode.

package Robotron;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Robotron extends Applet implements Runnable
{
  public static String VERSION_NUMBER = new String("0.1");
  public static String COPYRIGHT = new String("Copyright 2001-2003 Alex Curtis. All Rights Reserved.");

  CCGame m_game;  // The actual game class. Does all the work.

  Image m_backgroundImage;    // The image of the arena.
  Image m_welcomeImage; // The first screen the player sees.

  int m_width;  // The width of the applet. Should be a multiple of 512 to look proper.
  int m_height; // The height of the applet. Should be same as width to look proper.

  Thread m_gameThread;        // The thread which runs the game, independently of the browser.
  boolean m_paused; // True indicates the game is paused and not to update anything.
  boolean m_shutDown; // True if the game thread should exit.
  int m_slowDown; // Number of milliseconds to wait between frames

  Graphics m_backBufferGraphics;  // The grapics context for the back buffer.
  Image m_backBufferImage;  // The actual back buffer.

  UserInput m_input;  // Stores state of movement keys and mouse.

  int m_state;  // Game state : 0=welcome, 1=playing game

  TextScroll m_scrollText;  // The scrolling welcome message.

  //public DebugDisplay m_debug; // Used to show debug messages

/******************************************************************************
 * init() method.                                                             *
 * Called when applet first loaded into browser.                              *
 * Do initialisation here in preference to in constructor.                    *
 ******************************************************************************/
  public void init()
  {

    //m_width = getWidth();
    //m_height = getHeight();

    int csum = 0;
    byte[] bsum = COPYRIGHT.getBytes();
    int i;
    for( i=0; i<53; ++i ) {
      csum += (int)bsum[i];
    }
    while( csum != 4440 ) {
      try
      {
        Thread.sleep(1000);
      }
      catch(InterruptedException e) {/* Do nothing. */};
    }



    m_backBufferImage = createImage(512,512);
    m_backBufferGraphics = m_backBufferImage.getGraphics();

    // Load the graphics images.
    MediaTracker t = new MediaTracker(this);

    m_backgroundImage = getImage(getCodeBase(), "roboworld.gif");
    t.addImage(m_backgroundImage, 0);

    m_welcomeImage = getImage(getCodeBase(), "welcome.gif");
    t.addImage(m_welcomeImage, 0);

    Image vramImg = getImage(getCodeBase(), "vram.gif");
    t.addImage(vramImg,0);

    try
    {
      t.waitForAll();
    } catch (InterruptedException e) {/* Do nothing. */}

    if(t.isErrorAny())
    {
      showStatus("Error Loading Images!");
      while(true); // Infinite loop
    }

    if(t.checkAll())
    {
      showStatus("Images successfully loaded!");
    }

    // Tell sprites where to find their image data
    Sprite.setUpImage(vramImg, this);

    // Create the UserInput holder
    m_input = new UserInput();

    // Create the game object
    m_game = new CCGame(this);
    m_paused = false;
    m_shutDown = false;

    String slowDown = getParameter( "SlowDown" );
    if( slowDown != null ) {
      m_slowDown = Integer.valueOf( slowDown ).intValue();
    } else {
      m_slowDown = 20;
    }

    // Create the scrolling welcome text
    m_scrollText = new TextScroll();
    m_scrollText.set(". . . . . . . . . . . . . . . . . . . . WELCOME TO BOZOTRON, A JAVA APPLET BY ALEX CURTIS COPYRIGHT 2001-2003 ALL RIGHTS RESERVED. START THE GAME BY CLICKING THE MOUSE IN THIS SCREEN. ARROW KEYS MOVE THE PLAYER AROUND THE SCREEN, AND THE MOUSE AIMS AND FIRES. BLAST YOUR WAY THROUGH ALL TEN TRAUMATIC LEVELS OF BADDIES TO RESCUE THE STRANDED EARTHLINGS. ENJOY THE GAME.", 0, 380);

    // Register that we want to handle mouse events and keyboard events.
    enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
    enableEvents(AWTEvent.KEY_EVENT_MASK);

    m_state = 0;  // Begin with welcome screen

    //m_debug = new DebugDisplay(); // Start debug display
  }

/******************************************************************************
 * start() method.                                                            *
 * Called when applet gets focus, becomes visible.                            *
 ******************************************************************************/
  public void start()
  {
    // Create a Thread object, tell it that this owns the run() method which it is to call.
    m_gameThread = new Thread(this);
    if(m_gameThread != null)
    {
      // Tell the Thread object to start.
      m_gameThread.start();
    }

    // Unpause the game
    // Don't need to synchronise, cos it's atomic (I hope)
    m_paused = false;
  }

/******************************************************************************
 * stop() method.                                                             *
 * Called when applet loses focus, becomes invisible.                         *
 ******************************************************************************/
  public void stop()
  {
    // Pause the game
    // Don't need to synchronise, cos it's atomic (I hope)
    m_paused = true;
  }

/******************************************************************************
 * destroy() method.                                                          *
 * Called when applet is shut down. Must destroy the animation thread here.   *
 ******************************************************************************/
  public void destroy()
  {
    // Tell the run() thread to exit.
    // Don't need to synchronise, cos it's atomic (I hope)
    m_shutDown = true;
  }

/******************************************************************************
 * update() method.                                                           *
 * Need to override update because the Applet one clears the screen first.    *
 ******************************************************************************/
  public void update(Graphics g)
  {
    paint(g);
  }

/******************************************************************************
 * run() method.                                                              *
 * Called by gameThread, effectively once. Kind of a "go" method.             *
 ******************************************************************************/
  public void run()
  {
    Rectangle dirtyRect;
    dirtyRect = new Rectangle(0,0,512,512);

    long tFrameStart;
    long tFrameEnd;
    int tRemaining;

    // Run loop /////////////////////////////////////////
    while(m_shutDown==false)
    {

      // Start off in attract mode
      m_game.attractMode(true);

      // Set level for attract mode
      m_game.setLevel(0);

      // Game loop ////////////////////////////////////////
      boolean gameOver = false;
      while(gameOver==false && m_shutDown==false)
      {
        // Start Level
        m_game.initialiseLevel(); // Loads level baddies etc.
        m_game.reset();   // Clears timers, sets baddies to start etc.

        // Play loop ////////////////////////////////////////
        boolean playingGame = true;
        while(playingGame && m_shutDown==false)
        {
          tFrameStart = System.currentTimeMillis();

          if(m_paused == true)
          {
            // Do nothing in play loop if game is not visible
            continue;
          }

          // Draw background image, erasing earlier frame graphics.
          m_backBufferGraphics.drawImage(m_backgroundImage, 0, 0, this);

          //// Output debug text messages
          //m_debug.draw(m_backBufferGraphics);
          //m_debug.clear();

          // Draw game objects
          m_game.redraw(m_backBufferGraphics);

          // Draw attract mode stuff
          if(m_game.attractMode() == true)
          {
            // The title overlay
            m_backBufferGraphics.drawImage(m_welcomeImage, 0, 0, this);
            // The scroll text
            m_scrollText.draw(m_backBufferGraphics);
            m_scrollText.update();
          }

          // Call component.repaint, which will eventually call this.paint().
          repaint(dirtyRect.x, dirtyRect.y, dirtyRect.width, dirtyRect.height);

          // Move everything on by one frame
          playingGame = m_game.updateGame(m_input);

          // Sleep for a bit to get down to a playable speed.
          tFrameEnd = System.currentTimeMillis();
          if( tFrameEnd - tFrameStart < m_slowDown ) {
            tRemaining = m_slowDown - (int)(tFrameEnd - tFrameStart);
          } else {
            tRemaining = 1;
          }
          try
          {
            // Note that sleep() is static to Thread class, and sleeps the current thread.
            Thread.sleep( tRemaining );
          }
          catch(InterruptedException e) {/* Do nothing. */};
        }
        // End of play loop

        // Player pressed start?
        if(m_game.attractMode() == true)
        {
          if(m_game.beginGame() == true)
          {
            m_game.attractMode(false);
            m_game.setLevel(0);       // Begin at level 0
          }
          // Attract mode finished?
          else
          {
            gameOver = true;  // Restart attract mode.
          }
        }
        // Level finished?
        else if(m_game.goodiesSaved())
        {
          m_game.advanceLevel();
          m_game.initialiseLevel();  // Load next level
        }
        // All lives lost?
        else if(m_game.livesLost())
        {
          gameOver = true;  // Back to attract mode
          m_game.restart();
        }
        // Must mean just one life lost.
        else
        {
          /*nothing*/
        }
      }
      // End of game loop

      /* TBD High score name entry*/

      /* TBD High score display*/

    }
    // End of run loop

    // Shut down game here.
  }

/******************************************************************************
 * paint() method.                                                            *
 * Called when browser wants the applet to draw itself on the screen.         *
 ******************************************************************************/
  public void paint(Graphics g)
  {
    g.drawImage(m_backBufferImage, 0, 0, this);
  }

/******************************************************************************
 * processKeyEvent(KeyEvent e) method.                                        *
 * The keyboard event handler.                                                *
 ******************************************************************************/
  public void processKeyEvent(KeyEvent e)
  {
    boolean pressed;
    if(e.getID() == KeyEvent.KEY_PRESSED)
    {
      pressed = true;
    }
    else if(e.getID() == KeyEvent.KEY_RELEASED)
      pressed = false;
    else
      return; // Not an event we care about.

    switch(e.getKeyCode())
    {
    case KeyEvent.VK_LEFT:
      m_input.leftPressed = pressed;
      break;
    case KeyEvent.VK_RIGHT:
      m_input.rightPressed = pressed;
      break;
    case KeyEvent.VK_UP:
      m_input.upPressed = pressed;
      break;
    case KeyEvent.VK_DOWN:
      m_input.downPressed = pressed;
      break;
    }
  }


/******************************************************************************
 * processMouseMotionEvent(MouseEvent e) method.                              *
 * The mouse motion event handler override.                                   *
 ******************************************************************************/
  public void processMouseMotionEvent(MouseEvent e)
  {
    m_input.mousePoint = e.getPoint();
    // Won't bother to propagate the event.
  }

/******************************************************************************
 * processMouseEvent(MouseEvent e) method.                                    *
 * The mouse down event handler override.                                     *
 ******************************************************************************/
  public void processMouseEvent(MouseEvent e)
  {
    if(e.getID() == e.MOUSE_PRESSED)
    {
      // Mouse clicks begin a game
      m_game.beginGame(true);

      m_input.mouseDown = true;
      m_game.resetBulletDelay();
    }
    else if(e.getID() == e.MOUSE_RELEASED)
      m_input.mouseDown = false;
//  else
//    m_input.mouseDown = e.getModifiers() & e.BUTTON1_MASK;

    m_input.mousePoint = e.getPoint();

    // Won't bother to propagate the event.
  }

/******************************************************************************
 * getAppletInfo() method.                                                    *
 * Overrides Applet method.                                                   *
 ******************************************************************************/
 public String getAppletInfo()
 {
  return ("Bozotron Java Applet v." + VERSION_NUMBER + "\n"
    + "(c) 2001-2003 Alex Curtis\n");
 }

/******************************************************************************
 * getParameterInfo() method.                                                 *
 * Overrides Applet method. TBD actually make use of parameters.              *
 ******************************************************************************/
 public String[][] getParameterInfo()
 {
  return new String[][] {
    {"size", "int", "Size of applet side in pixels"},
    {"delay", "int", "Number of milliseconds to wait between frame updates"} };
 }

}

