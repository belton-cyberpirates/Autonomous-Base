package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Direction;
import org.firstinspires.ftc.teamcode.BotConfig;
import org.firstinspires.ftc.teamcode.Auto;


@Autonomous(name = "Red Far"/*, preselectTeleOp="Your Drive Code Here"*/, group="red")
//@Disabled
public class AutoRedFar extends Auto {

    public Action[] getActions() {
        
        Action[] actions = {
            // ======================= AUTO START ======================= //
            
            // Get ready for launching
            new OpenStopper(this),
            new SpinLauncherFast(this),
            
            // Move to shooting position
            new Move(this, 180, 0, -90+18),
            
            // Launch!
            new WaitForLauncher(this),
            new Wait(this, 500),
            new SpinPusher(this),
            new Wait(this, 500),
            new SpinIntake(this, -.3),
            new Wait(this, 200),
            new SpinIntake(this),
            
            new Wait(this, 3000),
            
            // Reset
            new StopLauncher(this),
            new StopIntake(this),
            new StopPusher(this),
            
            // Move to first line
            new CloseStopper(this),
            new Move(this, 680, -300, 90),
            
            // Intake artifacts
            new SpinIntake(this),
            new SpinPusher(this), 
            new Move(this, 680, -1100, 90),
            new Wait(this, 750),
            new StopPusher(this),
            new StopIntake(this),
            
            // Get ready for launching
            new OpenStopper(this),
            new SpinLauncherFast(this),
            
            // Move back to shooting position, while intaking to not lose artifacts
            new Move(this, 180, 0, -90+18), // same as first shooting position
            
            // Launch!
            new WaitForLauncher(this),
            new Wait(this, 500),
            new SpinPusher(this),
            new Wait(this, 500),
            new SpinIntake(this, -.3),
            new Wait(this, 200),
            new SpinIntake(this),
            new Wait(this, 3500),
            
            // Reset
            new StopLauncher(this),
            new StopIntake(this),
            new StopPusher(this),
            
            // Move to second line
            new CloseStopper(this),
            new Move(this, 1200, -300, 90),
            
            // Intake artifacts
            new SpinIntake(this),
            new SpinPusher(this), 
            new Move(this, 1190, -1100, 90),
            new Wait(this, 750),
            new StopPusher(this),
            new StopIntake(this),
            
            // Get ready for launching
            new OpenStopper(this),
            new SpinLauncherFast(this),
            
            // Move back to shooting position, while intaking to not lose artifacts
            new Move(this, 180, 0, -90+18), // same as first shooting position
            
            // Launch!
            new WaitForLauncher(this),
            new Wait(this, 500),
            new SpinPusher(this),
            new Wait(this, 500),
            new SpinIntake(this, -.3),
            new Wait(this, 200),
            new SpinIntake(this),
            
            new Wait(this, 3500),
            
            // End sequence
            
            // Move out of triangle
            new CloseStopper(this),
            new Move(this, 50, -500, 0),
            
            // ======================== AUTO END ======================== //
        };
        
        return actions;
    }
}