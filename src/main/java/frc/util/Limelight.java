
/* Lime Light Docs: http://docs.limelightvision.io/en/latest/networktables_api.html# */

package frc.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.drive.Vector2d; // Returning Goal Cordinates

public class Limelight {
    // Network Table used to contact Lime Light
    private static NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
    private static NetworkTable table = tableInstance.getTable("limelight");

    /* Commonly Used Contour Information */
    // Whether the limelight has any valid targets (0 or 1)

    private static NetworkTableEntry validTargetEntry = table.getEntry("tv");
    public static boolean hasValidTarget() {
        // > 0.5 converts double to boolean
        return validTargetEntry.getDouble(0) > 0.5;
    }

    // Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    public static final double MIN_X_ANGLE = -27;
    public static final double MAX_X_ANGLE = 27;

    private static NetworkTableEntry xAngleEntry = table.getEntry("tx");
    public static double getTargetXAngle() {
        return xAngleEntry.getDouble(0);
    }

    // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    public static final double MIN_Y_ANGLE = -20.5;
    public static final double MAX_Y_ANGLE = 20.5;

    private static NetworkTableEntry yAngleEntry = table.getEntry("ty");
    public static double getTargetYAngle() {
        return yAngleEntry.getDouble(0);
    }

    // Target Area (0% of image to 100% of image)
    public static final double MIN_TARGET_AREA = 0;
    public static final double MAX_TARGET_AREA = 1;

    private static NetworkTableEntry targetAreaEntry = table.getEntry("ta");
    public static double getTargetArea() {
        // Lime light returns a double from 0 - 100
        // Divide by 100 to scale number from 0 - 1
        return Math.min(targetAreaEntry.getDouble(0) / 100.0, 1);
    }

    // Skew or rotation (-90 degrees to 0 degrees)
    public static final double MIN_SKEW = -90;
    public static final double MAX_SKEW = 0;

    private static NetworkTableEntry targetSkewEntry = table.getEntry("ts");
    public static double getTargetSkew() {
        return targetSkewEntry.getDouble(0);
    }

    // The pipeline’s latency contribution (ms) Add at
    // least 11ms for image capture latency.
    public static final double IMAGE_CAPTURE_LATENCY = 11;
    private static NetworkTableEntry latencyEntry = table.getEntry("tl");
    public static double getLatencyMs() {
        // Add Image Capture Latency to get more accurate result
        return latencyEntry.getDouble(0) + IMAGE_CAPTURE_LATENCY;
    }

    // Pixel information returned from these functions
    public static final double MIN_SIDE_LENGTH = 0;
    public static final double MAX_SIDE_LENGTH = 320;

    // Sidelength of shortest side of the fitted bounding box (0 - 320 pixels)
    private static NetworkTableEntry shortestSideLengthEntry = table.getEntry("tshort");
    public static double getShortestSidelength() {
        return shortestSideLengthEntry.getDouble(0);
    }

    // Sidelength of longest side of the fitted bounding box (0 - 320 pixels)
    private static NetworkTableEntry longestSideLengthEntry = table.getEntry("tlong");
    public static double getLongestSidelength() {
        return longestSideLengthEntry.getDouble(0);
    }

    // Horizontal sidelength of the rough bounding box (0 - 320 pixels)
    private static NetworkTableEntry horizontalSideLengthEntry = table.getEntry("thor");
    public static double getHorizontalSidelength() {
        return horizontalSideLengthEntry.getDouble(0);
    }

    // Vertical sidelength of the rough bounding box (0 - 320 pixels)
    private static NetworkTableEntry verticalSideLengthEntry = table.getEntry("tvert");
    public static double getVerticalSidelength() {
        return verticalSideLengthEntry.getDouble(0);
    }

    /* Advanced Usage with Raw Contours (Not sent by default) */
    // Raw Contours are formatted as tx0, ty0, tx1, ty1, tx2, ty2
    // So to make this easier, you pass an int and it formats it

    public static double getRawTargetXAngle(int target) {
        return table.getEntry("tx" + target).getDouble(0);
    }

    public static double getRawTargetYAngle(int target) {
        return table.getEntry("ty" + target).getDouble(0);
    }

    public static double getRawTargetArea(int target) {
        // Lime light returns a double from 0 - 100
        // Divide by 100 to scale number from 0 - 1
        return Math.min(table.getEntry("ta" + target).getDouble(0) / 100.0, 1);
    }

    public static double getRawTargetSkew(int target) {
        return table.getEntry("ts" + target).getDouble(0);
    }

    public static double getCustomRawCrosshairX(int crosshair) {
        return table.getEntry("cx" + crosshair).getDouble(0);
    }

    public static double getRawCrosshairY(int crosshair) {
        return table.getEntry("cy" + crosshair).getDouble(0);
    }

    /* Custom Grip Values */
    // Return data given by custom GRIP pipeline
    public static double getCustomDouble(String element) {
        return table.getEntry(element).getDouble(0);
    }

    public static boolean setCustomNumber(String element, Number value) {
        return table.getEntry(element).setNumber(value);
    }

    public static String getCustomString(String element) {
        return table.getEntry(element).getString("");
    }

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
    public static void setCamMode(CamMode mode) {
        camModeEntry.setNumber(mode.getCodeValue());
    }

    // PIPELINE
    private static NetworkTableEntry pipelineEntry = table.getEntry("pipeline");
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
    public static void setSnapshotMode(SnapshotMode snapshot) {
        SnapshotModeEntry.setNumber(snapshot.getCodeValue());
    }

    /* Math using limelight values */
    // Calculate Distance using TY
    public static double getTargetDistance(double heightFromCamera, double cameraAngle) {
        return heightFromCamera / Math.tan(Math.toRadians(getTargetYAngle() + cameraAngle));
    }

    // Coordinates of limelight relative to the center of the robot
    // This is necessary to make MP easier
    public static final double LIMELIGHT_X_POS = 0;
    public static final double LIMELIGHT_Y_POS = 0;

    public static Vector2d getTargetCoordinates(double heightFromCamera, double cameraAngle) {
        final double DISTANCE = getTargetDistance(heightFromCamera, cameraAngle);
        final double XOFFSET = Math.toRadians(getTargetXAngle());
        return new Vector2d(DISTANCE * Math.sin(XOFFSET) + LIMELIGHT_X_POS,
                DISTANCE * Math.cos(XOFFSET) + LIMELIGHT_Y_POS);
    }
}
