 
/* Lime Light Docs: http://docs.limelightvision.io/en/latest/networktables_api.html# */

package frc.util;

import frc.util.NetworkTableClient;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.drive.Vector2d; // Returning Goal Cordinates

public class LimeLight {
    // Network Table used to contact Lime Light
    private static NetworkTableClient table = new NetworkTableClient("limelight");

    /* Commonly Used Network Table Entries */
    // Store these entrys as variables to prevent overcalling table.getEntry();
    private static NetworkTableEntry ValidTarget = table.getEntry("tv");
    private static NetworkTableEntry XOffset = table.getEntry("tx");
    private static NetworkTableEntry YOffset = table.getEntry("ty");
    private static NetworkTableEntry TargetArea = table.getEntry("ta");
    private static NetworkTableEntry TargetSkew = table.getEntry("ts");
    private static NetworkTableEntry Latency = table.getEntry("ts");
    private static NetworkTableEntry ShortestSideLength = table.getEntry("tshort");
    private static NetworkTableEntry LongestSideLength = table.getEntry("tlong");
    private static NetworkTableEntry HorizontalSideLength = table.getEntry("thoriz");
    private static NetworkTableEntry VerticalSideLength = table.getEntry("tvert");

    /* Commonly Used Contour Information */
    // Whether the limelight has any valid targets (0 or 1)
    public static boolean hasValidTarget() {
        // == 1 converts double to boolean
        return ValidTarget.getDouble(0) == 1;
    }

    // Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    public static final double MIN_X_OFFSET = -27;
    public static final double MAX_X_OFFSET = 27;

    public static double getTargetXOffset() {
        return XOffset.getDouble(0);
    }

    // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    public static final double MIN_Y_OFFSET = -20.5;
    public static final double MAX_Y_OFFSET = 20.5;

    public static double getTargetYOffset() {
        return YOffset.getDouble(0);
    }

    // Target Area (0% of image to 100% of image)
    public static final double MIN_TARGET_AREA = 0;
    public static final double MAX_TARGET_AREA = 1;

    public static double getTargetArea() {
        // Lime light returns a double from 0 - 100
        // Divide by 100 to scale number from 0 - 1
        return Math.min(TargetArea.getDouble(0) / 100.0, 1);
    }

    // Skew or rotation (-90 degrees to 0 degrees)
    public static final double MIN_SKEW = -90;
    public static final double MAX_SKEW = 0;

    public static double getTargetSkew() {
        return TargetSkew.getDouble(0);
    }

    // The pipeline’s latency contribution (ms) Add at
    // least 11ms for image capture latency.
    public static final double IMAGE_CAPTURE_LATENCY = 11;

    public static double getLatencyMs() {
        // Add Image Capture Latency to get more accurate result
        return Latency.getDouble(0) + IMAGE_CAPTURE_LATENCY;
    }

    // Pixel information returned from these functions
    public static final double MIN_SIDE_LENGTH = 0;
    public static final double MAX_SIDE_LENGTH = 320;

    // Sidelength of shortest side of the fitted bounding box (0 - 320 pixels)
    public static double getShortestSidelength() {
        return ShortestSideLength.getDouble(0);
    }

    // Sidelength of longest side of the fitted bounding box (0 - 320 pixels)
    public static double getLongestSidelength() {
        return LongestSideLength.getDouble(0);
    }

    // Horizontal sidelength of the rough bounding box (0 - 320 pixels)
    public static double getHorizontalSidelength() {
        return HorizontalSideLength.getDouble(0);
    }

    // Vertical sidelength of the rough bounding box (0 - 320 pixels)
    public static double getVerticalSidelength() {
        return VerticalSideLength.getDouble(0);
    }

    /* Advanced Usage with Raw Contours (Not sent by default) */
    // Raw Contours are formatted as tx0, ty0, tx1, ty1, tx2, ty2
    // So to make this easier, you pass an int and it formats it

    public static double getRawTargetXOffset(int Target) {
        return table.getDouble("tx" + Integer.toString(Target));
    }

    public static double getRawTargetYOffset(int Target) {
        return table.getDouble("ty" + Integer.toString(Target));
    }

    public static double getRawTargetArea(int Target) {
        // Lime light returns a double from 0 - 100
        // Divide by 100 to scale number from 0 - 1
        return Math.min(table.getDouble("ta" + Integer.toString(Target)) / 100.0, 1);
    }

    public static double getRawTargetSkew(int Target) {
        return table.getDouble("ts" + Integer.toString(Target));
    }

    public static double getRawCrosshairX(int crosshair) {
        return table.getDouble("cx" + Integer.toString(crosshair));
    }

    public static double getRawCrosshairY(int crosshair) {
        return table.getDouble("cy" + Integer.toString(crosshair));
    }

    /* Custom Grip Values */
    // Return data given by custom GRIP pipeline
    public static double getCustomDouble(String Element) {
        return table.getDouble(Element);
    }

    public static boolean setCustomDouble(String Element, Number Value) {
        return table.setNumber(Element, Value);
    }

    public static String getCustomString(String Element) {
        return table.getString(Element);
    }

    public static boolean setCustomString(String Element, String Value) {
        return table.setString(Element, Value);
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

    public static void setLEDMode(LEDMode mode) {
        table.setNumber("ledMode", mode.getCodeValue());
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

    public static void setCamMode(CamMode mode) {
        table.setNumber("camMode", mode.getCodeValue());
    }

    // PIPELINE
    public static void setPipeline(int pipeline) {
        // Prevent input of invalid pipelines
        if (pipeline >= 0 && pipeline <= 9) {
            table.setNumber("pipeline", pipeline);
        }
    }

    // STREAM
    public enum CameraStream { // PIP = Picture-In-Picture
        STANDARD(0), PIP_MAIN(1), PIP_SECONDARY(2);

        CameraStream(int value) {
            this.val = value;
        }

        public int getCodeValue() {
            return val;
        }

        private int val;
    };

    public static void setStream(CameraStream stream) {
        table.setNumber("stream", stream.getCodeValue());
    }

    // SNAPSHOT_MODE
    public enum SNAPSHOT_MODE {
        STOP(0), // Don't take snapshots
        TAKE_TWO_PER_SECOND(1); 

        SNAPSHOT_MODE(int value) {
            this.val = value;
        }

        public int getCodeValue() {
            return val;
        }

        private int val;
    };

    public static void setSnapshotMode(SNAPSHOT_MODE snapshot) {
        table.setNumber("snapshot", snapshot.getCodeValue());
    }

    /* Math using limelight values */
    // Calculate Distance using TY
    public static double getTargetDistance(double heightFromCamera, double cameraAngle) {
        return heightFromCamera / Math.tan(Math.toRadians(getTargetYOffset() + cameraAngle));
    }

    // Coordinates of limelight relative to the center of the robot
    // This is necessary to make MP easier
    public static final double LIMELIGHT_X_POS = 0;
    public static final double LIMELIGHT_Y_POS = 0;

    public static Vector2d getTargetCoordinates(double HeightFromCamera, double CameraAngle) {
        final double DISTANCE = getTargetDistance(HeightFromCamera, CameraAngle);
        final double XOFFSET = Math.toRadians(getTargetXOffset());
        return new Vector2d(DISTANCE * Math.sin(XOFFSET) + LIMELIGHT_X_POS,
                DISTANCE * Math.cos(XOFFSET) + LIMELIGHT_Y_POS);
    }
}