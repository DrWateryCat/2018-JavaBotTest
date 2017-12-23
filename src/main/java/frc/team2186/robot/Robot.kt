package frc.team2186.robot

import edu.wpi.first.wpilibj.IterativeRobot
import frc.team2186.robot.subsystems.Drive

class Robot : IterativeRobot() {

    override fun robotInit() {
    }

    override fun disabledInit() {
        currentMode = 0
        Drive.onDisabled()
    }

    override fun autonomousInit() {
        currentMode = 1
        Drive.onEnterAutonomous()
    }

    override fun teleopInit() {
        currentMode = 2
        Drive.onEnterTeleop()
    }

    override fun testInit() {}

    override fun disabledPeriodic() {}

    override fun autonomousPeriodic() {
    }

    override fun teleopPeriodic() {
    }

    override fun testPeriodic() {}

    companion object {
        var currentMode: Int = 0
    }
}