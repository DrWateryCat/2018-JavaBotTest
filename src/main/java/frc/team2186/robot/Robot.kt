package frc.team2186.robot

import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team2186.robot.autonomous.AutonomousMode
import frc.team2186.robot.autonomous.modes.DoNothing
import frc.team2186.robot.autonomous.modes.ForwardFiveFeet
import frc.team2186.robot.subsystems.Drive

class Robot : IterativeRobot() {

    private val leftJoystick = Joystick(Config.Controls.LEFT_JOYSTICK)
    private val rightJoystick = Joystick(Config.Controls.RIGHT_JOYSTICK)

    private val autoChooser = SendableChooser<AutonomousMode>()

    override fun robotInit() {
        with(autoChooser) {
            addDefault("Do Nothing", DoNothing())
            addObject("Forward Five Feet", ForwardFiveFeet())
        }

        SmartDashboard.putData("Autonomous Chooser", autoChooser)
    }

    override fun disabledInit() {
        currentMode = 0
        Drive.onDisabled()
    }

    override fun autonomousInit() {
        currentMode = 1
        Drive.onEnterAutonomous()

        autoChooser.selected.init()
    }

    override fun teleopInit() {
        currentMode = 2
        Drive.onEnterTeleop()
    }

    override fun testInit() {}

    override fun disabledPeriodic() {}

    override fun autonomousPeriodic() {
        autoChooser.selected.update()
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