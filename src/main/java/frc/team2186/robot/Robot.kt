package frc.team2186.robot

import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.Joystick
import frc.team2186.robot.subsystems.Drive

class Robot : IterativeRobot() {

    private val leftJoystick = Joystick(Config.Controls.LEFT_JOYSTICK)
    private val rightJoystick = Joystick(Config.Controls.RIGHT_JOYSTICK)

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
        Drive.leftThrottle = leftJoystick.getRawAxis(1)
        Drive.rightThrottle = rightJoystick.getRawAxis(1)
    }

    override fun testPeriodic() {}

    companion object {
        var currentMode: Int = 0
    }
}