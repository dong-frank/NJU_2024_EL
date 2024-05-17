package com.example.wheretogo;

import android.location.Geocoder;
import android.util.Log;

import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

/**
 * 全景工具类
 */
public class PanaTool {
    private PanaTool() {
        throw new AssertionError("No instances.");
    }

    public static void changePana(String city ,String address, PanoramaView panoramaView) {
        //TODO:地名转换为经纬度
        GeoCoder mCoder = null;
        mCoder = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                Log.i("PanaTool", "correctdd");
                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Log.i("PanaTool", "not find the location");
                } else {
                    Log.i("PanaTool", "find the location");
                    double latitude = geoCodeResult.getLocation().latitude;
                    double longitude = geoCodeResult.getLocation().longitude;
                    //TODO:经纬度转换为全景
                    panoramaView.setPanorama(longitude, latitude);
                    Log.i("PanaTool", "change the panorama");
                }
            }
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    return;
                } else {
                    //详细地址
                    String address = reverseGeoCodeResult.getAddress();
                    //行政区号
                    int adCode = reverseGeoCodeResult.getAdcode();
                }
            }

        };
        mCoder.setOnGetGeoCodeResultListener(listener);
        mCoder.geocode(new GeoCodeOption().city(city).address(address));
        mCoder.destroy();
    }

    public static void sugSearch(String city, String address , GameMapActivity activity) {
        SuggestionSearch msugSearch = SuggestionSearch.newInstance();
        OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener()
        {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                //处理sug检索结果
                activity.handleSuggestionResult(suggestionResult);
            }
        };
        msugSearch.setOnGetSuggestionResultListener(listener);
        msugSearch.requestSuggestion(new SuggestionSearchOption()
                .city(city)
                .keyword(address));
        msugSearch.destroy();
    }
}
