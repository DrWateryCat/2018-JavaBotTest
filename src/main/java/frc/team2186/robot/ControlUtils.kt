package frc.team2186.robot

import com.ctre.CANTalon

operator fun CANTalon.plus(slave: CANTalon): CANTalon = apply {
    slave.changeControlMode(CANTalon.TalonControlMode.Follower)
    slave.set(this.deviceID as Double)
}