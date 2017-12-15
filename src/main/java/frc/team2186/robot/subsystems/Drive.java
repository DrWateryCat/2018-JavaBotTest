package frc.team2186.robot.subsystems;

import frc.team2186.robot.Config;
import frc.team2186.robot.common.DriveGearbox;

public class Drive {
    private static Drive ourInstance = new Drive();

    public static Drive getInstance() {
        return ourInstance;
    }

    private DriveGearbox left;
    private DriveGearbox right;

    private double leftVal = 0;
    private double rightVal = 0;

    private Object lock = new Object();

    private Drive() {
        left = new DriveGearbox(Config.LEFT_GEARBOX);
        right = new DriveGearbox(Config.RIGHT_GEARBOX);
    }

    public void drive(double left, double right) {
        synchronized (lock) {
            leftVal = left;
            rightVal = right;
        }
    }

    public void stop() {
        synchronized (lock) {
            leftVal = 0;
            rightVal = 0;
        }
    }

    public void run() {
        while (true) {
            synchronized (lock) {
                left.set(leftVal);
                right.set(rightVal);
            }

            left.execute();
            right.execute();
        }
    }
}
