package com.example.http;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UploadHelper {

    private volatile static UploadHelper mInstance;

    private static Map<String, RequestBody> params;

    private UploadHelper() {
    }

    public static UploadHelper getInstance() {
        if (mInstance == null) {
            synchronized (UploadHelper.class) {
                if (mInstance == null) {
                    mInstance = new UploadHelper();
                    params = new HashMap<>();
                }
            }
        }
        return mInstance;
    }

    public UploadHelper addParameter(String key, File o) {
        RequestBody body = null;
        body = RequestBody.create(MediaType.parse("multipart/form-data;charset=UTF-8"), o);
        params.put(key,body);
        return this;
    }

    public Map<String,RequestBody> builder(){
        return params;
    }

    public void clear(){
        params.clear();
    }

}
