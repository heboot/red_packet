package com.zonghong.redpacket.rong;

import android.os.Parcel;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "RC:GroupNameMsg", flag = MessageTag.NONE)
public class UpdateGroupInfoEventMessage extends MessageContent {

    private String content;

    //给消息赋值。
    public UpdateGroupInfoEventMessage(Parcel in) {
        content = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性

    }

    public UpdateGroupInfoEventMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            Map jsonObj = JSON.parseObject(jsonStr, Map.class);

//            if (jsonObj.has("ID"))
//                ID = jsonObj.optString("ID");
            content = (String) jsonObj.get("content");

        } catch (Exception e) {
        }
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("content", content);
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
    public static final Creator<UpdateGroupInfoEventMessage> CREATOR = new Creator<UpdateGroupInfoEventMessage>() {

        @Override
        public UpdateGroupInfoEventMessage createFromParcel(Parcel source) {
            return new UpdateGroupInfoEventMessage(source);
        }

        @Override
        public UpdateGroupInfoEventMessage[] newArray(int size) {
            return new UpdateGroupInfoEventMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        ParcelUtils.writeToParcel(dest, content);//该类为工具类，对消息中属性进行序列化
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
