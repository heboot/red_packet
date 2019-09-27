package com.zonghong.redpacket.rong;

import android.os.Parcel;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.bean.MingPianBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "rongCellCard", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class MingPianMessage extends MessageContent {

    private String cardImg;

    private String cardId;

    private String cardName;

    private String accountId;


//    private String sum;

    //给消息赋值。
    public MingPianMessage(Parcel in) {
        cardImg = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
        //这里可继续增加你消息的属性
        cardId = ParcelUtils.readFromParcel(in);

        cardName = ParcelUtils.readFromParcel(in);

        accountId = ParcelUtils.readFromParcel(in);
    }

    public MingPianMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            MingPianBean jsonObj = JSON.parseObject(jsonStr, MingPianBean.class);

//            if (jsonObj.has("ID"))
//                ID = jsonObj.optString("ID");
            cardImg = String.valueOf(jsonObj.getCardImg());

            cardName = jsonObj.getCardName();

            cardId = jsonObj.getCardId();

            accountId = jsonObj.getAccountId();


        } catch (Exception e) {
        }
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("cardImg", cardImg);
            jsonObj.put("cardId", cardId);
            jsonObj.put("cardName", cardName);
            jsonObj.put("accountId", accountId);
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
    public static final Creator<MingPianMessage> CREATOR = new Creator<MingPianMessage>() {

        @Override
        public MingPianMessage createFromParcel(Parcel source) {
            return new MingPianMessage(source);
        }

        @Override
        public MingPianMessage[] newArray(int size) {
            return new MingPianMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        ParcelUtils.writeToParcel(dest, cardImg);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, cardId);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, cardName);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, accountId);//该类为工具类，对消息中属性进行序列化
    }
}
