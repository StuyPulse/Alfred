package frc.util;

import frc.util.Vector2D;

public class MPPathFinder {

    /*
     * Preportion is used to get what part of the curve the robot is on.
     * This is on a scale of 0 - 1, and it is stored as a double
     */

    public static class HelperFunctions {
        // Weighted sum of points
        public static Vector2D average(Vector2D a, Vector2D b, double preportion) {
            Vector2D out = new Vector2D();
            out.x = a.x * preportion + b.x * (1.0 - preportion);
            out.y = a.y * preportion + b.y * (1.0 - preportion);
            
            return out;
        }

        // Weighted sum of points, and then weighted sum of those points
        public static Vector2D getPosition(Vector2D[] points, double preportion) {
            Vector2D[] lines = points;
            
            while(lines.length > 1) {
                Vector2D[] newLines = new Vector2D[points.length - 1];
                for(int i = 0; i < newLines.length ; ++i) {
                    newLines[i] = average(lines[i], lines[i+1], preportion);
                }

                lines = newLines;
            }
    
            return lines[0];
        }
    }

    public static Vector2D[] getPath(Vector2D[] points, int accuracy) {
        Vector2D[] path = new Vector2D[accuracy+1];

        path[0] = points[1];
        for(int i = 1; i < accuracy; ++i) {
            path[i] = HelperFunctions.getPosition(points, ((double)i)/((double)accuracy));
        }

        path[accuracy] = points[points.length - 1];

        return path;
    }
}