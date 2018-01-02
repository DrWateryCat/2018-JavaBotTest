package frc.team2186.robot.autonomous.modes

import frc.team2186.robot.autonomous.AutonomousMode
import frc.team2186.robot.subsystems.Drive

class DriveTuning : AutonomousMode {
    override fun init() {
    }

    override fun update() {
        Drive.leftThrottle = Drive.rpmToInchesPerSecond(60.0)
        Drive.rightThrottle = Drive.rpmToInchesPerSecond(60.0)
    }
}