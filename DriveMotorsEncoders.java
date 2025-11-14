package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import java.util.Collections;
import java.util.Arrays;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import com.qualcomm.robotcore.hardware.AnalogInput;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import org.firstinspires.ftc.teamcode.Direction;
import org.firstinspires.ftc.teamcode.BotConfig;
import org.firstinspires.ftc.teamcode.PIDController;


public class DriveMotors {

    enum states {
        ENCODERS,
        DISTANCE,
        POWER,
        IDLE
    }

    public static PIDController distanceSensorPidController = new PIDController(0, 0, 0);
    public static PIDController forwardPidController = new PIDController(0.00255, 0.00000033, 0.0000025);
    public static PIDController strafePidController = new PIDController(0.00265, 0.0000004, 0.000004);
    public static PIDController imuPidController = new PIDController(1.2, 0, 0.001);

    static Orientation angles;

    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;
    public DcMotorEx backLeft;
    public DcMotorEx backRight;

    //public GoBildaPinpointDriver odometry;

    private LinearOpMode auto;

    ElapsedTime deltaTimer = new ElapsedTime();

    public states state = states.IDLE;

    public double targetX;
    public double targetY;
    public double targetHeading;

    public int targetDistance;
    
    ElapsedTime odometryTimer = new ElapsedTime();
    
    public double heading = 0;


    public DriveMotors(LinearOpMode auto) {
        this.auto = auto;

        this.frontRight = auto.hardwareMap.get(DcMotorEx.class, BotConfig.FRONT_RIGHT_WHEEL_NAME);
        this.frontLeft = auto.hardwareMap.get(DcMotorEx.class, BotConfig.FRONT_LEFT_WHEEL_NAME);
        this.backLeft = auto.hardwareMap.get(DcMotorEx.class, BotConfig.BACK_LEFT_WHEEL_NAME);
        this.backRight = auto.hardwareMap.get(DcMotorEx.class, BotConfig.BACK_RIGHT_WHEEL_NAME);
        
        //this.odometry = auto.hardwareMap.get(GoBildaPinpointDriver.class, "odo");
        
        this.backLeft.setTargetPosition(0);
        this.frontLeft.setTargetPosition(0);
        this.frontRight.setTargetPosition(0);
        this.backRight.setTargetPosition(0);
    
        ResetEncoders();
        SetZeroBehaviour();
    }


    public double process() {
        double deltaTime = deltaTimer.seconds();

        switch (this.state) {
            case POWER:
                SetToRunWithPower();
                break;
                
            case DISTANCE:
                SetToRunWithPower();
                driveWithDistanceSensor(deltaTime);
                break;
        }
        
        deltaTimer.reset();
        //odometry.update();
        
        //heading = odometry.getHeading(AngleUnit.RADIANS);
        
        return deltaTime;
    }
    
    
    // public void InitializeOdometry() {
    //     /*
    //     Set the odometry pod positions relative to the point that the odometry computer tracks around.
    //     The X pod offset refers to how far sideways from the tracking point the
    //     X (forward) odometry pod is. Left of the center is a positive number,
    //     right of center is a negative number. the Y pod offset refers to how far forwards from
    //     the tracking point the Y (strafe) odometry pod is. forward of center is a positive number,
    //     backwards is a negative number.
    //      */
    //     this.odometry.setOffsets(-100, -25, DistanceUnit.MM); 

    //     /*
    //     Set the kind of pods used by your robot. If you're using goBILDA odometry pods, select either
    //     the goBILDA_SWINGARM_POD, or the goBILDA_4_BAR_POD.
    //     If you're using another kind of odometry pod, uncomment setEncoderResolution and input the
    //     number of ticks per mm of your odometry pod.
    //      */
    //     this.odometry.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
    //     //odo.setEncoderResolution(13.26291192);


    //     /*
    //     Set the direction that each of the two odometry pods count. The X (forward) pod should
    //     increase when you move the robot forward. And the Y (strafe) pod should increase when
    //     you move the robot to the left.
    //      */
    //     this.odometry.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.FORWARD);


