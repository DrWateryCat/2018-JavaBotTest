package frc.team2186.robot.autonomous.modes

import edu.wpi.first.wpilibj.Timer
import frc.team2186.robot.autonomous.AutonomousMode
import frc.team2186.robot.network.Client
import frc.team2186.robot.subsystems.Drive

class ForwardFiveFeet : AutonomousMode {
    override fun init() {
    }
    val initialTime: Double by lazy {Timer.getFPGATimestamp()}
    val deltaTime: Double get() = Timer.getFPGATimestamp() - initialTime
    override fun update() {
        if (deltaTime > 1.0) {
            Drive.stop()
        } else {
            Drive.leftThrottle = (5.0 * 12)
            Drive.rightThrottle = (5.0 * 12)
            Drive.gyroAngle = 0.0
        }
    }
}