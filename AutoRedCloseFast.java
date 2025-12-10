package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Direction;
import org.firstinspires.ftc.teamcode.BotConfig;
import org.firstinspires.ftc.teamcode.Auto;


@Autonomous(name = "Red Close Fast"/*, preselectTeleOp="Your Drive Code Here"*/, group="red")
//@Disabled
public class AutoRedCloseFast extends Auto {
    
    Action[] launchSequence = {
        // Get ready for launching
        new OpenStopper(this),
        new SpinLauncher(this),
    
        // Move to shooting position
            new Move(this, -450, 1000, -50),
        
        // Launch!
        new WaitForLauncher(this),
        new Wait(this, 500),
        new SpinPusher(this),
        new Wait(this, 1000),
        new SpinIntake(this),
        new SpinPusher(this, 2),
        
        new Wait(this, 1500),

        // Reset
        new StopLauncher(this),
        new StopIntake(this),
        new StopPusher(this)
    };
        

    public Action[] getActions() {
        Action[] actions = {
            // ======================= AUTO START ======================= //
            // Launch!
            new ActionSequence(this, launchSequence),
            
            // Move to first line
            new CloseStopper(this),
            new Move(this, -680, 800, 90),
            
            // Intake artifacts
            new SpinIntake(this),
            new SpinPusher(this), 
            new Move(this, -680, 100, 90),
            new Wait(this, 750),
            new StopPusher(this),
            new StopIntake(this),
            
            new ActionSequence(this, launchSequence),
            
            // Move to second line
            new CloseStopper(this),
            new Move(this, -1350, 800, 90),
            
            // Intake artifacts
            new SpinIntake(this),
            new SpinPusher(this), 
            new Move(this, -1375, -100, 90),
            new Wait(this, 750),
            new StopPusher(this),
            new StopIntake(this),
            
            new Move(this, -1375, 200, 90),
            
            new ActionSequence(this, launchSequence),
            
            // End sequence
            
            // Move out of triangle
            new CloseStopper(this),
            new Move(this, -650, 300, 90),
            
            // ======================== AUTO END ======================== //
        };
        
        return actions;
    }
}

