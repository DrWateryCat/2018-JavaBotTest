package frc.team2186.robot.autonomous.modes

import edu.wpi.first.wpilibj.Timer
import frc.team2186.robot.autonomous.AutonomousMode
import frc.team2186.robot.network.Client
import frc.team2186.robot.subsystems.Drive

class ForwardFiveFeet : AutonomousMode {
    override fun init() {
    }
    var doOnce = false
    var initialTime = 0.0
    override fun update() {
        if (!doOnce) {
            initialTime = Timer.getFPGATimestamp()
            doOnce = true
        }

        if (Timer.getFPGATimestamp() - initialTime > 1.0) {
            Drive.stop()
        } else {
            //Hack until I can figure out what I want to do
            Client.requestedLeftVelocity = 60.0
            Client.requestedRightVelocity = 60.0
            Client.requestedGyroAngle = 0.0
        }
    }
}