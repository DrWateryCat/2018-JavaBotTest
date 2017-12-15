package frc.team2186.robot.auto;

import edu.wpi.first.wpilibj.Timer;
import frc.team2186.robot.subsystems.Drive;

public class ForwardFiveSeconds implements AutonomousMode {
    boolean runOnce = false;
    double currentTime;
    public void run() {
        if (!runOnce) {
            currentTime = Timer.getFPGATimestamp();
        }
        Drive.getInstance().drive(1, 1);
        if (currentTime >= currentTime + 5000) {
            Drive.getInstance().drive(0, 0);
        }

        runOnce = true;
    }
}
