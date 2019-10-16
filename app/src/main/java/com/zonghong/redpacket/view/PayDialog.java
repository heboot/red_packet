package com.zonghong.redpacket.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
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
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.BankListBean;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.bean.GetRedpackageBean;
import com.waw.hr.mutils.bean.GetRedpackageModel;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ChooseContactsActivity;
import com.zonghong.redpacket.activity.redpackage.NewRedPackageActivity;
import com.zonghong.redpacket.activity.user.AlterPayPwdActivity;
import com.zonghong.redpacket.activity.user.MyBankActivity;
import com.zonghong.redpacket.activity.user.NewBankActivity;
import com.zonghong.redpacket.activity.user.RechargeCashActivity;
import com.zonghong.redpacket.activity.user.TransferAccountActivity;
import com.zonghong.redpacket.adapter.MyBankAdapter;
import com.zonghong.redpacket.adapter.PayChooseBankAdapter;
import com.zonghong.redpacket.common.PayDialogType;
import com.zonghong.redpacket.common.RedPackageType;
import com.zonghong.redpacket.databinding.DialogPaypwdBinding;
import com.zonghong.redpacket.databinding.DialogRedpackageGetBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imlib.model.Conversation;

public class PayDialog extends DialogFragment {

    private DialogPaypwdBinding binding;

    private PayDialogType payDialogType;

    private float money;

    private String toId;

    private String bankId, nick;

    private RedPackageType redPackageType;

    private String remark;

    private String num;

    private BankListBean currentBank;

//    public static PayDialog newInstance(PayDialogType type) {
//        Bundle args = new Bundle();
//        args.putSerializable(MKey.TYPE, type);
//        args.putFloat(MKey.MONEY, 0);
//        PayDialog fragment = new PayDialog();
//        fragment.setArguments(args);
//        return fragment;
//    }


    public static PayDialog newRedPackageInstance(PayDialogType type, float money, String num, String toId, RedPackageType toType, String remark) {
        Bundle args = new Bundle();
        args.putSerializable(MKey.TYPE, type);
        args.putFloat(MKey.MONEY, money);
        args.putString("num", num);
        args.putString(MKey.USER_ID, toId);
        args.putSerializable("toType", toType);
        args.putString("remark", remark);
        PayDialog fragment = new PayDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static PayDialog newInstance(PayDialogType type, float money, String bankId) {
        Bundle args = new Bundle();
        args.putSerializable(MKey.TYPE, type);
        args.putFloat(MKey.MONEY, money);
        args.putString(MKey.ID, bankId);
        PayDialog fragment = new PayDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static PayDialog newInstance(PayDialogType type, float money, String toId, String bankId, String nick) {
        Bundle args = new Bundle();
        args.putSerializable(MKey.TYPE, type);
        args.putFloat(MKey.MONEY, money);
        args.putString(MKey.USER_ID, toId);
        args.putString(MKey.ID, bankId);
        args.putString(MKey.NICKNAME, nick);
        PayDialog fragment = new PayDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
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

    private void setNoChooseView() {
        binding.rvList.setVisibility(View.GONE);
        binding.vClose.setVisibility(View.GONE);
        binding.tvPaytype.setVisibility(View.GONE);
        binding.tvPaytext.setVisibility(View.GONE);
        binding.ivClose.setVisibility(View.VISIBLE);


        binding.ivIcon.setVisibility(View.GONE);
        binding.tvTypeTitle.setVisibility(View.GONE);
    }

    private void setChooseView() {
        binding.tvTitle.setText("选择支付方式");
        binding.tvPaytype.setOnClickListener((v) -> {
            binding.rvList.setVisibility(View.VISIBLE);
            binding.vClose.setVisibility(View.GONE);
            binding.llytPwd.setVisibility(View.GONE);
            binding.vLine.setVisibility(View.GONE);
            binding.tvPaytype.setVisibility(View.GONE);
            binding.tvPaytext.setVisibility(View.GONE);
            binding.vBack.setVisibility(View.VISIBLE);
            binding.ivClose.setVisibility(View.VISIBLE);
        });
        binding.vBack.setOnClickListener((v) -> {
            binding.rvList.setVisibility(View.GONE);
            binding.vClose.setVisibility(View.VISIBLE);
            binding.llytPwd.setVisibility(View.VISIBLE);
            binding.vLine.setVisibility(View.VISIBLE);
            binding.tvPaytype.setVisibility(View.VISIBLE);
            binding.tvPaytext.setVisibility(View.VISIBLE);
            binding.vBack.setVisibility(View.GONE);
            binding.ivClose.setVisibility(View.GONE);
        });


        binding.rvList.setLayoutManager(new LinearLayoutManager(MAPP.mapp, LinearLayoutManager.VERTICAL, false));
        bankList();
    }

    private PayChooseBankAdapter myBankAdapter;

    private void bankList() {
        Map params = new HashMap<>();
        HttpClient.Builder.getServer().bankList(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<BankListBean>>() {
            @Override
            public void onSuccess(BaseBean<List<BankListBean>> baseBean) {
                List<BankListBean> bankListBeanList = new ArrayList<>();
                BankListBean bankListBean = new BankListBean();
                bankListBean.setOption("yue");
                if (myBankAdapter == null) {
                    if (baseBean.getData() != null) {
                        bankListBeanList.addAll(baseBean.getData());
                        bankListBeanList.add(0, bankListBean);
                        myBankAdapter = new PayChooseBankAdapter(bankListBeanList);
                        binding.rvList.setAdapter(myBankAdapter);
                    }
                } else {
                    bankListBeanList.addAll(baseBean.getData());
                    bankListBeanList.add(0, bankListBean);
                    myBankAdapter.getData().clear();
                    myBankAdapter.setNewData(bankListBeanList);
                    myBankAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(BaseBean<List<BankListBean>> baseBean) {
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_paypwd, container, false);
        binding = DataBindingUtil.bind(view);
        EventBus.getDefault().register(this);
        payDialogType = (PayDialogType) getArguments().get(MKey.TYPE);
        money = getArguments().getFloat(MKey.MONEY);
        toId = getArguments().getString(MKey.USER_ID);
        bankId = getArguments().getString(MKey.ID);
        if (payDialogType == PayDialogType.REDPACKAGE) {
            redPackageType = (RedPackageType) getArguments().get("toType");
            num = (String) getArguments().get("num");
            remark = (String) getArguments().get("remark");
        } else if (payDialogType == PayDialogType.ZHUANZHUANG) {
            nick = getArguments().getString(MKey.NICKNAME);
        }


        switch (payDialogType) {
            case CASH:
                binding.tvType.setText("提现");
                setNoChooseView();
                break;
            case RECHARGE:
                binding.tvType.setText("充值");
                setNoChooseView();
                break;
            case CREATE_GROUP:
                binding.tvType.setText("创建群组");
                setChooseView();
                break;
            case ZHUANZHUANG:
                binding.tvType.setText("转账");
                setNoChooseView();
//                setChooseView();
                break;
            case REDPACKAGE:
                binding.tvType.setText("红包");
                setChooseView();
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
                    checkPayPwd(String.valueOf(money), bankId);
                } else {

                    binding.tvP1.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });
        QMUIKeyboardHelper.showKeyboard(binding.etPwd, false);
        return binding.getRoot();
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
                } else if (payDialogType == PayDialogType.REDPACKAGE) {
                    createRedpackage();
                }

            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                dismiss();
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }
        });
    }

    private void tTransfer(String toId, String money, String bankId) {
        Map params = new HashMap();
        params.put("sum", money);
        params.put("get_user", toId);
        params.put("bank_id", 0);


        HttpClient.Builder.getServer().tTransfer(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                CreateRedPackageChildBean createRedPackageChildBean = new CreateRedPackageChildBean();
                createRedPackageChildBean.setUser_id(toId);
                createRedPackageChildBean.setFrom_id(String.valueOf(UserService.getInstance().getUserId()));
                createRedPackageChildBean.setUserContent("转账给" + nick);
                createRedPackageChildBean.setNoUserContent("收到" + UserService.getInstance().getUserInfoBean().getNick_name() + "的转账");
                createRedPackageChildBean.setMoney(money);
                RongUtils.sendZhuanzhuangRedPackageMessage(createRedPackageChildBean);
                EventBus.getDefault().post(new UserEvent.ZHUANZHUANG_SUC_EVENT());
                dismiss();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                dismiss();
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
                dismiss();
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
                dismiss();
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }
        });
    }

