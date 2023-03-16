package com.example.astronomypicdaily;

public interface OnFetchDataListener {

    void onFetchData(String copyright, String date, String explanation, String hdurl, String media_type, String service_version, String title, String url,String message);

    void onError(String message);

}
