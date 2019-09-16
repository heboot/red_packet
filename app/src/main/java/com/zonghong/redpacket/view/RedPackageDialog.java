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
import android.widget.Toast;

import com.example.http.HttpClient;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.bean.GetRedpackageBean;
import com.waw.hr.mutils.bean.GetRedpackageModel;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.DialogRedpackageGetBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imlib.model.Conversation;

public class RedPackageDialog extends DialogFragment {

    private DialogRedpackageGetBinding binding;

    private String redId;

    private Conversation.ConversationType type;

    private String fromId;

    private CreateRedPackageChildBean createRedPackageChildBean;

    public static RedPackageDialog newInstance(String redId, Conversation.ConversationType type, String fromId, CreateRedPackageChildBean createRedPackageChildBean) {
        Bundle args = new Bundle();
        args.putSerializable(MKey.ID, redId);
        args.putSerializable(MKey.TYPE, type);
        args.putSerializable("fromId", fromId);
        args.putSerializable(MKey.DATA, createRedPackageChildBean);
        RedPackageDialog fragment = new RedPackageDialog();
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
        View view = inflater.inflate(R.layout.dialog_redpackage_get, container, false);
        binding = DataBindingUtil.bind(view);

        redId = getArguments().getString(MKey.ID);
        fromId = getArguments().getString("fromId");
        type = (Conversation.ConversationType) getArguments().get(MKey.TYPE);
        createRedPackageChildBean = (CreateRedPackageChildBean) getArguments().get(MKey.DATA);

        binding.tvTtt.setText(createRedPackageChildBean.getRedName() + "的红包");

        ImageUtils.showAvatar(createRedPackageChildBean.getImage(), binding.ivAvatar);

        binding.tvDesc.setText(createRedPackageChildBean.getDesc());

        binding.vOpen.setOnClickListener((v) -> {
            createRedpackage();
        });
        binding.vClose.setOnClickListener((v) -> {
            dismiss();
        });
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


    private void createRedpackage() {
        Map params = new HashMap<>();
        params.put("red_id", redId);
        HttpClient.Builder.getServer().tGetMoney(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                if (baseBean.getData() instanceof GetRedpackageBean) {
                    GetRedpackageModel getRedpackageModel = new GetRedpackageModel();
                    if (type == Conversation.ConversationType.GROUP) {
                        getRedpackageModel.setGroupId(fromId);
                    } else {
                        getRedpackageModel.setUserId(fromId);
                    }
                    RongUtils.sendRedPackageOpenMessage(getRedpackageModel);
                    IntentUtils.intent2RedPackageOpenActivity(String.valueOf(baseBean.getData()));
                    dismiss();
                }
//                GetRedpackageBean

            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                Toast.makeText(MAPP.mapp, baseBean.getMsg(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
