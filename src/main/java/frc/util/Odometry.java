package frc.util;

import frc.plugin.FieldPosition;
import frc.robot.Robot;

public final class Odometry {
    public FieldPosition getCoordinates(double camHeight, double camAngle, double yAngle, double xAngle, double targetHeight, double[] targetCoordinates) {
        double height = targetHeight - camHeight;
        double displacementAngle = yAngle - camAngle;
        double distanceFromTarget = height / Math.tan(displacementAngle);
        double x = targetCoordinates[0] - Math.sin(xAngle) * distanceFromTarget;
        double y = targetCoordinates[1] - Math.cos(xAngle) * distanceFromTarget;
        return new FieldPosition(x, y, Robot.drivetrain.getGyroAngle());
    }

    public FieldPosition getCoordinates(double[] prevPos, double prevEncoderVal, double encoderVal, double gyroAngle) {
        double distanceTravelled = encoderVal - prevEncoderVal;
        return new FieldPosition(Math.cos(distanceTravelled), Math.sin(distanceTravelled), Robot.drivetrain.getGyroAngle());
    }
}
