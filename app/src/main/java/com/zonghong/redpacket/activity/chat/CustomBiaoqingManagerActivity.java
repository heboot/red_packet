package com.zonghong.redpacket.activity.chat;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.ArrayMap;
import android.view.View;

import com.example.http.HttpClient;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.CustomBiaoqingListBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.CustomBiaoqingAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityBiaoqingManagerBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CustomBiaoqingManagerActivity extends BaseActivity<ActivityBiaoqingManagerBinding> {

    private CustomBiaoqingAdapter customBiaoqingAdapter;

    private CustomBiaoqingListBean.ExpressionListBean expressionListBean;

    private QMUIDialog qmuiDialog;

    private QMUIBottomSheet chooseAvatarSheet;


    private final int REQUEST_CAMERA = 40001, REQUEST_PHOTO = 40002;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_biaoqing_manager;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("表情管理");
    }

    @Override
    public void initData() {

        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);

        binding.rvList.setLayoutManager(new GridLayoutManager(MAPP.mapp, 4));
        expressionListBean = new CustomBiaoqingListBean.ExpressionListBean();
        expressionListBean.setAdd(true);
        list();
    }

    @Override
    public void initListener() {

    }


    public void selectPic() {
        if (chooseAvatarSheet == null) {
            chooseAvatarSheet = DialogUtils.getAvatarBottomSheet(CustomBiaoqingManagerActivity.this, new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                @Override
                public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                    if (position == 0) {
                        PictureSelector.create(CustomBiaoqingManagerActivity.this)
                                .openCamera(PictureMimeType.ofImage())
                                .enableCrop(true)
                                .isGif(true)
                                .compress(true)
                                .withAspectRatio(1, 1)
                                .maxSelectNum(1).compress(false)
                                .forResult(REQUEST_CAMERA);
                        chooseAvatarSheet.dismiss();
                    } else {
                        PictureSelector.create(CustomBiaoqingManagerActivity.this)
                                .openGallery(PictureMimeType.ofImage())
                                .isGif(true)
//                                .compress(true)
                                .maxSelectNum(1).enableCrop(false)
                                .forResult(REQUEST_PHOTO);
                        chooseAvatarSheet.dismiss();
                    }
                }
            });
        }
        chooseAvatarSheet.show();
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
            if (selectList.get(0).isCompressed()) {
                avatarPath = selectList.get(0).getCompressPath();
            } else {
                avatarPath = selectList.get(0).getPath();
            }


            uploadAvatar();
//                UploadUtils.uploadImage(selectList.get(0).getCompressPath(), UploadUtils.getIDCardPath(), upCompletionHandler);
        }
    }

    private String avatarPath;

    private void uploadAvatar() {

        loadingDialog.show();

        File file = new File(avatarPath);
        RequestBody requestFile;
        if (avatarPath.endsWith("gif")) {
            requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        } else {
            requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        }
        requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);


        MultipartBody.Part body = MultipartBody.Part.createFormData("expression", file.getName(), requestFile);


        HttpClient.Builder.getServer().createExpression(UserService.getInstance().getToken(), body).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                dismissLoadingDialog();
                list();
                tipDialog = DialogUtils.getSuclDialog(CustomBiaoqingManagerActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                dismissLoadingDialog();
                tipDialog = DialogUtils.getFailDialog(CustomBiaoqingManagerActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private List<CustomBiaoqingListBean.ExpressionListBean> datas = new ArrayList<>();
//    https://zonghongkeji.cn/redPacket/public/in/delPression

    public void del(String eid) {
        qmuiDialog = new QMUIDialog.MessageDialogBuilder(this)
                .setMessage("确定要删除表情吗")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        qmuiDialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        qmuiDialog.dismiss();
                        doDel(eid);
                    }
                })
                .create();
        qmuiDialog.show();
    }

    private void doDel(String eid) {
        params = new HashMap<>();
        params.put("expre_id", eid);
        HttpClient.Builder.getServer().delPression(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(CustomBiaoqingManagerActivity.this, baseBean.getMsg(), true);
                tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        list();
                    }
                });
                tipDialog.show();

            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(CustomBiaoqingManagerActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void list() {

        HttpClient.Builder.getServer().getExpression(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<CustomBiaoqingListBean.ExpressionListBean>>() {
            @Override
            public void onSuccess(BaseBean<List<CustomBiaoqingListBean.ExpressionListBean>> baseBean) {
                datas.clear();
                datas.add(expressionListBean);
                if (baseBean.getData() != null && baseBean.getData().size() > 0) {
                    datas.addAll(baseBean.getData());
                }
                if (customBiaoqingAdapter == null) {
                    customBiaoqingAdapter = new CustomBiaoqingAdapter(datas, new WeakReference<>(CustomBiaoqingManagerActivity.this));
                    binding.rvList.setAdapter(customBiaoqingAdapter);
                } else {
                    customBiaoqingAdapter.setNewData(datas);
                }

            }

            @Override
            public void onError(BaseBean<List<CustomBiaoqingListBean.ExpressionListBean>> baseBean) {
                tipDialog = DialogUtils.getFailDialog(CustomBiaoqingManagerActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}
