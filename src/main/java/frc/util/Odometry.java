package frc.util;

import frc.robot.Robot;

public final class Odometry {
    public void getCoordinates(double camHeight, double camAngle, double yAngle, double xAngle, double targetHeight, double[] targetCoordinates) {
        double height = targetHeight - camHeight;
        double displacementAngle = yAngle - camAngle;
        double distanceFromTarget = height / Math.tan(displacementAngle);
        double x = targetCoordinates[0] - Math.sin(xAngle) * distanceFromTarget;
        double y = targetCoordinates[1] - Math.cos(xAngle) * distanceFromTarget;
        Robot.robotLocation.update(x, y, Robot.drivetrain.getGyroAngle());
    }

    public void getCoordinates(double[] prevPos, double prevEncoderVal, double encoderVal, double gyroAngle) {
        double distanceTravelled = encoderVal - prevEncoderVal;
        Robot.robotLocation.update(Math.cos(distanceTravelled), Math.sin(distanceTravelled), Robot.drivetrain.getGyroAngle());
    }
}
