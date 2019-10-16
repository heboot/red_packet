package com.zonghong.redpacket.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.http.HttpClient;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.LayoutDelFriendBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DelFriendDialog extends DialogFragment {

    private LayoutDelFriendBinding binding;

    private String uid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }

    public static DelFriendDialog newInstance(String uid) {
        Bundle args = new Bundle();
        args.putString(MKey.ID, uid);
        DelFriendDialog fragment = new DelFriendDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
//        Window window = getDialog().getWindow();
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.gravity = Gravity.CENTER;
//        window.setAttributes(params);
////        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
////        window.setWindowAnimations(R.style.BottomDialog_Animation);
//        //设置边距
//        DisplayMetrics dm = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_del_friend, container, false);
        binding = DataBindingUtil.bind(view);
        uid = getArguments().getString(MKey.ID);
        binding.clytContainer.setOnClickListener(v -> {
            dismiss();
        });
        binding.tvDel.setOnClickListener(v -> {
            delFriend(uid);
        });
        return binding.getRoot();
    }

    private void delFriend(String userId) {
        Map params = new HashMap<>();
        params.put("friend_id", userId);
        HttpClient.Builder.getServer().fDel(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                dismiss();
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                dismiss();
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }
        });
    }

}