    //     /*
    //     Before running the robot, recalibrate the IMU. This needs to happen when the robot is stationary
    //     The IMU will automatically calibrate when first powered on, but recalibrating before running
    //     the robot is a good idea to ensure that the calibration is "good".
    //     resetPosAndIMU will reset the position to 0,0,0 and also recalibrate the IMU.
    //     This is recommended before you run your autonomous, as a bad initial calibration can cause
    //     an incorrect starting value for x, y, and heading.
    //      */
    //     //odo.recalibrateIMU();
    //     this.odometry.resetPosAndIMU();
    //     this.odometry.setPosition( new Pose2D(DistanceUnit.MM, 0, 0, AngleUnit.RADIANS, 0) );
    // }


    public void DriveWithPower(double backLeftPower, double frontLeftPower, double frontRightPower, double backRightPower) {
        this.state = states.POWER;

        backLeft.setPower(backLeftPower);
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }


    public void MoveWithEncoders(int backLeftPos, int frontLeftPos, int frontRightPos, int backRightPos) {
        this.state = states.ENCODERS;
        SetToRunToPosition();
        
        backLeft.setTargetPosition(backLeft.getTargetPosition() + backLeftPos);
        frontLeft.setTargetPosition(frontLeft.getTargetPosition() + frontLeftPos);
        frontRight.setTargetPosition(frontRight.getTargetPosition() + frontRightPos);
        backRight.setTargetPosition(backRight.getTargetPosition() + backRightPos);
    }


    public void MoveWithEncoders(Direction dir, int distance) {
        
        switch (dir) {
            case FORWARD:
                MoveWithEncoders(-distance, -distance, distance, distance);
                break;
            case LEFT:
                MoveWithEncoders(-distance, distance, distance, -distance);
                break;
            case RIGHT:
                MoveWithEncoders(distance, -distance, -distance, distance);
                break;
             case BACKWARD:
                MoveWithEncoders(distance, distance, -distance, -distance);
                break;
            case FRONT_LEFT:
                MoveWithEncoders(-distance, 0, distance, 0);
                break;
            case FRONT_RIGHT:
                MoveWithEncoders(0, -distance, 0, distance);
                break;
            case BACK_LEFT:
                MoveWithEncoders(0, distance, 0, -distance);
                break;
            case BACK_RIGHT:
                MoveWithEncoders(distance, 0, -distance, 0);
                break;
        }
    }


    public void driveWithDistanceSensor(double delta) {
        double dist = distSensor.getDistance(DistanceUnit.MM);
        double error = targetDistance - dist;
        
        double power = distanceSensorPidController.PIDControl(error, delta);

        frontLeft.setPower(-power);
        frontRight.setPower(power);
        backLeft.setPower(-power);
        backRight.setPower(power);
        
        auto.telemetry.addData("dist", dist);
        auto.telemetry.addData("error", error);
        auto.telemetry.addData("power", power);
    }


    public void MoveToDistance(int distance) {
        this.targetDistance = distance;

        this.state = states.DISTANCE;
    }


    public void Stop() {
        this.state = states.IDLE;
    }


    public boolean isDone() {
        switch (this.state) {
            case ENCODERS:
                return (!backLeft.isBusy()) && 
                    (!frontLeft.isBusy()) && 
                    (!frontRight.isBusy()) && 
                    (!backRight.isBusy()); 
            
            case DISTANCE:
                return (Math.abs(distanceSensorPidController.lastError) < 5);
        }
        
        return true;
    }


    private void SetToRunWithPower() {
        this.frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    private void SetToRunToPosition() {
        this.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        this.frontLeft.setVelocity(BotConfig.AUTO_DRIVE_VELOCITY);
        this.frontRight.setVelocity(BotConfig.AUTO_DRIVE_VELOCITY);
        this.backLeft.setVelocity(BotConfig.AUTO_DRIVE_VELOCITY);
        this.backRight.setVelocity(BotConfig.AUTO_DRIVE_VELOCITY);
    }


    private void SetToRunWithVelocity() {
        this.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
  
  
    private void SetZeroBehaviour() {
        this.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    
    
    private void ResetEncoders() {
        this.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
