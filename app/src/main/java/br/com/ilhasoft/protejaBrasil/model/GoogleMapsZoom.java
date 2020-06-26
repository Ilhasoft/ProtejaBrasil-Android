package br.com.ilhasoft.protejaBrasil.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dielson Sales on 25/02/16.
 */
public class GoogleMapsZoom {

    public static int FURTHEST_ZOOM =  1;
    public static int CLOSEST_ZOOM = 19;

    private static List<Double> zoomLevels;

    static {
        zoomLevels = new ArrayList<>();
        // (zoomLevel, meters/pixel)
        zoomLevels.add(0, 0.0);
        zoomLevels.add(1, 21282.0);
        zoomLevels.add(2, 16355.0);
        zoomLevels.add(3, 10064.0);
        zoomLevels.add(4, 5540.0);
        zoomLevels.add(5, 2909.0);
        zoomLevels.add(6, 1485.0);
        zoomLevels.add(7, 752.0);
        zoomLevels.add(8, 378.0);
        zoomLevels.add(9, 190.0);
        zoomLevels.add(10, 95.0);
        zoomLevels.add(11, 48.0);
        zoomLevels.add(12, 24.0);
        zoomLevels.add(13, 12.0);
        zoomLevels.add(14, 6.0);
        zoomLevels.add(15, 3.0);
        zoomLevels.add(16, 1.48);
        zoomLevels.add(17, 0.74);
        zoomLevels.add(18, 0.37);
        zoomLevels.add(19, 0.19);
    }

    /**
     * Calculates the best zoom level to display two points in Google Maps that as close or further
     * than closestAllowedZoom.
     * @param radius the distance between the two points
     * @param mapWidthInPixels the width of the map view
     * @param closestAllowedZoom the closer zoom that the camera is allowed to get
     * @return the best zoom level to display both points
     */
    public static int calculateZoomLevelForDistance(double radius, double mapWidthInPixels, int closestAllowedZoom) {
        double diameter = radius * 2;
        int zoom = CLOSEST_ZOOM;
        double totalMeters;
        // Gets farther every iteration
        while (--zoom >= FURTHEST_ZOOM) {
            totalMeters = getMetersPerPixel(zoom) * mapWidthInPixels;
            if (diameter < totalMeters) {
                break;
            }
        }
        if (zoom > closestAllowedZoom) {
            return closestAllowedZoom;
        }
        return zoom;
    }

    private static double getMetersPerPixel(int zoom) {
        return zoomLevels.get(zoom);
    }
}
