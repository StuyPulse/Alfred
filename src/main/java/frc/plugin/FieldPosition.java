package frc.plugin;

import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

public final class FieldPosition extends ComplexData<FieldPosition> {

    private double[] pos = new double[2];

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> zzz = new HashMap<>();
        zzz.put("x", pos[0]);
        zzz.put("y", pos[1]);
        return zzz;
    }

    public enum StartingPosition {
        LEFT(-1, -1),
        RIGHT(-1, -1),
        MIDDLE(-1, -1);

        double x;
        double y;

        StartingPosition(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double[] getCoordinates() {
            return new double[]{x, y};
        }
    }

    public FieldPosition(StartingPosition prevPos) {
        this.pos = prevPos.getCoordinates();
    }

    public FieldPosition(double x, double y) {
        pos[0] = x;
        pos[1] = y;
    }

    public FieldPosition setX(double x) {
        return new FieldPosition(x, pos[1]);
    }

    public FieldPosition setY(double y) {
        return new FieldPosition(pos[0], y);
    }

}
