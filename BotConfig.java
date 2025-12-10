package org.firstinspires.ftc.teamcode;


public class BotConfig {
  /*****************************************************************************
  ** HARDWARE CONSTANTS
  *****************************************************************************/
  public static final String FRONT_RIGHT_WHEEL_NAME = "front_right";
  public static final String FRONT_LEFT_WHEEL_NAME = "front_left";
  public static final String BACK_LEFT_WHEEL_NAME = "back_left";
  public static final String BACK_RIGHT_WHEEL_NAME = "back_right";
  
  public static final String INTAKE_NAME = "intake";
  public static final String PUSHER_NAME = "pusher";
  public static final String STOPPER_NAME = "stopper";

  public static final String LAUNCHER_LEFT_NAME = "flywheel";
  public static final String LAUNCHER_RIGHT_NAME = "flywheel";

  public static final String TURRET_NAME = "turret";

  public static final String PINPOINT_NAME = "odo";

  public static final String LIGHT_NAME = "light";
  // ---------------------------------------------------------------------------


  /*****************************************************************************
  ** DISTANCE CALIBRATION CONSTANTS
  *****************************************************************************/
  public static final int TILE_LENGTH = 875; //TODO find actual distance
  // ---------------------------------------------------------------------------


  /*****************************************************************************
  ** TELEOP CALIBRATION CONSTANTS
  *****************************************************************************/
  public static final double BASE_SPEED = .6;
  
  public static final double MAX_BOOST = 0.66; // boost maxes out at an additional 66% of the base speed
  public static final double STRAFE_MULT = 1.41;

  public static final int LAUNCHER_DROP_VELOCITY = 300;
  public static final int LAUNCHER_PASSIVE_VELOCITY = 1000;
  public static final int LAUNCHER_VELOCITY = 1500;
  public static final int LAUNCHER_FAR_VELOCITY = 1790;
  
  public static final double LIGHT_GREEN = 0.472;
  public static final double LIGHT_RED = 0.277;
  // ---------------------------------------------------------------------------


  /*****************************************************************************
  ** AUTO CALIBRATION CONSTANTS
  *****************************************************************************/
  public static final int AUTO_DRIVE_VELOCITY = 1000; // encoders only
  public static final int AUTO_PUSHER_VELOCITY = 700;
  public static final int AUTO_INTAKE_VELOCITY = 2500;
  public static final int AUTO_LAUNCHER_VELOCITY = 1425;
  // ---------------------------------------------------------------------------


  /*****************************************************************************
  ** LAUNCHER CONSTANTS
  *****************************************************************************/
  public static final int LAUNCHER_VELOCITY_MARGIN = 40;
  public static final int TURRET_MAX_OFFSET = 300;
  
  public static final double TURRET_OFFSET_FAR_RED = -0.5;
  public static final double TURRET_OFFSET_FAR_BLUE = 4.5;
  public static final double TURRET_OFFSET_CLOSE_BLUE = -0.5; // test to find
  public static final double TURRET_OFFSET_CLOSE_RED = 6; // test to find

  public static final double STOPPER_OPEN_POS = 0.075;
  public static final double STOPPER_CLOSE_POS = 0.25;
  // ---------------------------------------------------------------------------


  /*****************************************************************************
  ** CAMERA CONSTANTS
  *****************************************************************************/
  public static final String CAMERA_NAME = "Webcam 1";
  // ---------------------------------------------------------------------------


// ---------------------------------------------------------------------------
}