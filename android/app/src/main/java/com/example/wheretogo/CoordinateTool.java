package com.example.wheretogo;

import static com.baidu.mapapi.model.CoordUtil.getDistance;

import android.util.Log;

import com.baidu.lbsapi.tools.CoordinateConverter;
import com.baidu.lbsapi.tools.Point;


/**
 * 坐标工具类
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



}
