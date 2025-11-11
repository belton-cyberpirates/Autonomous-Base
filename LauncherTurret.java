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

public class Launcher {

	private LinearOpMode auto;

	public DcMotorEx launcherLeft;
	public DcMotorEx launcherRight;
	public DcMotorEx turret;

	int launcherTargetVelocity = 0;


	public Launcher(LinearOpMode auto) {
		this.auto = auto;
		
		this.launcherLeft = auto.hardwareMap.get(DcMotorEx.class, BotConfig.LAUNCHER_LEFT_NAME);
		this.launcherRight = auto.hardwareMap.get(DcMotorEx.class, BotConfig.LAUNCHER_RIGHT_NAME);

		this.turret = auto.hardwareMap.get(DcMotorEx.class, BotConfig.TURRET_NAME);
	}
  
  
	public void SetVelocity(int velocity) {
		launcherTargetVelocity = velocity;
		launcherLeft.setVelocity(-velocity);
		launcherRight.setVelocity(velocity);
	}


	public void Spin() {
		this.SetVelocity(BotConfig.LAUNCHER_VELOCITY);
	}


	public void SetTurretPower(double power) {
		turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		turret.setPower(power);
	}


	public void SetTurretVelocity(int velocity) {
		turret.setVelocity(velocity);
	}


	public void SetTurretPosition(int position) {
		turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		turret.setTargetPosition(position);
	}


	public boolean isAtVelocity() {
		return launcher.getVelocity() > this.launcherTargetVelocity - BotConfig.LAUNCHER_VELOCITY_MARGIN &&
			launcher.getVelocity() < this.launcherTargetVelocity + BotConfig.LAUNCHER_VELOCITY_MARGIN;
	}
	
	
	public int getVelocity() {
		return launcher.getVelocity();
	}
}