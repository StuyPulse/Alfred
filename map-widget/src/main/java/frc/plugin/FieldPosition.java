package frc.plugin;

import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

public final class FieldPosition extends ComplexData<FieldPosition> {

    private double[] pos = new double[2];
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
        LEFT_CS(new FieldPosition(-1, -1, -1)),
        RIGHT_CS(new FieldPosition(-1, -1, -1)),
        MIDDLE(new FieldPosition(-1, -1, -1)),
        LEFT_R(new FieldPosition(-1, -1, -1)),
        RIGHT_R(new FieldPosition(-1, -1, -1));

        double x;
        double y;
        double angle;

        StartingPosition(FieldPosition pos) {
            x = pos.getX();
            y = pos.getY();
            angle = pos.getAngle();
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

    public FieldPosition(double x, double y, double gyroAngle) {
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
