package com.zonghong.redpacket.rong;

import android.os.Parcel;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.model.ZhuanZhangModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "redpackageTransfer", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class RedPackageZhuanZhangChatMessage extends MessageContent {

    private String ID;

    private String fromId;

    private String userContent;

    private String notUserContent;

    private String money;

        private String sum;

    //给消息赋值。
    public RedPackageZhuanZhangChatMessage(Parcel in) {
        ID = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
        //这里可继续增加你消息的属性
        fromId = ParcelUtils.readFromParcel(in);

        userContent = ParcelUtils.readFromParcel(in);


        notUserContent = ParcelUtils.readFromParcel(in);

        money = ParcelUtils.readFromParcel(in);

        sum = ParcelUtils.readFromParcel(in);
    }

    public RedPackageZhuanZhangChatMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();

        }

        try {
            ZhuanZhangModel jsonObj = JSON.parseObject(jsonStr, ZhuanZhangModel.class);

            //            if (jsonObj.has("ID"))
            //                ID = jsonObj.optString("ID");
            ID = String.valueOf(jsonObj.getID());

            fromId = jsonObj.getFromId();

            userContent = jsonObj.getUserContent();

            notUserContent = jsonObj.getNotUserContent();

            money = jsonObj.getMoney();

            sum = jsonObj.getSum();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("ID", ID);
            jsonObj.put("fromId", fromId);
            jsonObj.put("userContent", userContent);
            jsonObj.put("notUserContent", notUserContent);
            jsonObj.put("money", money);
            jsonObj.put("sum", sum);
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
    public static final Creator<RedPackageZhuanZhangChatMessage> CREATOR = new Creator<RedPackageZhuanZhangChatMessage>() {

        @Override
        public RedPackageZhuanZhangChatMessage createFromParcel(Parcel source) {
            return new RedPackageZhuanZhangChatMessage(source);
        }

        @Override
        public RedPackageZhuanZhangChatMessage[] newArray(int size) {
            return new RedPackageZhuanZhangChatMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        ParcelUtils.writeToParcel(dest, ID);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, fromId);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, userContent);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, notUserContent);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, money);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, sum);//该类为工具类，对消息中属性进行序列化
    }
}
