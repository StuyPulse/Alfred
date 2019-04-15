package frc.util;

import frc.util.Vector2D;

public class MPPathFinder {

    /*
     * Preportion is used to get what part of the curve the robot is on.
     * This is on a scale of 0 - 1, and it is stored as a double
     */

    public static class HelperFunctions {
        public static Vector2D average(Vector2D a, Vector2D b, double preportion) {
            Vector2D out = new Vector2D();
            out.x = a.x * preportion + b.x * (1.0 - preportion);
            out.y = a.y * preportion + b.y * (1.0 - preportion);
            
            return out;
        }

        public static Vector2D getPosition(Vector2D[] points, double preportion) {
            Vector2D[][] calcSpace = new Vector2D[points.length][points.length];
            calcSpace[0] = points;
            
            for(int i = 1; i < points.length; ++i) {
                for(int j = 0; j < points.length - i; ++j) {
                    calcSpace[i][j] = average(calcSpace[i-1][j], calcSpace[i-1][j+1], preportion);
                }
            }
    
            return calcSpace[points.length-1][0];
        }
    }

    public static Vector2D[] getPath(Vector2D[] points, int accuracy) {
        Vector2D[] path = new Vector2D[accuracy];

        for(int i = 0; i < accuracy; ++i) {
            path[i] = HelperFunctions.getPosition(points, ((double)i)/((double)accuracy-1));
        }

        return path;
    }
}