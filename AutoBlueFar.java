package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Direction;
import org.firstinspires.ftc.teamcode.BotConfig;
import org.firstinspires.ftc.teamcode.Auto;


@Autonomous(name = "Blue Far"/*, preselectTeleOp="Your Drive Code Here"*/, group="blue")
//@Disabled
public class AutoBlueFar extends Auto {

    public Action[] getActions() {
        
        Action[] launchSequence = {
            // Get ready for launching
            new OpenStopper(this),
            new SpinLauncherFast(this),
            
            // Move to shooting position
            new Move(this, 180, 0, -90-24.5),
            
            // Launch!
            new WaitForLauncher(this),
            new Wait(this, 500),
            new SpinPusher(this),
            new Wait(this, 1000),
            new SpinIntake(this),
            new SpinPusher(this, 2),
            
            new Wait(this, 2000),
            
            // Reset
            new StopLauncher(this),
            new StopIntake(this),
            new StopPusher(this)
        };
        
        Action[] actions = {
            // ======================= AUTO START ======================= //
            
            // Launch!
            new ActionSequence(this, launchSequence),
            
            // Move to first line
            new Move(this, 680, 300, -90),

            // Intake artifacts
            new CloseStopper(this),
            new SpinIntake(this),
            new SpinPusher(this), 
            new Move(this, 680, 1100, -90),
            new Wait(this, 750),
            new StopPusher(this),
            new StopIntake(this),
            
            new ActionSequence(this, launchSequence),

            // If we have more time 

            // Move to second line
            new Move(this, 1200, 300, -90),

            // Intake artifacts
            new CloseStopper(this),
            new SpinIntake(this),
            new SpinPusher(this), 
            new Move(this, 1190, 1100, -90),
            new Wait(this, 750),
            new StopPusher(this),
            new StopIntake(this),
            
            new ActionSequence(this, launchSequence),

            // I don't think we have any chance of shooting these artifacts, if we even have time to grab them
            
            // End sequence

            // Move out of triangle
            new Move(this, 50, 500, -90),



            
            
            // ======================== AUTO END ======================== //
        };
        
        return actions;
    }
}
