package Robotron;
import java.awt.*;

/******************************************************************************
 * The AttackWave class                                                       *
 * Contains details of an aliens' attack formation.                           *
 * A formation is a period where aliens are being created from one source.    *
 ******************************************************************************/
class AttackWave
{
  static final int MAX_FORMATIONS = 4; // Four attack formations per level.
  static final int MAX_SOURCES = 4; // Each formation can have baddies coming from at most MAX_SOURCES directions.
  int m_numBaddies; // Number of baddies to create on this wave.
  int m_numSources;   // How many sources for the wave.
  int m_baddieFrameDelay; // How many frames to leave between creating baddies.
  Point[] m_waveSource; // The place(s) where the baddies are created.

  int m_numBaddiesCreated;  // Keeps count of how many baddies have come out of the current (set of) sources.
  int m_baddieFrameTimer; // To create a delay between the emission of each baddie.
  int m_sourceNumber; // Which source is currently emitting baddies.

  AttackWave(int numBaddies, int baddieFrameDelay, Point[] sources)
  {
    m_waveSource = new Point[MAX_SOURCES];

    // Ensure that the total baddies doesn't exceed MAX_BADDIES (TBD use assert?)
    m_numBaddies = numBaddies;
    m_baddieFrameDelay = baddieFrameDelay;
    m_numSources = sources.length;
    m_waveSource = sources;
  }


  void reset()
  {
    m_numBaddiesCreated = 0;
    m_baddieFrameTimer = 0;
    m_sourceNumber = 0;
  }

  boolean waveEnded()
  {
    return(m_numBaddiesCreated >= m_numBaddies);
  }

  void start()
  {
    reset();
  }

  boolean emitBaddie(Baddie bdd)
  {
    // Ensure enough of a delay occurred since creating the last baddie.
    m_baddieFrameTimer++;
    if(m_baddieFrameTimer >= m_baddieFrameDelay)
    {
      bdd.start(m_waveSource[m_sourceNumber]);
      m_numBaddiesCreated++;
      m_baddieFrameTimer = 0;

      // Cycle through to next source
      m_sourceNumber++;
      if(m_sourceNumber==m_numSources)
      {
        m_sourceNumber = 0;
      }
      return true;
    }
    else
    {
      return false;
    }
  }

}

