package com.zonghong.redpacket.view;

import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.http.HttpClient;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.bean.GetRedpackageBean;
import com.waw.hr.mutils.bean.GetRedpackageModel;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ChooseContactsActivity;
import com.zonghong.redpacket.activity.user.NewBankActivity;
import com.zonghong.redpacket.common.PayDialogType;
import com.zonghong.redpacket.databinding.DialogPaypwdBinding;
import com.zonghong.redpacket.databinding.DialogRedpackageGetBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imlib.model.Conversation;

public class PayDialog extends DialogFragment {

    private DialogPaypwdBinding binding;

    private PayDialogType payDialogType;

    private float money;


    public static PayDialog newInstance(PayDialogType type) {
        Bundle args = new Bundle();
        args.putSerializable(MKey.TYPE, type);
        args.putFloat(MKey.MONEY, 0);
        PayDialog fragment = new PayDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static PayDialog newInstance(PayDialogType type, float money) {
        Bundle args = new Bundle();
        args.putSerializable(MKey.TYPE, type);
        args.putFloat(MKey.MONEY, money);
        PayDialog fragment = new PayDialog();
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
        }

        binding.tvMoney.setText(money + "");

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
                    checkPayPwd();
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

    private void checkPayPwd() {
        Map params = new HashMap();
        params.put("payPass", binding.tvP1.getText().toString() + binding.tvP1.getText().toString() + binding.tvP1.getText().toString() + binding.tvP1.getText().toString() + binding.tvP1.getText().toString() + binding.tvP1.getText().toString());
        HttpClient.Builder.getServer().uVerPay(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());

                if (payDialogType == PayDialogType.CREATE_GROUP) {
                    EventBus.getDefault().postSticky(new UserEvent.PAY_SUC_EVENT());
                }

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
