//TODO: MAKE THIS IN KOTLIN SO THAT IT'S SHORTER & HOPEFULLY LESS JANK
package frc.plugin;

import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

public final class FieldPosition extends ComplexData<FieldPosition> {

    private double[] pos;
    private double startAngle;
    private double angle;

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> zzz = new HashMap<>();
        zzz.put("x", pos[0]);
        zzz.put("y", pos[1]);
        zzz.put("angle", angle);
        return zzz;
    }

    public enum StartingPosition {
        LEFT_CS(new double[] {73, 188, 90}),
        RIGHT_CS(new double[] {0, 0, 0}),
        MIDDLE(new double[] {-1, -1, -1}),
        LEFT_R(new double[] {-1, -1, -1}),
        RIGHT_R(new double[] {-1, -1, -1});

        final double x;
        final double y;
        final double angle;

        StartingPosition(double[] pos) {
            x = pos[0];
            y = pos[1];
            angle = pos[2];
        }

        public double[] getCoordinates() {
            return new double[]{x, y};
        }
    }

    public FieldPosition(StartingPosition prevPos, double gyroAngle) {
        this.pos = prevPos.getCoordinates();
        startAngle = gyroAngle;
        angle = 0;
    }

    public void update(double x, double y, double gyroAngle) {
        pos[0] = x;
        pos[1] = y;
        angle = gyroAngle - startAngle;
    }

    public double getX() {
        return pos[0];
    }

    public double getY() {
        return pos[1];
    }

    public double getAngle() {
        return angle;
    }

}
