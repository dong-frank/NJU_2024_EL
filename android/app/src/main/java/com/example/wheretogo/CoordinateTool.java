package com.example.wheretogo;

import com.baidu.lbsapi.tools.CoordinateConverter;
import com.baidu.lbsapi.tools.Point;


/**
 * 坐标工具类,删去测算两点距离功能
 */
public class CoordinateTool {
    private CoordinateTool() {
        throw new AssertionError("No instances.");
    }

    public Point GCJtoBD(Point point) {
        return CoordinateConverter.converter(CoordinateConverter.COOR_TYPE.COOR_TYPE_GCJ02,point);
    }

    public Point WGStoBD(Point point) {
        return CoordinateConverter.converter(CoordinateConverter.COOR_TYPE.COOR_TYPE_WGS84,point);
    }

    public static double calculateBearing(Point point1, Point point2) {
        double longitude1 = Math.toRadians(point1.x);
        double longitude2 = Math.toRadians(point2.x);
        double latitude1 = Math.toRadians(point1.y);
        double latitude2 = Math.toRadians(point2.y);

        double longDiff = longitude2 - longitude1;
        double y = Math.sin(longDiff) * Math.cos(latitude2);
        double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);

        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }

    public static String bearingToDirection(double bearing) {
        if (bearing >= 337.5 || bearing < 22.5) {
            return "北";
        } else if (bearing >= 22.5 && bearing < 67.5) {
            return "东北";
        } else if (bearing >= 67.5 && bearing < 112.5) {
            return "东";
        } else if (bearing >= 112.5 && bearing < 157.5) {
            return "东南";
        } else if (bearing >= 157.5 && bearing < 202.5) {
            return "南";
        } else if (bearing >= 202.5 && bearing < 247.5) {
            return "西南";
        } else if (bearing >= 247.5 && bearing < 292.5) {
            return "西";
        } else if (bearing >= 292.5 && bearing < 337.5) {
            return "西北";
        } else {
            return "";
        }
    }
}
