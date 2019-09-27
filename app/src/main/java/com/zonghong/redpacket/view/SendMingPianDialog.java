package com.zonghong.redpacket.view;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.bean.GroupDetaiInfoBean;
import com.waw.hr.mutils.bean.MingPianBean;
import com.waw.hr.mutils.bean.UserInfoBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.common.SendMingPianType;
import com.zonghong.redpacket.databinding.DialogSendMingpianBinding;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.utils.ImageUtils;

public class SendMingPianDialog extends DialogFragment {

    private DialogSendMingpianBinding binding;

    private UserInfoBean userInfoBean;

    private GroupDetaiInfoBean groupDetaiInfoBean;

    private String toName, toId;

    private SendMingPianType type;

    public static SendMingPianDialog newInstance(SendMingPianType sendMingPianType, UserInfoBean userInfoBean, GroupDetaiInfoBean groupDetaiInfoBean, String toName, String toId) {
        Bundle args = new Bundle();
        args.putSerializable(MKey.GET_USER, userInfoBean);
        args.putSerializable(MKey.GROUP, groupDetaiInfoBean);
        args.putSerializable(MKey.TYPE, sendMingPianType);
        args.putSerializable(MKey.NAME, toName);
        args.putSerializable(MKey.ID, toId);
        SendMingPianDialog fragment = new SendMingPianDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        window.setWindowAnimations(R.style.BottomDialog_Animation);
        //设置边距
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_send_mingpian, container, false);
        binding = DataBindingUtil.bind(view);

        userInfoBean = (UserInfoBean) getArguments().get(MKey.GET_USER);
        groupDetaiInfoBean = (GroupDetaiInfoBean) getArguments().get(MKey.GROUP);
        toName = (String) getArguments().get(MKey.NAME);
        toId = (String) getArguments().get(MKey.ID);
        type = (SendMingPianType) getArguments().get(MKey.TYPE);

        binding.tvName.setText(userInfoBean.getNick_name());

        binding.tvMingpian.setText("[个人名片]" + toName);
        if (type == SendMingPianType.USER) {
            ImageUtils.showAvatar(userInfoBean.getImg(), binding.ivAvatar);
        } else if (groupDetaiInfoBean != null) {
            ImageUtils.showAvatar(groupDetaiInfoBean.getGroupInfo().getImg(), binding.ivAvatar);
        }

        binding.tvCancel.setOnClickListener(v1 -> {
            dismiss();
        });

        binding.tvConfirm.setOnClickListener(v2 -> {
            newgroup();
            dismiss();
        });

        return view;
    }

    private void newgroup() {
        MingPianBean mingPianBean = new MingPianBean();
        mingPianBean.setAccountId(userInfoBean.getAccount_id());
        mingPianBean.setCardId(userInfoBean.getID() + "");
        mingPianBean.setCardImg(userInfoBean.getImg());
        mingPianBean.setCardName(userInfoBean.getNick_name());
        RongUtils.sendMingPianMessage(type, toId, mingPianBean);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


}
