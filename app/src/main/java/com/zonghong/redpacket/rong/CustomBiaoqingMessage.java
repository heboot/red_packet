package com.zonghong.redpacket.rong;

import android.os.Parcel;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "redpackagetip", flag = MessageTag.ISPERSISTED)
public class CustomBiaoqingMessage extends MessageContent {

    private String name;

//    private String sum;

    //给消息赋值。
    public CustomBiaoqingMessage(Parcel in) {
        name = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
        //这里可继续增加你消息的属性
    }

    public CustomBiaoqingMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            CreateRedPackageChildBean jsonObj = JSON.parseObject(jsonStr, CreateRedPackageChildBean.class);

//            if (jsonObj.has("ID"))
//                ID = jsonObj.optString("ID");
            name = String.valueOf(jsonObj.getID());


        } catch (Exception e) {
        }
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("ID", name);
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }
        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return null;

    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<CustomBiaoqingMessage> CREATOR = new Creator<CustomBiaoqingMessage>() {

        @Override
        public CustomBiaoqingMessage createFromParcel(Parcel source) {
            return new CustomBiaoqingMessage(source);
        }

        @Override
        public CustomBiaoqingMessage[] newArray(int size) {
            return new CustomBiaoqingMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        ParcelUtils.writeToParcel(dest, name);//该类为工具类，对消息中属性进行序列化
    }
}
