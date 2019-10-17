package com.zonghong.redpacket.rong;

import android.view.KeyEvent;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import io.rong.common.rlog.RLog;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.RongExtension;
import io.rong.imkit.emoticon.EmojiTab;
import io.rong.imkit.emoticon.IEmojiItemClickListener;
import io.rong.imkit.emoticon.IEmoticonTab;
import io.rong.imkit.plugin.CombineLocationPlugin;
import io.rong.imkit.plugin.DefaultLocationPlugin;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imkit.widget.provider.LocationPlugin;
import io.rong.imlib.model.Conversation;

public class GroupExtensionModule extends DefaultExtensionModule {

    private EditText mEditText;
    private Stack<EditText> stack;

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules = super.getPluginModules(conversationType);

        for (int i = 0; i < pluginModules.size(); i++) {
            if (pluginModules.get(i) instanceof FilePlugin) {
                pluginModules.remove(i);
            } else if (pluginModules.get(i) instanceof CombineLocationPlugin) {
                pluginModules.remove(i);
            } else if (pluginModules.get(i) instanceof DefaultLocationPlugin) {
                pluginModules.remove(i);
            } else if (pluginModules.get(i) instanceof LocationPlugin) {
                pluginModules.remove(i);
            } else if (pluginModules.get(i) instanceof ImagePlugin) {
                pluginModules.remove(i);
            }else if (pluginModules.get(i) instanceof MyLocationPlugin) {
                pluginModules.remove(i);
            }
        }
//        pluginModules.clear();
        pluginModules.add(new MyPicPlugin());
        pluginModules.add(new MyLocationPlugin());
        pluginModules.add(new MingpianPlugin());
        pluginModules.add(new RedpackagePlugin());
        return pluginModules;
    }

    public void onAttachedToExtension(RongExtension extension) {
        this.mEditText = extension.getInputEditText();
        this.stack.push(this.mEditText);
    }

    public void onDetachedFromExtension() {
        if (this.stack.size() > 0) {
            this.stack.pop();
            this.mEditText = this.stack.size() > 0 ? (EditText)this.stack.peek() : null;
        }

    }

    public void onInit(String appKey) {
        this.stack = new Stack();
    }

    @Override
    public List<IEmoticonTab> getEmoticonTabs() {
        EmojiTab emojiTab = new EmojiTab();
        emojiTab.setOnItemClickListener(new IEmojiItemClickListener() {
            public void onEmojiClick(String emoji) {
                EditText editText = GroupExtensionModule.this.mEditText;
                if (editText != null) {
                    int start = editText.getSelectionStart();
                    editText.getText().insert(start, emoji);
                }

            }

            public void onDeleteClick() {
                EditText editText = GroupExtensionModule.this.mEditText;
                if (editText != null) {
                    editText.dispatchKeyEvent(new KeyEvent(0, 67));
                }

            }
        });
        List<IEmoticonTab> list = new ArrayList();
        list.add(emojiTab);
        list.add(new MyEmoticon());
        return list;
    }

}
