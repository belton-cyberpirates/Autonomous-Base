package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name="Field Centric (main)", group="DriveCodes")
public class DriveCode extends LinearOpMode {
    
    // Hardware Helper Classes
    DriveMotors driveMotors;
    Intake intake;
    Launcher launcher;

    // Other Hardware Objects
    Servo light;
    
    // Vars
    double headingOffset = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        
        driveMotors = new DriveMotors(this);
        intake = new Intake(this);
        launcher = new Launcher(this);
        
        light = hardwareMap.get(Servo.class, BotConfig.LIGHT_NAME);

        // Wait for the start button to be pressed
        waitForStart();

        while (opModeIsActive()) {
            // Reset yaw when y button pressed so restarting is not needed if it needs a reset
            if (gamepad1.y) {
                headingOffset = driveMotors.heading;
            }
            
            // Process classes
            driveMotors.process();
            launcher.process();
            
            // P1 variables
            double leftStickXGP1 = gamepad1.left_stick_x;
            double leftStickYGP1 = gamepad1.left_stick_y;
            double rightStickXGP1 = gamepad1.right_stick_x;
            double rightStickYGP1 = gamepad1.right_stick_y;

            // Get the speed the bot should go with the joystick pushed all the way
            double maxSpeed = calcMaxSpeed(gamepad1.right_trigger - gamepad1.left_trigger, BotConfig.BASE_SPEED, BotConfig.MAX_BOOST);
            
            double turnPower = -gamepad1.right_stick_x;

            // Virtually rotate the joystick by the angle of the robot
            double heading = driveMotors.heading - headingOffset;
            
            double rotatedX =
                leftStickXGP1 * Math.cos(heading) -
                leftStickYGP1 * Math.sin(heading);
            double rotatedY =
                leftStickXGP1 * Math.sin(heading) +
                leftStickYGP1 * Math.cos(heading);
            
            // strafing is slower than rolling, bump horizontal speed
            rotatedX *= BotConfig.STRAFE_MULT;
            
            // Set the power of the wheels based off the new joystick coordinates
            // y+x+stick <- [-1,1]
            driveMotors.DriveWithPower(
                ( rotatedY + rotatedX + ( turnPower )) * maxSpeed, // Back left
                ( rotatedY - rotatedX + ( turnPower )) * maxSpeed, // Front left
                (-rotatedY - rotatedX + ( turnPower )) * maxSpeed, // Front right
                (-rotatedY + rotatedX + ( turnPower )) * maxSpeed  // Back right
            );
            
            // P2 variables
            double leftStickYGP2 = gamepad2.left_stick_y;
            double rightStickYGP2 = gamepad2.right_stick_y;
            
            // Intake
            intake.SetPower(leftStickYGP2 < 0 ? -leftStickYGP2 : -leftStickYGP2 / 3);
            
            // Pusher
            intake.SetPusherPower(-leftStickYGP2 / 1.5);
            
            // Stopper
            intake.SetStopper(gamepad2.right_trigger > 0.5);
            
            // Flywheel
            if (rightStickYGP2 > .5) {
                launcher.SetVelocity(BotConfig.LAUNCHER_DROP_VELOCITY);
            } 
            else if (rightStickYGP2 < -.5) {
                launcher.SetVelocity(BotConfig.LAUNCHER_VELOCITY);
            }
            else {
                launcher.SetVelocity(BotConfig.LAUNCHER_PASSIVE_VELOCITY);
            }
            
            // Light
            light.setPosition(
                launcher.isAtVelocity() ? 
                    BotConfig.LIGHT_GREEN
                :
                    BotConfig.LIGHT_RED
            );
            
            // Telemetry
            telemetry.addData("headingOffset", headingOffset);

            telemetry.update();
        }
    }


    /**
     * if boost trigger unpressed, return base_speed,
     * else return base_speed + boost amount
     */
    double calcMaxSpeed(double triggerVal, double baseSpeed, double boostMult) {
        double boostRatio = triggerVal * boostMult;
        double boostSpeed = boostRatio * baseSpeed;

        return baseSpeed + boostSpeed;
    }
}