    private void createRedpackage() {
        Map params = new HashMap();
        params.put("sum", money);
        params.put("number", num);
        if (redPackageType == RedPackageType.CHAT) {
            params.put("get_user", toId);
        } else {
            params.put("group_id", toId);
        }

        if (StringUtils.isEmpty(remark)) {
            remark = "恭喜发财";
        } else {
            remark = remark;
        }


        HttpClient.Builder.getServer().tCreate(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<CreateRedPackageChildBean>() {
            @Override
            public void onSuccess(BaseBean<CreateRedPackageChildBean> baseBean) {
                if (redPackageType == RedPackageType.CHAT) {
                    baseBean.getData().setUser_id(String.valueOf(toId));
                    baseBean.getData().setFrom_id(String.valueOf(toId));
                } else {
                    baseBean.getData().setGroup_id(String.valueOf(toId));
                    baseBean.getData().setFrom_id(String.valueOf(toId));
                }

                baseBean.getData().setImage(UserService.getInstance().getUserInfoBean().getImg());
//                baseBean.getData().setUser_id(UserService.getInstance().getUserId());
                baseBean.getData().setDesc(remark);
                baseBean.getData().setRedName(UserService.getUserService().getUserInfoBean().getNick_name());
                RongUtils.sendRedPackageMessage(baseBean.getData());
                remark = "";
                EventBus.getDefault().post(new UserEvent.SEND_REDPACKAGE_EVENT());
                dismiss();
            }

            @Override
            public void onError(BaseBean<CreateRedPackageChildBean> baseBean) {
                dismiss();
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(UserEvent.CHOOSE_BANK_EVENT event) {
        currentBank = event.getBankListBean();
        if (StringUtils.isEmpty(event.getBankListBean().getOption())) {
            binding.tvPaytext.setText(event.getBankListBean().getBank_name());
        } else {
            binding.tvPaytext.setText("余额");
        }

        binding.rvList.setVisibility(View.GONE);
        binding.vClose.setVisibility(View.VISIBLE);
        binding.llytPwd.setVisibility(View.VISIBLE);
        binding.vLine.setVisibility(View.VISIBLE);
        binding.tvPaytype.setVisibility(View.VISIBLE);
        binding.tvPaytext.setVisibility(View.VISIBLE);
        binding.vBack.setVisibility(View.GONE);
        binding.ivClose.setVisibility(View.GONE);

    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QMUIKeyboardHelper.showKeyboard(binding.etPwd, false);
        binding.etPwd.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
