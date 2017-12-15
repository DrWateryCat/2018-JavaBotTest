package frc.team2186.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2186.robot.auto.AutonomousMode;
import frc.team2186.robot.auto.DoNothing;
import frc.team2186.robot.auto.ForwardFiveSeconds;
import frc.team2186.robot.net.Client;
import frc.team2186.robot.subsystems.Drive;

public class Robot extends IterativeRobot {
    Joystick left = new Joystick(Config.CONTROLS.LEFT);
    Joystick right = new Joystick(Config.CONTROLS.RIGHT);

    SendableChooser<AutonomousMode> chooser = new SendableChooser<>();
    AutonomousMode chosen;

    @Override
    public void robotInit() {
        new Thread(() -> Client.getInstance().run()).start();
        new Thread(() -> Drive.getInstance().run()).start();

        chooser.addDefault("Do Nothing", new DoNothing());
        chooser.addObject("Forward Five Seconds", new ForwardFiveSeconds());

        SmartDashboard.putData("Autonomous mode", chooser);
    }

    @Override
    public void disabledInit() { }

    @Override
    public void autonomousInit() {
        chosen = chooser.getSelected();
    }

    @Override
    public void teleopInit() { }

    @Override
    public void testInit() { }


    @Override
    public void disabledPeriodic() { }

    @Override
    public void autonomousPeriodic() {
        chosen.run();
    }

    @Override
    public void teleopPeriodic() {
        Drive.getInstance().drive(left.getRawAxis(1), right.getRawAxis(1));
    }

    @Override
    public void testPeriodic() { }
}