package com.zonghong.redpacket.rong;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.emoticon.EmojiTab;
import io.rong.imkit.emoticon.IEmoticonTab;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imlib.model.Conversation;

public class CustomDefaultExtensionModule extends DefaultExtensionModule {

    private RedpackagePlugin myPlugin;

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules = super.getPluginModules(conversationType);
        for (int i = 0; i < pluginModules.size(); i++) {
            if (pluginModules.get(i) instanceof FilePlugin) {
                pluginModules.remove(i);
            }
        }
        pluginModules.add(new RedpackagePlugin());
        pluginModules.add(new ZhuanZhangPlugin());
        pluginModules.add(new MingpianPlugin());
        return pluginModules;
    }

    @Override
    public List<IEmoticonTab> getEmoticonTabs() {
        List<IEmoticonTab> list = new ArrayList<>();
        EmojiTab emojiTab = new EmojiTab();
        list.add(emojiTab);
        list.add(new MyEmoticon());
        return list;
    }

}
