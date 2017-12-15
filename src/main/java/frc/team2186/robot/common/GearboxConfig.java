package frc.team2186.robot.common;

public class GearboxConfig {
    public final int MASTER;
    public final int SLAVE;
    public final boolean TALON;

    public GearboxConfig(int master, int slave, boolean talon) {
        MASTER = master;
        SLAVE = slave;
        TALON = talon;
    }
}
