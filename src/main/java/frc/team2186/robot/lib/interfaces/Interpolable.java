package frc.team2186.robot.lib.interfaces;

public interface Interpolable<T> {
    T interpolate(T other, double x);
}
