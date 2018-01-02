package frc.team2186.robot.autonomous.modes

import frc.team2186.robot.Config
import frc.team2186.robot.autonomous.AutonomousMode

class UseJetson : AutonomousMode {
    override fun init() {
        Config.Jetson.ENABLED = true
    }

    override fun update() {
    }
}