package frc.util;

public class Vector2D {
    public double x;
    public double y;

    Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    Vector2D(double[] in) {
        this.x = in[0];
        this.y = in[1];
    }

    Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double[] getArray() {
        return new double[]{x, y};
    }

    public boolean equals(Vector2D other) {
        return this.x == other.x && this.y == other.y;
    }

    public double distance(Vector2D other) {
        double xDis = x - other.x;
        double yDis = y - other.y;
        xDis *= xDis; yDis *= yDis;
        return Math.sqrt(xDis + yDis);
    }

    public double distance() {
        return distance(new Vector2D(0, 0));
    }

    public Vector2D rotate(double angdeg, Vector2D origin) {
        final double radians = Math.toRadians(angdeg);
        final double sin = Math.sin(radians);
        final double cos = Math.cos(radians);
        
        final Vector2D point = this.sub(origin); // center the point around origin
        Vector2D out = new Vector2D(); // make output variable

        out.x = point.x * cos - point.y * sin; // Rotate coords
        out.y = point.y * cos + point.x * sin;

        // reposition point
        return out.add(origin);
    }

    public Vector2D rotate(double angdeg) {
        return rotate(angdeg, new Vector2D(0,0));
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D sub(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D mul(Vector2D other) {
        return new Vector2D(this.x * other.x, this.y * other.y);
    }

    public Vector2D mul(double other) {
        return new Vector2D(this.x * other, this.y * other);
    }

    public Vector2D div(Vector2D other) {
        return new Vector2D(this.x / other.x, this.y / other.y);
    }

    public Vector2D div(double other) {
        return new Vector2D(this.x / other, this.y / other);
    }
}