package com.zonghong.redpacket.activity.user;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.http.HttpClient;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.LoginBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.loginregister.LoginActivity;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.AlterTextType;
import com.zonghong.redpacket.common.QRCodeType;
import com.zonghong.redpacket.databinding.ActivityInfoBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class InfoActivity extends BaseActivity<ActivityInfoBinding> {

    private QMUIBottomSheet sexSheet;

    private QMUIBottomSheet chooseAvatarSheet;

    private final int REQUEST_CAMERA = 40001, REQUEST_PHOTO = 40002;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("个人信息");
    }

    @Override
    public void initData() {
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
        binding.tvNick.setText(UserService.getInstance().getUserInfoBean().getNick_name());
        binding.tvSex.setText(UserService.getInstance().getUserInfoBean().getSex() == 1 ? "男" : "女");
        ImageUtils.showAvatar(UserService.getInstance().getUserInfoBean().getImg(), binding.ivAvatar);
    }

    @Override
    public void initListener() {
        binding.tvSexTitle.setOnClickListener((v) -> {
            if (sexSheet == null) {
                sexSheet = DialogUtils.getSexbottomSheet(this, new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        sexSheet.dismiss();
                        if (position == 0) {
                            chooseSex(2);
                        } else {
                            chooseSex(1);
                        }
                    }
                });
            }
            sexSheet.show();
        });
        binding.tvName.setOnClickListener((v) -> {
            IntentUtils.intent2AlterTextActivity(AlterTextType.NICK_NAME, UserService.getInstance().getUserInfoBean().getNick_name());
        });
        binding.tvCodeTitle.setOnClickListener((v) -> {
            IntentUtils.intent2QRCodeActivity(QRCodeType.USER, UserService.getInstance().getUserInfoBean().getNick_name(), UserService.getInstance().getUserInfoBean().getImg(), UserService.getInstance().getUserInfoBean().getSex() + "", "" + UserService.getInstance().getUserInfoBean().getAccount_id());
//            IntentUtils.doIntent(QRCodeActivity.class);
        });

        binding.tvAvatar.setOnClickListener((v) -> {
            if (chooseAvatarSheet == null) {
                chooseAvatarSheet = DialogUtils.getAvatarBottomSheet(InfoActivity.this, new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        if (position == 0) {
                            PictureSelector.create(InfoActivity.this)
                                    .openCamera(PictureMimeType.ofImage())
                                    .enableCrop(true)
                                    .withAspectRatio(1, 1)
                                    .maxSelectNum(1).compress(true)
                                    .forResult(REQUEST_CAMERA);
                            chooseAvatarSheet.dismiss();
                        } else {
                            PictureSelector.create(InfoActivity.this)
                                    .openGallery(PictureMimeType.ofImage())
                                    .maxSelectNum(1).enableCrop(true)
                                    .compress(true).withAspectRatio(1, 1)
                                    .forResult(REQUEST_PHOTO);
                            chooseAvatarSheet.dismiss();
                        }
                    }
                });
            }
            chooseAvatarSheet.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 图片、视频、音频选择结果回调
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
            avatarPath = selectList.get(0).getCompressPath();

            uploadAvatar();
//                UploadUtils.uploadImage(selectList.get(0).getCompressPath(), UploadUtils.getIDCardPath(), upCompletionHandler);
        }
    }

    private String avatarPath;

    private void uploadAvatar() {

        loadingDialog.show();

        File file = new File(avatarPath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), requestFile);

        HttpClient.Builder.getServer().editHeadImg(UserService.getInstance().getToken(), body).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                dismissLoadingDialog();
//                tipDialog = DialogUtils.getSuclDialog(InfoActivity.this, baseBean.getMsg(), true);
//                tipDialog.show();
                ImageUtils.showAvatar((String) baseBean.getData(), binding.ivAvatar);
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                dismissLoadingDialog();
                tipDialog = DialogUtils.getFailDialog(InfoActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private void chooseSex(int sex) {
        loadingDialog.show();
        params.put("sex", sex);
        HttpClient.Builder.getServer().uSex(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
//                tipDialog = DialogUtils.getSuclDialog(InfoActivity.this, baseBean.getMsg(), true);
                if (sex == 1) {
                    binding.tvSex.setText("男");
                } else {
                    binding.tvSex.setText("女");
                }
//                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(InfoActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}
