package frc.util;

//TODO:Add import for field position class in frc plugin
//TODO:Replace double[] with field position

public final class Odometry {
    public double[] getCoordinates(double camHeight, double camAngle, double yAngle, double xAngle, double targetHeight, double[] targetCoordinates) {
        double height = targetHeight - camHeight;
        double displacementAngle = yAngle - camAngle;
        double distanceFromTarget = height / Math.tan(displacementAngle);
        double x = targetCoordinates[0] - Math.sin(xAngle) * distanceFromTarget;
        double y = targetCoordinates[1] - Math.cos(xAngle) * distanceFromTarget;
        return new double[] {x, y};
    }

    public double[] getCoordinates(double[] prevPos, double prevEncoderVal, double encoderVal, double gyroAngle) {
        double distanceTravelled = encoderVal - prevEncoderVal;
        return new double[] {Math.cos(distanceTravelled), Math.sin(distanceTravelled)};
    }
}