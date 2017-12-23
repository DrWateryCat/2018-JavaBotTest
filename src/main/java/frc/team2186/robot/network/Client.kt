package frc.team2186.robot.network

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.networktables.NetworkTable
import frc.team2186.robot.Config
import frc.team2186.robot.Robot
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket

object Client {
    private val table = NetworkTable.getTable("Robot Client")

    init {
        table.setDefaultBoolean("Jetson Connected", false)
        table.setDefaultNumber("Requested Left", 0.0)
        table.setDefaultNumber("Requested Right", 0.0)

        Thread {
            update()
        }.start()
    }

    private fun update() {
        while (true) {
            val jetson = Socket(Config.Jetson.IP, Config.Jetson.PORT)
            val output = DataOutputStream(jetson.getOutputStream())
            val input = BufferedReader(InputStreamReader(jetson.getInputStream()))

            while (jetson.isConnected) {
                val toJetson = JsonObject()
                synchronized(this) {
                    toJetson.addProperty("left_position", this.leftPosition)
                    toJetson.addProperty("right_position", this.rightPosition)
                    toJetson.addProperty("left_velocity", this.leftVelocity)
                    toJetson.addProperty("right_velocity", this.rightVelocity)
                    toJetson.addProperty("current_gyro", this.currentGyro)
                    toJetson.addProperty("current_mode", Robot.currentMode)
                    toJetson.addProperty("current_time", Timer.getFPGATimestamp())
                }

                output.writeBytes(toJetson.asString + '\n')

                val fromJetson = JsonParser().parse(input.readLine())

                synchronized(this) {
                    this.requestedLeftVelocity = fromJetson.asJsonObject.get("left_velocity").asDouble
                    this.requestedRightVelocity = fromJetson.asJsonObject.get("right_velocity").asDouble
                    this.requestedGyroAngle = fromJetson.asJsonObject.get("gyro_angle").asDouble
                    this.connected = true

                    table.putNumber("Requested Left", this.requestedLeftVelocity)
                    table.putNumber("Requested Right", this.requestedRightVelocity)
                    table.putNumber("Requested Gyro", this.requestedGyroAngle)
                    table.putBoolean("Jetson Connected", this.connected)
                }
            }

            connected = false
        }
    }

    var leftPosition: Double = 0.0
    var rightPosition: Double = 0.0

    var leftVelocity: Double = 0.0
    var rightVelocity: Double = 0.0

    var currentGyro: Double = 0.0

    var connected: Boolean = false

    var requestedLeftVelocity: Double = 0.0
    var requestedRightVelocity: Double = 0.0
    var requestedGyroAngle: Double = 0.0
}