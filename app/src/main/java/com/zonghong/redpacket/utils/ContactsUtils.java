package com.zonghong.redpacket.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.model.ContactsModel;
import com.zonghong.redpacket.utils.ManufacturerSupportUtil;

import java.util.ArrayList;

public class ContactsUtils {


    /**
     * read contacts, {@link android.Manifest.permission#READ_CONTACTS}
     *
     * @param activity
     * @return true if success
     * @throws Exception
     */
    public static boolean checkReadContacts(Activity activity) throws Exception {
        Cursor cursor = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone
                .CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            if (ManufacturerSupportUtil.isForceManufacturer()) {
                if (isNumberIndexInfoIsNull(cursor, cursor.getColumnIndex(ContactsContract.CommonDataKinds
                        .Phone.NUMBER))) {
                    cursor.close();
                    return false;
                }
            }
            cursor.close();
            return true;
        } else {
            return false;
        }
    }


    /**
     * in {@link com.joker.api.support.manufacturer.XIAOMI}
     * 1.denied {@link android.Manifest.permission#READ_CONTACTS} permission
     * ---->cursor.getCount == 0
     * 2.granted {@link android.Manifest.permission#READ_CONTACTS} permission
     * ---->cursor.getCount return real count in contacts
     * <p>
     * so when there are no user or permission denied, it will return 0
     *
     * @param cursor
     * @param numberIndex
     * @return true if can not get info
     */
    private static boolean isNumberIndexInfoIsNull(Cursor cursor, int numberIndex) {
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                return TextUtils.isEmpty(cursor.getString(numberIndex));
            }
            return false;
        } else {
            return true;
        }
    }


    /**
     * 获取通讯录工具类
     * 返回的数据：
     * [{
     * "name": "xxx",
     * "note": "呵呵呵呵",
     * "phone": "13333333332"
     * },
     * {
     * "name": "yyy",
     * "phone": "13333333333"
     * },
     * {
     * "name": "zzz",
     * "phone": "13333333334"
     * },
     * ......
     * ]
     */

    public static ArrayList<ContactsModel> getAllContacts(Context context) {
        ArrayList<ContactsModel> contacts = new ArrayList<ContactsModel>();

        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            //新建一个联系人实例
            ContactsModel temp = new ContactsModel();
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            if(StringUtils.isEmpty(name)){
                temp.setPhoneName("#");
            }else{
                temp.setPhoneName(name);
            }

            //获取联系人电话号码
            Cursor phoneCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            while (phoneCursor.moveToNext()) {
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phone = phone.replace("-", "");
                phone = phone.replace(" ", "");
//                temp.phone = phone;
                temp.setPhone(phone);
            }

            //获取联系人备注信息
            Cursor noteCursor = context.getContentResolver().query(
                    ContactsContract.Data.CONTENT_URI,
                    new String[]{ContactsContract.Data._ID, ContactsContract.CommonDataKinds.Nickname.NAME},
                    ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE + "'",
                    new String[]{contactId}, null);

            contacts.add(temp);
            //记得要把cursor给close掉
            phoneCursor.close();
            noteCursor.close();
        }
        cursor.close();
        return contacts;
    }


}
