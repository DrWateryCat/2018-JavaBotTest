package frc.team2186.robot.common;

import com.ctre.CANTalon;

public class DriveGearbox {
    private CANTalon master;
    private CANTalon slave;

    private double currValue;

    public DriveGearbox(GearboxConfig cfg) throws IllegalArgumentException {
        if (!cfg.TALON) throw new IllegalArgumentException("Drive gearboxes must be talons!");

        master = new CANTalon(cfg.MASTER);
        slave = new CANTalon(cfg.SLAVE);

        slave.changeControlMode(CANTalon.TalonControlMode.Follower);
        slave.set(cfg.MASTER);
    }

    public void set(double val) {
        currValue = val;
    }

    public void execute() {
        master.set(currValue);
    }
}
