/* Lime Light Docs: http://docs.limelightvision.io/en/latest/networktables_api.html# */

package frc.util;

import frc.util.NetworkTableClient;
import edu.wpi.first.wpilibj.drive.Vector2d; // Returning Goal Cordinates

public class LimeLight {
    // Network Table used to contact Lime Light
    private static NetworkTableClient table = new NetworkTableClient("limelight");

    /* “Best? Contour information */
    // Whether the limelight has any valid targets (0 or 1)
    public static boolean hasValidTarget() {
        // == 1 converts double to boolean
        return table.getDouble("tv") == 1;
    }

    // Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    public static final double MIN_X_OFFSET = -27;
    public static final double MAX_X_OFFSET = 27;

    public static double getTargetXOffset() {
        return table.getDouble("tx");
    }

    // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    public static final double MIN_Y_OFFSET = -20.5;
    public static final double MAX_Y_OFFSET = 20.5;

    public static double getTargetYOffset() {
        return table.getDouble("ty");
    }

    // Calculate Distance using TY
    public static double getTargetDistance(double HeightFromCamera, double CameraAngle) {
        return HeightFromCamera / Math.tan(Math.toRadians(getTargetYOffset() + CameraAngle));
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

    // Target Area (0% of image to 100% of image)
    public static final double MIN_TARGET_AREA = 0;
    public static final double MAX_TARGET_AREA = 1;

    public static double getTargetArea() {
        // Lime light returns a double from 0 - 100
        // Divide by 100 to scale number from 0 - 1
        return table.getDouble("ta") / 100.0;
    }

    // Skew or rotation (-90 degrees to 0 degrees)
    public static final double MIN_SKEW = -90;
    public static final double MAX_SKEW = 0;

    public static double getTargetSkew() {
        return table.getDouble("ts");
    }

    // The pipeline’s latency contribution (ms) Add at
    // least 11ms for image capture latency.
    public static final double IMAGE_CAPTURE_LATENCY = 11;

    public static double getLatency() {
        // Add Image Capture Latency to
        // get more accurate result
        return table.getDouble("tl") + IMAGE_CAPTURE_LATENCY;
    }

    // Sidelength of shortest side of the fitted bounding box (pixels)
    public static double getShortestSidelength() {
        return table.getDouble("tshort");
    }

    // Sidelength of longest side of the fitted bounding box (pixels)
    public static double getLongestSidelength() {
        return table.getDouble("tlong");
    }

    // Horizontal sidelength of the rough bounding box (0 - 320 pixels)
    public static final double MIN_HORIZ_SIDE_LENGTH = 0;
    public static final double MAX_HORIZ_SIDE_LENGTH = 320;

    public static double getHorizontalSidelength() {
        return table.getDouble("thoriz");
    }

    // Vertical sidelength of the rough bounding box (0 - 320 pixels)
    public static final double MIN_VIRT_SIDE_LENGTH = 0;
    public static final double MAX_VIRT_SIDE_LENGTH = 320;

    public static double getVerticalSidelength() {
        return table.getDouble("tvert");
    }

    // Return data given by custom GRIP pipeline
    public static double getCustomDouble(String Element) {
        return table.getDouble(Element);
    }

    // Return data given by custom GRIP pipeline
    public static String getCustomString(String Element) {
        return table.getString(Element);
    }

    /* Camera Controls (Use Enums to prevent invalid inputs) */
    // LED_MODE
    public enum LED_MODE {
        PIPELINE(0), // Use LED mode set in pipeline
        FORCE_OFF(1), // Force LEDs off
        FORCE_BLINK(2), // Force LEDs to blink
        FORCE_ON(3); // Force LEDs on

        LED_MODE(int value) {
            this.val = value;
        }

        public int getCodeValue() {
            return val;
        }

        private int val;
    };

    public static void setLEDMode(LED_MODE mode) {
        table.setNumber("ledMode", mode.getCodeValue());
    }

    // CAM_MODE
    public enum CAM_MODE {
        VISION(0), // Use limelight for CV
        DRIVER(1); // Use limelight for driving (this is dumb, dont do this)

        CAM_MODE(int value) {
            this.val = value;
        }

        public int getCodeValue() {
            return val;
        }

        private int val;
    };

    public static void setCamMode(CAM_MODE mode) {
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
    public enum STREAM { // PIP = Picture-In-Picture
        STANDARD(0), PIP_MAIN(1), PIP_SECONDARY(2);

        STREAM(int value) {
            this.val = value;
        }

        public int getCodeValue() {
            return val;
        }

        private int val;
    };

    public static void setStream(STREAM stream) {
        table.setNumber("stream", stream.getCodeValue());
    }

    // SNAPSHOT_MODE
    public enum SNAPSHOT_MODE {
        STOP(0), // Don't take snapshots
        TAKE_TWO_PER_SECOND(1); // Take two snapshots per second

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

    /* Advanced Usage with Raw Contours */
    /* Raw Targets */

    // Raw Contours are formatted as tx0, ty0, tx1, ty1, tx2, ty2
    // So to make this easier, you pass an int and it formats it

    // Raw Screenspace X
    public static double getTargetXOffset(int Target) {
        return table.getDouble("tx" + Integer.toString(Target));
    }

    // Raw Screenspace Y
    public static double getTargetYOffset(int Target) {
        return table.getDouble("ty" + Integer.toString(Target));
    }

    // Area (0% of image to 100% of image)
    public static double getTargetArea(int Target) {
        // Lime light returns a double from 0 - 100
        // Divide by 100 to scale number from 0 - 1
        return table.getDouble("ta" + Integer.toString(Target)) / 100.0;
    }

    // Skew or rotation (-90 degrees to 0 degrees)
    public static double getTargetSkew(int Target) {
        return table.getDouble("ts" + Integer.toString(Target));
    }

    /* Raw Crosshairs */
    // Crosshair A X in normalized screen space
    public static double getCrosshairX(int crosshair) {
        return table.getDouble("cx" + Integer.toString(crosshair));
    }

    // Crosshair A Y in normalized screen space
    public static double getCrosshairY(int crosshair) {
        return table.getDouble("cy" + Integer.toString(crosshair));
    }
}