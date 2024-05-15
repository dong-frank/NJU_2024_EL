package com.example.wheretogo

import com.baidu.lbsapi.tools.CoordinateConverter
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.GeoCodeOption
import com.baidu.mapapi.search.geocode.GeoCodeResult
import com.baidu.mapapi.search.geocode.GeoCoder
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult


object CoordinateTool {
    //TODO: 输入地名转换为相关坐标
    fun nameToPid(name: String): Point {
        var mCoder = GeoCoder.newInstance()
        var lat = 0.0
        var lon = 0.0
        var listener = object : OnGetGeoCoderResultListener{
            override fun onGetGeoCodeResult(geoCodeResult: GeoCodeResult) {
                if (null!=geoCodeResult && null != geoCodeResult.getLocation()){
                    if(geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR){
                        //没有检索到结果
                        return;
                    }else{
                        //获取地理编码结果
                        lat = geoCodeResult.location.latitude
                        lon = geoCodeResult.location.longitude
                        return
                    }

                }
            }

            override fun onGetReverseGeoCodeResult(p0: ReverseGeoCodeResult?) {
                if (p0  == null || p0.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    return;
                } else {
                    //详细地址
                    var address = p0.getAddress()
                    //行政区号
                    var adCode = p0.getAdcode()
                }
            }
        }
        mCoder.setOnGetGeoCodeResultListener(listener)
        mCoder.geocode(
            GeoCodeOption()
                .city(name.split(" ")[0])
                .address(name.split(" ")[1])
        )
        mCoder.destroy()
        return Point(lat, lon)
    }

    fun wsgToBd(point: Point): com.baidu.lbsapi.tools.Point? {
        return CoordinateConverter.converter(CoordinateConverter.COOR_TYPE.COOR_TYPE_WGS84,point)
    }

    fun gcjToBd(point: Point): com.baidu.lbsapi.tools.Point? {
        return CoordinateConverter.converter(CoordinateConverter.COOR_TYPE.COOR_TYPE_GCJ02,point)
    }


}