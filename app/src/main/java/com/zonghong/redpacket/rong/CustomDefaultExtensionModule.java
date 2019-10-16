package com.zonghong.redpacket.rong;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.emoticon.EmojiTab;
import io.rong.imkit.emoticon.IEmoticonTab;
import io.rong.imkit.plugin.CombineLocationPlugin;
import io.rong.imkit.plugin.DefaultLocationPlugin;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imkit.widget.provider.LocationPlugin;
import io.rong.imlib.model.Conversation;

public class CustomDefaultExtensionModule extends DefaultExtensionModule {


    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules = super.getPluginModules(conversationType);
//        for (int i = 0; i < pluginModules.size(); i++) {
//            if (pluginModules.get(i) instanceof FilePlugin) {
//                pluginModules.remove(i);
//            }else if(pluginModules.get(i) instanceof CombineLocationPlugin){
//                pluginModules.remove(i);
//            }else if(pluginModules.get(i) instanceof DefaultLocationPlugin){
//                pluginModules.remove(i);
//            }else if(pluginModules.get(i) instanceof ImagePlugin){
//                pluginModules.remove(i);
//            }else if (pluginModules.get(i) instanceof LocationPlugin) {
//                pluginModules.remove(i);
//            }else if (pluginModules.get(i) instanceof MyLocationPlugin) {
//                pluginModules.remove(i);
//            }
//        }

        pluginModules.clear();
        pluginModules.add(new MyPicPlugin());
        pluginModules.add(new MyLocationPlugin());
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
