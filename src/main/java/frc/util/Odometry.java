package frc.util;

import frc.plugin.FieldPosition;
import frc.robot.Robot;

public final class Odometry {

    private static double prevEncoderVal = 0;

    public static void setCoordinates(double camHeight, double camAngle, double yAngle, double xAngle, double targetHeight, double[] targetCoordinates) {
        double height = targetHeight - camHeight;
        double displacementAngle = yAngle - camAngle;
        double distanceFromTarget = height / Math.tan(displacementAngle);
        double x = targetCoordinates[0] - Math.sin(xAngle) * distanceFromTarget;
        double y = targetCoordinates[1] - Math.cos(xAngle) * distanceFromTarget;
        Robot.robotLocation.update(x, y, Robot.drivetrain.getGyroAngle());
    }

    public static void setCoordinates(FieldPosition prevPos, double encoderVal) {
        double distanceTravelled = encoderVal - prevEncoderVal;
        prevEncoderVal = encoderVal;
        prevPos.update(Math.cos(distanceTravelled), Math.sin(distanceTravelled), Robot.drivetrain.getGyroAngle());
    }
    //TODO:Add slick paths (hopefully)
}
