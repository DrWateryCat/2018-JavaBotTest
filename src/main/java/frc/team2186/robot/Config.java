package frc.team2186.robot;

import frc.team2186.robot.common.GearboxConfig;

public class Config {
    public static final GearboxConfig LEFT_GEARBOX = new GearboxConfig(0, 1, true);
    public static final GearboxConfig RIGHT_GEARBOX = new GearboxConfig(2, 3, true);

    public static final String JETSON_IP = "10.21.86.4";

    public static final class CONTROLS {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
    }
}
