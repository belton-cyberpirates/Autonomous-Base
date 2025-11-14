package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Direction;


public class Move extends Action {
    Auto auto;
    Direction dir;
    int distance;
    ElapsedTime startTime;
    double expectedTime;
    
    public Move(Auto auto, Direction dir, int distance) {
        this.auto = auto;
        
        this.dir = dir;
        this.distance = distance;
    }
    
    public Move(Auto auto, Direction dir, int distance, double expectedTime) {
        this.auto = auto;
        
        this.dir = dir;
        this.distance = distance;
        this.expectedTime = expectedTime;
    }

    public void onStart() {
        startTime = new ElapsedTime();
        auto.driveMotors.MoveWithEncoders(dir, distance);
    }
    
    public boolean isDone() {
        return auto.driveMotors.isDone() /*|| (expectedTime != 0 && startTime.time() >= expectedTime*/;
    }

}
