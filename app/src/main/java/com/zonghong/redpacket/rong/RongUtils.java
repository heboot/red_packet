package com.zonghong.redpacket.rong;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.bean.GetRedpackageModel;
import com.waw.hr.mutils.bean.MingPianBean;
import com.waw.hr.mutils.event.GroupEvent;
import com.waw.hr.mutils.event.MessageEvent;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.MainActivity;
import com.zonghong.redpacket.common.SendMingPianType;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.sticker.util.FileUtil;

public class RongUtils {
    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #init(Context)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     * @param callback 连接回调。
     * @return RongIMClient  客户端核心类的实例。
     */
    public static void connect(String token) {

//        if (MAPP.mapp.getApplicationInfo().packageName.equals(MAPP.mapp.(getApplicationContext()))) {

        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                            2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                UserService.getInstance().setUserId(userid);
                IntentUtils.doIntent(MainActivity.class);
                EventBus.getDefault().post(new UserEvent.LOGIN_SUC_EVENT());
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    public static void toGroupChat(String toUserId, String toUserName) {
        setGroupExtensionModule();
        RongIM.getInstance().startConversation(MAPP.mapp.getCurrentActivity(), Conversation.ConversationType.GROUP, toUserId, toUserName);
    }

    public static void toGroupChat(String toUserId, String toUserName, long time) {
        setGroupExtensionModule();
        RongIM.getInstance().startConversation(MAPP.mapp.getCurrentActivity(), Conversation.ConversationType.GROUP, toUserId, toUserName, time);
    }

    public static void toChat(String toUserId, String toUserName) {
        setPrivateExtensionModule();
        /**
         * 启动单聊界面。
         *
         * @param context      应用上下文。
         * @param targetUserId 要与之聊天的用户 Id。
         * @param title        聊天的标题，开发者需要在聊天界面通过 intent.getData().getQueryParameter("title")
         *                     获取该值, 再手动设置为聊天界面的标题。
         */
        RongIM.getInstance().startConversation(MAPP.mapp.getCurrentActivity(), Conversation.ConversationType.PRIVATE, toUserId, toUserName);
    }

    public static void toChatByTime(String toUserId, String toUserName, long time) {
        setPrivateExtensionModule();
        /**
         * 启动单聊界面。
         *
         * @param context      应用上下文。
         * @param targetUserId 要与之聊天的用户 Id。
         * @param title        聊天的标题，开发者需要在聊天界面通过 intent.getData().getQueryParameter("title")
         *                     获取该值, 再手动设置为聊天界面的标题。
         */
        RongIM.getInstance().startConversation(MAPP.mapp.getCurrentActivity(), Conversation.ConversationType.PRIVATE, toUserId, toUserName, time);
    }

    public static void sendZhuanzhuangRedPackageMessage(CreateRedPackageChildBean createRedPackageChildBean) {
        RedPackageZhuanZhangChatMessage redPackageChatMessage = new RedPackageZhuanZhangChatMessage(JSON.toJSONString(createRedPackageChildBean).getBytes());
        Message message = new Message();
        message.setConversationType(Conversation.ConversationType.PRIVATE);
        message.setTargetId(createRedPackageChildBean.getUser_id());
        message.setContent(redPackageChatMessage);
        message.setMessageDirection(io.rong.imlib.model.Message.MessageDirection.SEND);
        RongIM.getInstance().sendMessage(message, "", "", new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                LogUtil.e("SEND MESSAGE", "onAttached");
            }

            @Override
            public void onSuccess(Message message) {
                LogUtil.e("SEND MESSAGE", "onSuccess");
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                LogUtil.e("SEND MESSAGE", "onError");
            }
        });
    }

    public static void sendRedPackageMessage(CreateRedPackageChildBean createRedPackageChildBean) {
        RedPackageChatMessage redPackageChatMessage = new RedPackageChatMessage(JSON.toJSONString(createRedPackageChildBean).getBytes());
        Message message = new Message();
        if (StringUtils.isEmpty(createRedPackageChildBean.getGroup_id())) {
            message.setConversationType(Conversation.ConversationType.PRIVATE);
            message.setTargetId(createRedPackageChildBean.getUser_id());
        } else {
            message.setConversationType(Conversation.ConversationType.GROUP);
            message.setTargetId(createRedPackageChildBean.getGroup_id());
        }
        message.setContent(redPackageChatMessage);
        message.setMessageDirection(io.rong.imlib.model.Message.MessageDirection.SEND);
        RongIM.getInstance().sendMessage(message, "", "", new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                LogUtil.e("SEND MESSAGE", "onAttached");
            }

            @Override
            public void onSuccess(Message message) {
                LogUtil.e("SEND MESSAGE", "onSuccess");
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                LogUtil.e("SEND MESSAGE", "onError");
            }
        });
    }

    public static void sendRedPackageOpenMessage(GetRedpackageModel getRedpackageModel) {
        LogUtil.e("SEND MESSAGE", JSON.toJSONString(getRedpackageModel));
        RedPackageChatOpenMessage redPackageChatMessage = new RedPackageChatOpenMessage(JSON.toJSONString(getRedpackageModel).getBytes());
        Message message = new Message();
        if (StringUtils.isEmpty(getRedpackageModel.getGroupId())) {
            message.setConversationType(Conversation.ConversationType.PRIVATE);
            message.setTargetId(getRedpackageModel.getUserId());
        } else {
            message.setConversationType(Conversation.ConversationType.GROUP);
            message.setTargetId(getRedpackageModel.getGroupId());
        }
        message.setContent(redPackageChatMessage);
        message.setMessageDirection(io.rong.imlib.model.Message.MessageDirection.SEND);

        RongIM.getInstance().sendMessage(message, "", "", new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                LogUtil.e("SEND MESSAGE", "onAttached");
            }

            @Override
            public void onSuccess(Message message) {
                LogUtil.e("SEND MESSAGE", "onSuccess");
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                LogUtil.e("SEND MESSAGE", "onError");
            }
        });
    }


    public static void sendMingPianMessage(SendMingPianType type, String toId, MingPianBean mingPianBean) {
        MingPianMessage mingPianMessage = new MingPianMessage(JSON.toJSONString(mingPianBean).getBytes());
        Message message = new Message();
        if (type == SendMingPianType.USER) {
            message.setConversationType(Conversation.ConversationType.PRIVATE);
        } else {
            message.setConversationType(Conversation.ConversationType.GROUP);
        }
        message.setContent(mingPianMessage);
        message.setTargetId(toId);
        message.setMessageDirection(io.rong.imlib.model.Message.MessageDirection.SEND);
        RongIM.getInstance().sendMessage(message, "", "", new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                LogUtil.e("SEND MESSAGE", "onAttached");
            }

            @Override
            public void onSuccess(Message message) {
                LogUtil.e("SEND MESSAGE", "onSuccess");
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                LogUtil.e("SEND MESSAGE", "onError");
            }
        });
    }


    public static void checkUnread() {
        RongIM.getInstance().getTotalUnreadCount(new RongIMClient.ResultCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                EventBus.getDefault().post(new MessageEvent.GET_UNREAD_MESSAGE_NUM_EVENT(integer));
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    public static void setConversationToTop(Conversation.ConversationType type, String targetId) {
        /**
         * 设置某一会话为置顶或者取消置顶，回调方式获取设置是否成功。
         *
         * @param conversationType 会话类型。
         * @param id               目标 Id。根据不同的 conversationType，可能是用户 Id、群组 Id 或聊天室 Id。
         * @param isTop            是否置顶。
         * @param callback         设置置顶或取消置顶是否成功的回调。
         */
        RongIM.getInstance().setConversationToTop(type, targetId, true, null);
    }

    public static void setPrivateExtensionModule() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new CustomDefaultExtensionModule());
            }
        }
    }

    public static void setGroupExtensionModule() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    //            if (defaultModule != null) {
                    defaultModule = module;
