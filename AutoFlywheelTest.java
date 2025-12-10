package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Direction;
import org.firstinspires.ftc.teamcode.BotConfig;
import org.firstinspires.ftc.teamcode.Auto;


@Autonomous(name = "Flywheel"/*, preselectTeleOp="Your Drive Code Here"*/, group="z")
// @Disabled
public class AutoFlywheelTest extends Auto {

    public Action[] getActions() {
        Action[] actions = {
            // ======================= AUTO START ======================= //

            // Actions Here:
            // Move left
            //new Move(this, -100, -100, -45),
            
            new SpinLauncher(this),
            new Wait(this, 10000),
            
            // ======================== AUTO END ======================== //
        };
        
        return actions;
    }
}

