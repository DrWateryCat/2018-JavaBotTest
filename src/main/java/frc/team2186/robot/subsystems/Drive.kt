package frc.team2186.robot.subsystems

import com.ctre.CANTalon
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.PIDController
import edu.wpi.first.wpilibj.PIDOutput
import edu.wpi.first.wpilibj.PIDSource
import edu.wpi.first.wpilibj.SPI
import frc.team2186.robot.Config
import frc.team2186.robot.Robot
import frc.team2186.robot.lib.common.SynchronousPID
import frc.team2186.robot.lib.math.Rotation2D
import frc.team2186.robot.network.Client
import frc.team2186.robot.plus

object Drive {

    private const val TICKS_PER_REV = 1440
    private const val TICKS_PER_100MS = TICKS_PER_REV * 6.0 / 10.0
    private const val WHEEL_DIAMETER = 6.0

    private val leftSide = CANTalon(Config.Drive.LEFT_MASTER).apply {
        configEncoderCodesPerRev(TICKS_PER_REV)
        reverseSensor(true)
        changeControlMode(CANTalon.TalonControlMode.PercentVbus)
        enableBrakeMode(true)
        p = Config.PID.LeftDrive.P
        i = Config.PID.LeftDrive.I
        d = Config.PID.LeftDrive.D
        f = Config.PID.LeftDrive.F
    } + CANTalon(Config.Drive.LEFT_SLAVE).apply {
        enableBrakeMode(true)
    }

    private val rightSide = CANTalon(Config.Drive.RIGHT_MASTER).apply {
        configEncoderCodesPerRev(TICKS_PER_REV)
        enableBrakeMode(true)
        reverseOutput(true)
        inverted = true
        changeControlMode(CANTalon.TalonControlMode.PercentVbus)
        p = Config.PID.RightDrive.P
        i = Config.PID.RightDrive.I
        d = Config.PID.RightDrive.D
        f = Config.PID.RightDrive.F
    } + CANTalon(Config.Drive.RIGHT_SLAVE).apply {
        enableBrakeMode(true)
    }

    val leftVelocity: Double get() = nativeToInchesPerSecond(-leftSide.encVelocity as Double)
    val rightVelocity: Double get() = nativeToInchesPerSecond(rightSide.encVelocity as Double)

    private var leftVel: Double = 0.0
    private var rightVel: Double = 0.0
    private var gyroA = 0.0

    var leftThrottle: Double = 0.0
    var rightThrottle: Double = 0.0
    var gyroAngle: Double = 0.0

    val gyro: AHRS = AHRS(SPI.Port.kMXP)

    private val velocityHeadingPID = SynchronousPID(1.0, 0.0, 0.0)

    private var currentError = 0.0
    private var deltaSpeed = 0.0

    fun inchesPerSecondToRPM(ips: Double): Double = ips * 60 / (WHEEL_DIAMETER * Math.PI)
    private fun rpmToNativeUnits(rpm: Double): Double = rpm / TICKS_PER_REV / (1 / 60 / 100)
    private fun inchesPerSecondToNativeUnits(ips: Double) = rpmToNativeUnits(inchesPerSecondToRPM(ips))

    private fun nativeToRPM(native: Double): Double = native * TICKS_PER_REV * (1 / 60 / 100)
    fun rpmToInchesPerSecond(rpm: Double) = rpm * (WHEEL_DIAMETER * Math.PI) / 60
    private fun nativeToInchesPerSecond(native: Double) = rpmToInchesPerSecond(nativeToRPM(native))

    init {
        velocityHeadingPID.setOutputRange(-30.0, 30.0)

        Thread {
            update()
        }.start()
    }

    private fun update() {
        while (true) {
            when {
                Robot.currentMode == 0 -> {
                    //Disabled
                    leftSide.set(0.0)
                    rightSide.set(0.0)
                }
                Robot.currentMode == 1 -> {
                    //Auto
                    if (Config.Jetson.ENABLED.not()) {
                        leftVel = leftThrottle
                        rightVel = rightThrottle
                        gyroA = gyroAngle
                    } else {
                        leftVel = Client.requestedLeftVelocity
                        rightVel = Client.requestedRightVelocity
                        gyroA = Client.requestedGyroAngle
                    }

                    calculateVelocityHeading()

                    leftSide.set(inchesPerSecondToNativeUnits(leftVel))
                    rightSide.set(inchesPerSecondToNativeUnits(rightVel))
                }
                Robot.currentMode == 2 -> {
                    //Teleop
                    leftSide.set(this.leftThrottle)
                    rightSide.set(this.rightThrottle)
                }
            }

            Client.leftVelocity = this.leftVelocity
            Client.rightVelocity = this.rightVelocity
            Client.currentGyro = this.gyro.yaw as Double
        }
    }

    fun currentHeading(): Rotation2D = Rotation2D.fromDegrees(this.gyro.yaw as Double)

    fun calculateVelocityHeading() {
        val actualGyro = currentHeading()
        val lastHeadingError = Rotation2D.fromDegrees(gyroA).rotateBy(actualGyro.inverse()).degrees

        val deltaV = velocityHeadingPID.calculate(lastHeadingError)
        leftVel += deltaV / 2
        rightVel -= deltaV / 2
    }

    fun onDisabled() {
        leftSide.changeControlMode(CANTalon.TalonControlMode.PercentVbus)
        rightSide.changeControlMode(CANTalon.TalonControlMode.PercentVbus)
    }

    fun onEnterAutonomous() {
        leftSide.changeControlMode(CANTalon.TalonControlMode.Speed)
        rightSide.changeControlMode(CANTalon.TalonControlMode.Speed)
    }

    fun onEnterTeleop() {
        onDisabled()
    }

    fun stop() {
        when {
            Robot.currentMode == 0 || Robot.currentMode == 2 -> {
                this.leftThrottle = 0.0
                this.rightThrottle = 0.0
            }

            Robot.currentMode == 1 -> {
                this.leftVel = 0.0
                this.rightVel = 0.0
            }
        }
    }
}