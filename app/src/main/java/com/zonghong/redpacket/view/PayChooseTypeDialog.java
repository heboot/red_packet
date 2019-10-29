package com.zonghong.redpacket.view;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.http.HttpClient;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.common.PayDialogType;
import com.zonghong.redpacket.databinding.DialogPaypwdBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.NumberUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PayChooseTypeDialog extends DialogFragment {

    private DialogPaypwdBinding binding;

    private PayDialogType payDialogType;

    private float money;

    private String toId;

    private String bankId, nick;


//    public static PayDialog newInstance(PayDialogType type) {
//        Bundle args = new Bundle();
//        args.putSerializable(MKey.TYPE, type);
//        args.putFloat(MKey.MONEY, 0);
//        PayDialog fragment = new PayDialog();
//        fragment.setArguments(args);
//        return fragment;
//    }

    public static PayChooseTypeDialog newInstance(PayDialogType type, float money, String bankId) {
        Bundle args = new Bundle();
        args.putSerializable(MKey.TYPE, type);
        args.putFloat(MKey.MONEY, money);
        args.putString(MKey.ID, bankId);
        PayChooseTypeDialog fragment = new PayChooseTypeDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static PayChooseTypeDialog newInstance(PayDialogType type, float money, String toId, String bankId, String nick) {
        Bundle args = new Bundle();
        args.putSerializable(MKey.TYPE, type);
        args.putFloat(MKey.MONEY, money);
        args.putString(MKey.USER_ID, toId);
        args.putString(MKey.ID, bankId);
        args.putString(MKey.NICKNAME, nick);
        PayChooseTypeDialog fragment = new PayChooseTypeDialog();
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
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
//        window.setWindowAnimations(R.style.BottomDialog_Animation);
        //设置边距
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_paypwd, container, false);
        binding = DataBindingUtil.bind(view);

        payDialogType = (PayDialogType) getArguments().get(MKey.TYPE);

        money = getArguments().getFloat(MKey.MONEY);

        bankId = getArguments().getString(MKey.ID);

        toId = getArguments().getString(MKey.USER_ID);

        nick = getArguments().getString(MKey.NICKNAME);

        switch (payDialogType) {
            case CASH:
                binding.tvType.setText("提现");
                break;
            case RECHARGE:
                binding.tvType.setText("充值");
                break;
            case CREATE_GROUP:
                binding.tvType.setText("创建群组");
                break;
            case ZHUANZHUANG:
                binding.tvType.setText("转账");
                break;
        }

        binding.tvMoney.setText(NumberUtils.formatDouble(Double.parseDouble(money+"")) + "");

        QMUIKeyboardHelper.showKeyboard(binding.etPwd, false);
        binding.vClose.setOnClickListener((v) -> {
            dismiss();
        });

        binding.etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) {
                    binding.tvP1.setText(charSequence);
                    binding.tvP2.setText("");
                    binding.tvP3.setText("");
                    binding.tvP4.setText("");
                    binding.tvP5.setText("");
                    binding.tvP6.setText("");
                } else if (charSequence.length() == 2) {
                    binding.tvP2.setText(charSequence.subSequence(1, charSequence.length()));
                    binding.tvP3.setText("");
                    binding.tvP4.setText("");
                    binding.tvP5.setText("");
                    binding.tvP6.setText("");
                } else if (charSequence.length() == 3) {
                    binding.tvP3.setText(charSequence.subSequence(2, charSequence.length()));
                    binding.tvP4.setText("");
                    binding.tvP5.setText("");
                    binding.tvP6.setText("");
                } else if (charSequence.length() == 4) {
                    binding.tvP4.setText(charSequence.subSequence(3, charSequence.length()));
                    binding.tvP5.setText("");
                    binding.tvP6.setText("");
                } else if (charSequence.length() == 5) {
                    binding.tvP5.setText(charSequence.subSequence(4, charSequence.length()));
                    binding.tvP6.setText("");
                } else if (charSequence.length() == 6) {
                    QMUIKeyboardHelper.hideKeyboard(binding.etPwd);
                    binding.tvP6.setText(charSequence.subSequence(5, charSequence.length()));
                    checkPayPwd(String.valueOf(money), bankId);
                } else {

                    binding.tvP1.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });


        return view;
    }

    private void checkPayPwd(String money, String bankId) {
        Map params = new HashMap();
        params.put("payPass", binding.tvP1.getText().toString() + binding.tvP2.getText().toString() + binding.tvP3.getText().toString() + binding.tvP4.getText().toString() + binding.tvP5.getText().toString() + binding.tvP6.getText().toString());
        HttpClient.Builder.getServer().uVerPay(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                if (payDialogType == PayDialogType.CREATE_GROUP) {
                    dismiss();
                    EventBus.getDefault().postSticky(new UserEvent.PAY_SUC_EVENT());
                } else if (payDialogType == PayDialogType.RECHARGE) {
                    bRechar(money, bankId);
                } else if (payDialogType == PayDialogType.CASH) {
                    bEmbody(money, bankId);
                } else if (payDialogType == PayDialogType.ZHUANZHUANG) {
                    tTransfer(toId, money, bankId);
                }

            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }
        });
    }

    private void tTransfer(String toId, String money, String bankId) {
        Map params = new HashMap();
        params.put("sum", money);
        params.put("get_user", toId);
        params.put("bank_id", bankId);


        HttpClient.Builder.getServer().tCreate(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<CreateRedPackageChildBean>() {
            @Override
            public void onSuccess(BaseBean<CreateRedPackageChildBean> baseBean) {
//                baseBean.getData().setUser_id(toId);
//                baseBean.getData().setFrom_id(String.valueOf(UserService.getInstance().getUserId()));
//                baseBean.getData().setUserContent("转账给" + nick);
//                baseBean.getData().setNoUserContent("收到" + UserService.getInstance().getUserInfoBean().getNick_name() + "的转账");
//                baseBean.getData().setMoney(money);
//                RongUtils.sendZhuanzhuangRedPackageMessage(baseBean.getData());
//                EventBus.getDefault().postSticky(new UserEvent.CASH_SUC_EVENT());
            }

            @Override
            public void onError(BaseBean<CreateRedPackageChildBean> baseBean) {
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }
        });
    }

    public void bRechar(String money, String bankId) {
        Map params = new HashMap();
        params.put("money", money);
        params.put("bank_id", bankId);
        HttpClient.Builder.getServer().bRechar(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                dismiss();
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
                EventBus.getDefault().postSticky(new UserEvent.RECHARGE_SUC_EVENT());
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }
        });
    }

    private void bEmbody(String money, String bankId) {
        Map params = new HashMap();
        params.put("money", money);
        params.put("bank_id", bankId);
        HttpClient.Builder.getServer().bEmbody(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                dismiss();
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
                EventBus.getDefault().postSticky(new UserEvent.CASH_SUC_EVENT());
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }
        });
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


}
