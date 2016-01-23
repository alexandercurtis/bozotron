package Robotron;
import java.awt.*;

/******************************************************************************
 * The UserInput class                                                        *
 * Groups together relevant keyboard and mouse events.                        *
 ******************************************************************************/
class UserInput
{
  boolean leftPressed;
  boolean rightPressed;
  boolean upPressed;
  boolean downPressed;

  boolean mouseDown;
  Point mousePoint;

  UserInput()
  {
    leftPressed = false;
    rightPressed = false;
    upPressed = false;
    downPressed = false;

    mouseDown = false;
    mousePoint = new Point(0,0);
  }
}