//                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
//                RongExtensionManager.getInstance().registerExtensionModule(new GroupExtensionModule());
//            }

//                    for (int i = 0; i < module.getPluginModules(Conversation.ConversationType.GROUP).size(); i++) {
//                        if (module.getPluginModules(Conversation.ConversationType.GROUP).get(i) instanceof ZhuanZhangPlugin) {
//                            module.getPluginModules(Conversation.ConversationType.GROUP).remove(i);
//                        }
//                    }
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new GroupExtensionModule());
            }
        }
    }

    public static void sendCustonBiaoqingMessage(String to, Conversation.ConversationType conversationType, String url) {

       Glide.with(MAPP.mapp).asFile().load(url).into(new SimpleTarget<File>() {
           @Override
           public void onLoadStarted(@Nullable Drawable placeholder) {
               LogUtil.e("下载onLoadStarted","");
           }

           @Override
           public void onLoadFailed(@Nullable Drawable errorDrawable) {
               LogUtil.e("下载onLoadFailed","");
           }

           @Override
           public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                LogUtil.e("下载",resource+""  );
                if( resource!=null){
                    LogUtil.e("下载2",resource.getPath()+"");
                    ImageMessage imageMessage = ImageMessage.obtain(Uri.fromFile(resource),Uri.fromFile(resource),true);
                    Message message = Message.obtain(to, conversationType, imageMessage);
                    message.setMessageDirection(io.rong.imlib.model.Message.MessageDirection.SEND);

                    RongIM.getInstance().sendImageMessage(message,"","", new RongIMClient.SendImageMessageCallback() {
                        @Override
                        public void onAttached(Message message) {
                            LogUtil.e("发送图片","onAttached");
                        }

                        @Override
                        public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                            LogUtil.e("发送图片","onError"  + JSON.toJSONString(errorCode));
                        }

                        @Override
                        public void onSuccess(Message message) {
                            LogUtil.e("发送图片","onSuccess"  );

                        }

                        @Override
                        public void onProgress(Message message, int i) {

                        }
                    });
                }
           }



       });




    }

    public static void sendClearMessage(String groupId) {
        String str = "{groupId:'" + groupId + "'}";
        DeleteGroupMessageEventMessage deleteGroupMessageEventMessage = new DeleteGroupMessageEventMessage(str.getBytes());
        Message message = new Message();
        message.setConversationType(Conversation.ConversationType.GROUP);
        message.setTargetId(groupId);
        message.setContent(deleteGroupMessageEventMessage);
        message.setMessageDirection(io.rong.imlib.model.Message.MessageDirection.SEND);
        RongIM.getInstance().sendMessage(message, "", "", new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                LogUtil.e("SEND MESSAGE", "onAttached");
            }

            @Override
            public void onSuccess(Message message) {
                LogUtil.e("SEND MESSAGE", "onSuccess");
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                LogUtil.e("SEND MESSAGE", "onError");
            }
        });
    }

    public static void deleteGroupMessage(String groupId) {
//        RongIMClient.getInstance().deleteMessages(Conversation.ConversationType.GROUP, groupId, new RongIMClient.ResultCallback<Boolean>() {
//            @Override
//            public void onSuccess(Boolean aBoolean) {
        EventBus.getDefault().post(new GroupEvent.DELETE_GROUP_MESSAGE_EVENT(groupId));
//            }

//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                ToastUtils.show(MAPP.mapp, JSON.toJSONString(errorCode));
//            }
//        });
    }

}
