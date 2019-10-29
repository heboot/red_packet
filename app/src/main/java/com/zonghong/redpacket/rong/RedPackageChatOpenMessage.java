package com.zonghong.redpacket.rong;

import android.os.Parcel;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.bean.RedpackageTipBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "rongCellNotification", flag = MessageTag.ISPERSISTED)
public class RedPackageChatOpenMessage extends MessageContent {

    private String message;

    private String targetId;

    private String redId;

    //给消息赋值。
    public RedPackageChatOpenMessage(Parcel in) {
        message = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
        //这里可继续增加你消息的属性
        targetId =  ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
        redId =  ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
    }

    public RedPackageChatOpenMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            RedpackageTipBean jsonObj = JSON.parseObject(jsonStr, RedpackageTipBean.class);

//            if (jsonObj.has("ID"))
//                ID = jsonObj.optString("ID");
            message = String.valueOf(jsonObj.getMessage());
            targetId =  String.valueOf(jsonObj.getTargetId());
            redId =  String.valueOf(jsonObj.getRedId());
        } catch (Exception e) {
        }
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("message", message);
            jsonObj.put("targetId", targetId);
            jsonObj.put("redId", redId);
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
    public static final Creator<RedPackageChatOpenMessage> CREATOR = new Creator<RedPackageChatOpenMessage>() {

        @Override
        public RedPackageChatOpenMessage createFromParcel(Parcel source) {
            return new RedPackageChatOpenMessage(source);
        }

        @Override
        public RedPackageChatOpenMessage[] newArray(int size) {
            return new RedPackageChatOpenMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        ParcelUtils.writeToParcel(dest, message);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, targetId);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, redId);//该类为工具类，对消息中属性进行序列化
    }
}
