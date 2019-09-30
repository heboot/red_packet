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

@MessageTag(value = "rongCellClear", flag = MessageTag.NONE)
public class DeleteGroupMessageEventMessage extends MessageContent {

    private String groupId;

    //给消息赋值。
    public DeleteGroupMessageEventMessage(Parcel in) {
        groupId = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性

    }

    public DeleteGroupMessageEventMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            Map jsonObj = JSON.parseObject(jsonStr, Map.class);

//            if (jsonObj.has("ID"))
//                ID = jsonObj.optString("ID");
            groupId = (String) jsonObj.get("groupId");

        } catch (Exception e) {
        }
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("groupId", groupId);
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
    public static final Creator<DeleteGroupMessageEventMessage> CREATOR = new Creator<DeleteGroupMessageEventMessage>() {

        @Override
        public DeleteGroupMessageEventMessage createFromParcel(Parcel source) {
            return new DeleteGroupMessageEventMessage(source);
        }

        @Override
        public DeleteGroupMessageEventMessage[] newArray(int size) {
            return new DeleteGroupMessageEventMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        ParcelUtils.writeToParcel(dest, groupId);//该类为工具类，对消息中属性进行序列化
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
