package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.BotConfig;
import java.util.List;

public class Intake {

    private LinearOpMode auto;

    public DcMotorEx intake;
    public DcMotorEx pusher;
    public Servo stopper;

    boolean hasPusher;
    boolean hasStopper;


    public Intake(LinearOpMode auto) {
        this.auto = auto;
        
        this.intake = auto.hardwareMap.get(DcMotorEx.class, BotConfig.INTAKE_NAME);

        hasPusher = (BotConfig.PUSHER_NAME != "");
        if (hasPusher) {
            this.pusher = auto.hardwareMap.get(DcMotorEx.class, BotConfig.PUSHER_NAME);
        }

        hasStopper = (BotConfig.STOPPER_NAME != "");
        if (hasStopper) {
            this.stopper = auto.hardwareMap.get(Servo.class, BotConfig.STOPPER_NAME);
        }
    }


    public void SetPower(double power) {
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setPower(power);
    }


    public void SetPusherPower(double power) {
        if (!hasPusher) {
            throw new Exception("Must have a pusher to set pusher power!");
            return;
        }

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pusher.setPower(power);
    }
  
  
    public void SetIntakeVelocity(int velocity) {
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setVelocity(velocity);
    }


    public void SpinIntake() {
        this.SetIntakeVelocity(BotConfig.AUTO_INTAKE_VELOCITY);
    }


    public void SpinIntake(double mult) {
        this.SetIntakeVelocity((int)(BotConfig.AUTO_INTAKE_VELOCITY * mult));
    }
  
  
    public void SetPusherVelocity(int velocity) {
        if (!hasPusher) {
            throw new Exception("Must have a pusher to set pusher velocity!");
            return;
        }

        pusher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pusher.setVelocity(velocity);
    }


    public void SpinPusher(double mult) {
        if (!hasPusher) {
            throw new Exception("Must have a pusher to set pusher power!");
            return;
        }

        this.SetPusherVelocity((int)(BotConfig.AUTO_PUSHER_VELOCITY * mult));
    }


    public void SpinPusher() {
        this.SpinPusher(1);
    }


    public void OpenStopper() {
        if (!hasStopper) {
            throw new Exception("Must have a stopper to open stopper!");
            return;
        }

        stopper.setPosition(BotConfig.STOPPER_OPEN_POS);
    }


    public void CloseStopper() {
        if (!hasStopper) {
            throw new Exception("Must have a stopper to set close stopper!");
            return;
        }
        stopper.setPosition(BotConfig.STOPPER_CLOSE_POS);
    }


    public void SetStopper(boolean open) {
        stopper.setPosition(open ? BotConfig.STOPPER_OPEN_POS : BotConfig.STOPPER_CLOSE_POS);
    }


    public boolean isStopperOpen() {
        return stopper.getPosition() == BotConfig.STOPPER_OPEN_POS;
    }
    
    public int getIntakeVelocity() {
        return (int)intake.getVelocity();
    }
}