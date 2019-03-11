/* Lime Light Docs: http://docs.limelightvision.io/en/latest/networktables_api.html# */

package frc.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.drive.Vector2d; // Returning Goal Cordinates
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight {
    // Network Table used to contact Lime Light
    private static NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
    private static NetworkTable table = tableInstance.getTable("limelight");

    /* Commonly Used Contour Information */
    // Whether the limelight has any valid targets (0 or 1)
    private static NetworkTableEntry validTargetEntry = table.getEntry("tv");

    // Not final incase user wants
    // to change them at runtime
    public static double DEFAULT_TARGET_HEIGHT_THRESHOLD = 6;
    public static double DEFAULT_MIN_ASPECT_RATIO = 1.2;
    public static double DEFAULT_MAX_ASPECT_RATIO = 3.3;
    public static double DEFAULT_ANGLE_THRESHOLD = 25;

    // Toggle for posting to SmartDashboard
    public static final boolean POST_TO_SMART_DASHBOARD = true;

    /**
     * @param targetHeightThreshold Height threshold for target
     * @param minRatio              Min ratio for the blue aspect ratio
     * @param maxRatio              Max ratio for the blue aspect ratio
     * @param angleThreshold        maximum skew the target can have
     * @return Whether or not the limelight has a target in view
     */
    public static boolean hasValidTarget(
            double targetHeightThreshold, 
            double minRatio, double maxRatio,
            double angleThreshold) {
        return hasAnyTarget() 
             & hasValidHeight(targetHeightThreshold) 
             & hasValidBlueAspectRatio(minRatio, maxRatio)
             & hasValidBlueOrientation(angleThreshold);
    }

    /**
     * @return Whether or not the limelight has a target in view
     */
    public static boolean hasValidTarget() {
        return hasValidTarget(
            DEFAULT_TARGET_HEIGHT_THRESHOLD, 
            DEFAULT_MIN_ASPECT_RATIO, 
            DEFAULT_MAX_ASPECT_RATIO,
            DEFAULT_ANGLE_THRESHOLD);
    }

    /**
     * Decides if a target shows up on limelight screen
     * @return If it has any target
     */
    public static boolean hasAnyTarget() {
        boolean validTarget = validTargetEntry.getDouble(0) > 0.5;

        if (POST_TO_SMART_DASHBOARD) {
            SmartDashboard.putBoolean("Valid Target", validTarget);
        }

        return validTarget;
    }

    /**
     * @param targetHeightThreshold Height threshold for target
     * @return If the target fits the height threshold
     */
    public static boolean hasValidHeight(double targetHeightThreshold) {
        boolean validHeight = getTargetYAngle() < targetHeightThreshold;

        if (POST_TO_SMART_DASHBOARD) {
            SmartDashboard.putBoolean("Valid Height", validHeight);
        }

        return validHeight;
    }

    /**
     * The blue aspect ratio is the ratio of the width to height of the rotated
     * rectangle.
     * 
     * @param minRatio Min ratio for the blue aspect ratio
     * @param maxRatio Max ratio for the blue aspect ratio
     * @return If the blue aspect ratio fits the thresholds
     */
    public static boolean hasValidBlueAspectRatio(double minRatio, double maxRatio) {
        double aspectRatio = getHorizontalSidelength() / getVerticalSidelength();
        boolean validRatio = aspectRatio > minRatio && aspectRatio < maxRatio;

        if (POST_TO_SMART_DASHBOARD) {
            SmartDashboard.putBoolean("Valid Ratio", validRatio);
            SmartDashboard.putNumber("Aspect Ratio", aspectRatio);
        }

        return validRatio;
    }

    /**
     * @param angleThreshold maximum skew the target can have
     * @return if the skew is less than the maximum skew
     */
    public static boolean hasValidBlueOrientation(double angleThreshold) {
        double skew = Math.abs(getTargetSkew());
        boolean validOrientation = Math.min(skew, 90.0 - skew) <= angleThreshold;

        if (POST_TO_SMART_DASHBOARD) {
            SmartDashboard.putBoolean("VALID_SKEW", validOrientation);
            SmartDashboard.putNumber("SKEW_VALUE", Math.min(skew, 90.0 - skew));
        }

        return validOrientation;
    }

    // Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    public static final double MIN_X_ANGLE = -27;
    public static final double MAX_X_ANGLE = 27;
    private static NetworkTableEntry xAngleEntry = table.getEntry("tx");

    /**
     * @return Horizontal side length of the target
     */
    public static double getTargetXAngle() {
        return xAngleEntry.getDouble(0);
    }

    // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    public static final double MIN_Y_ANGLE = -20.5;
    public static final double MAX_Y_ANGLE = 20.5;
    private static NetworkTableEntry yAngleEntry = table.getEntry("ty");

    /**
     * @return The vertical angle of the target
     */
    public static double getTargetYAngle() {
        return yAngleEntry.getDouble(0);
    }

    // Target Area (0% of image to 100% of image)
    public static final double MIN_TARGET_AREA = 0;
    public static final double MAX_TARGET_AREA = 1;
    private static NetworkTableEntry targetAreaEntry = table.getEntry("ta");

    /**
     * @return Percent of the screen the target takes up on a scale of 0 to 1
     */
    public static double getTargetArea() {
        // Lime light returns a double from 0 - 100
        // Divide by 100 to scale number from 0 - 1
        return Math.min(targetAreaEntry.getDouble(0) / 100.0, 1);
    }

    // Skew or rotation (-90 degrees to 0 degrees)
    public static final double MIN_SKEW = -90;
    public static final double MAX_SKEW = 0;
    private static NetworkTableEntry targetSkewEntry = table.getEntry("ts");

    /**
     * @return Skew of the Target
     */
    public static double getTargetSkew() {
        return targetSkewEntry.getDouble(0);
    }

    // The pipeline’s latency contribution (ms) Add at
    // least 11ms for image capture latency.
    public static final double IMAGE_CAPTURE_LATENCY = 11;
    private static NetworkTableEntry latencyEntry = table.getEntry("tl");

    /**
     * @return Latency of limelight information in milli-seconds
     */
    public static double getLatencyMs() {
        // Add Image Capture Latency to get more accurate result
        return latencyEntry.getDouble(0) + IMAGE_CAPTURE_LATENCY;
    }

    // Pixel information returned from these functions
    public static final double MIN_SIDE_LENGTH = 0;
    public static final double MAX_SIDE_LENGTH = 320;

    private static NetworkTableEntry shortestSideLengthEntry = table.getEntry("tshort");

    /**
     * Sidelength of shortest side of the fitted bounding box (0 - 320 pixels)
     * 
     * @return Shortest side length of target in pixels
     */
    public static double getShortestSidelength() {
        return shortestSideLengthEntry.getDouble(0);
    }

    private static NetworkTableEntry longestSideLengthEntry = table.getEntry("tlong");

    /**
     * Sidelength of longest side of the fitted bounding box (0 - 320 pixels)
     * 
     * @return Longest side length of the target in pixels
     */
    public static double getLongestSidelength() {
        return longestSideLengthEntry.getDouble(0);
    }

    private static NetworkTableEntry horizontalSideLengthEntry = table.getEntry("thor");

    /**
     * Horizontal sidelength of the rough bounding box (0 - 320 pixels)
     * 
     * @return Horizontal side length of target in pixels
     */
    public static double getHorizontalSidelength() {
        return horizontalSideLengthEntry.getDouble(0);
    }

    private static NetworkTableEntry verticalSideLengthEntry = table.getEntry("tvert");

    /**
     * Vertical sidelength of the rough bounding box (0 - 320 pixels)
     * 
     * @return Vertical side length of target in pixels
     */
    public static double getVerticalSidelength() {
        return verticalSideLengthEntry.getDouble(0);
    }

    /* Advanced Usage with Raw Contours (Not sent by default) */
    // Raw Contours are formatted as tx0, ty0, tx1, ty1, tx2, ty2
    // So to make this easier, you pass an int and it formats it

    /**
     * @param target Target to read X Angle from
     * @return X Angle of corresponding target
     */
    public static double getRawTargetXAngle(int target) {
        return table.getEntry("tx" + target).getDouble(0);
    }

    /**
     * @param target Target to read Y Angle from
     * @return Y Angle of corresponding target
     */
    public static double getRawTargetYAngle(int target) {
        return table.getEntry("ty" + target).getDouble(0);
    }

    /**
     * @param target Target to read Area from
     * @return Percent of the screen the corresponding target takes up on a scale of
     *         0 to 1
     */
    public static double getRawTargetArea(int target) {
        // Lime light returns a double from 0 - 100
        // Divide by 100 to scale number from 0 - 1
        return Math.min(table.getEntry("ta" + target).getDouble(0) / 100.0, 1);
    }

    /**
     * @param target Target to read Skew from
     * @return Skew of corresponding target
     */
    public static double getRawTargetSkew(int target) {
        return table.getEntry("ts" + target).getDouble(0);
    }

    /**
     * @param crosshair Crosshair to read coords from
     * @return X Coordinate of corresponding crosshair
     */
    public static double getCustomRawCrosshairX(int crosshair) {
        return table.getEntry("cx" + crosshair).getDouble(0);
    }

    /**
     * @param crosshair Crosshair to read coords from
     * @return Y Coordinate of corresponding crosshair
     */
    public static double getRawCrosshairY(int crosshair) {
        return table.getEntry("cy" + crosshair).getDouble(0);
    }

    /* Custom Grip Values */
    // Return data given by custom GRIP pipeline
    /**
     * @param element Name of double provided by GRIP Pipeline
     * @return Double provided by GRIP Pipeline
     */
    public static double getCustomDouble(String element) {
        return table.getEntry(element).getDouble(0);
    }

    /**
     * @param element Name of Number to set on Network Table
     * @param value   Value to set the Number on the Network Table to
     * @return Whether or not the write was successful
     */
    public static boolean setCustomNumber(String element, Number value) {
        return table.getEntry(element).setNumber(value);
    }

    /**
     * @param element Name of String provided by GRIP Pipeline
     * @return String provided by GRIP Pipeline
     */
    public static String getCustomString(String element) {
        return table.getEntry(element).getString("");
    }

    /**
     * @param element Name of String to set on Network Table
     * @param value   Value to set the Sting on the Network Table to
     * @return Whether or not the write was successful
     */
    public static boolean setCustomString(String element, String value) {
        return table.getEntry(element).setString(value);
    }

    /* Camera Controls (Use Enums to prevent invalid inputs) */
    // LEDMode
    public enum LEDMode {
        PIPELINE(0), // Use LED mode set in pipeline
        FORCE_OFF(1), // Force LEDs off
        FORCE_BLINK(2), // Force LEDs to blink
        FORCE_ON(3); // Force LEDs on

        LEDMode(int value) {
            this.val = value;
        }

        public int getCodeValue() {
            return val;
        }

        private int val;
    };

    private static NetworkTableEntry LEDModeEntry = table.getEntry("ledMode");

    /**
     * @param mode Specified LED Mode to set the limelight to
     */
    public static void setLEDMode(LEDMode mode) {
        LEDModeEntry.setNumber(mode.getCodeValue());
    }

    // CAM_MODE
    public enum CamMode {
        VISION(0), // Use limelight for CV
        DRIVER(1); // Use limelight for driving

        CamMode(int value) {
            this.val = value;
        }

        public int getCodeValue() {
            return val;
        }

        private int val;
    };

    private static NetworkTableEntry camModeEntry = table.getEntry("camMode");

    /**
     * @param mode Specified Cam Mode to set the limelight to
     */
    public static void setCamMode(CamMode mode) {
        camModeEntry.setNumber(mode.getCodeValue());
    }

    // PIPELINE
    private static NetworkTableEntry pipelineEntry = table.getEntry("pipeline");

    /**
     * @param pipeline Specified pipeline to set the limelight to
     */
    public static void setPipeline(int pipeline) {
        // Prevent input of invalid pipelines
        if (pipeline >= 0 && pipeline <= 9) {
            pipelineEntry.setNumber(pipeline);
        }
    }

    private static NetworkTableEntry getPipelineEntry = table.getEntry("getpipe");

    public static double getPipeline() {
        return getPipelineEntry.getDouble(0);
    }

    // STREAM
    public enum CameraStream { // PIP = Picture-In-Picture
        STANDARD(0), // Shows limelight and secondary camera side by side
        PIP_MAIN(1), // Shows the secondary camera along with and within the limelight camera
        PIP_SECONDARY(2); // Shows the limelight along with and within the limelight camera

        CameraStream(int value) {
            this.val = value;
        }

        public int getCodeValue() {
            return val;
        }

        private int val;
    };

    private static NetworkTableEntry CameraStreamEntry = table.getEntry("stream");

    /**
     * @param stream Specified Camera Stream to set the limelight to
     */
    public static void setCameraStream(CameraStream stream) {
        CameraStreamEntry.setNumber(stream.getCodeValue());
    }

    // SNAPSHOT_MODE
    public enum SnapshotMode {
        STOP(0), // Don't take snapshots
        TWO_PER_SECOND(1);

        SnapshotMode(int value) {
            this.val = value;
        }

        public int getCodeValue() {
            return val;
        }

        private int val;
    };

    private static NetworkTableEntry SnapshotModeEntry = table.getEntry("snapshot");

    /**
     * @param mode Specified Snapshot Mode to set the limelight to
     */
    public static void setSnapshotMode(SnapshotMode mode) {
        SnapshotModeEntry.setNumber(mode.getCodeValue());
    }

    /* Math using limelight values */
    /**
     * Calculate Distance using TY
     * 
     * @param heightFromCamera Height from limelight camera to center of the target
     * @param cameraAngle      Angle at which the limelight is placed
     * @return Distance from the target to the camera
     */
    public static double getTargetDistance(double heightFromCamera, double cameraAngle) {
        return heightFromCamera / Math.tan(Math.toRadians(getTargetYAngle() + cameraAngle));
    }

    // Coordinates of limelight relative to the center of the robot
    // This is necessary to make MP easier
    public static final double LIMELIGHT_X_POS = 0;
    public static final double LIMELIGHT_Y_POS = 0;

    /**
     * Calculate Coordinates using TY
     * 
     * @param heightFromCamera Height from limelight camera to center of the target
     * @param cameraAngle      Angle at which the limelight is placed
     * @return Coordinates of the target from the limelight camera
     */
    public static Vector2d getTargetCoordinates(double heightFromCamera, double cameraAngle) {
        final double DISTANCE = getTargetDistance(heightFromCamera, cameraAngle);
        final double XOFFSET = Math.toRadians(getTargetXAngle());
        return new Vector2d(DISTANCE * Math.sin(XOFFSET) + LIMELIGHT_X_POS,
                DISTANCE * Math.cos(XOFFSET) + LIMELIGHT_Y_POS);
    }
}
