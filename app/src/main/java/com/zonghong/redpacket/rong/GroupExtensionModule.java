package com.zonghong.redpacket.rong;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.DefaultLocationPlugin;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imkit.widget.provider.LocationPlugin;
import io.rong.imlib.model.Conversation;

public class GroupExtensionModule extends DefaultExtensionModule {


    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules = super.getPluginModules(conversationType);
        for (int i = 0; i < pluginModules.size(); i++) {
            if (pluginModules.get(i) instanceof FilePlugin) {
                pluginModules.remove(i);
            }
        }
        pluginModules.add(new DefaultLocationPlugin());
        pluginModules.add(new RedpackagePlugin());
//        pluginModules.add(new ZhuanZhangPlugin());
        pluginModules.add(new MingpianPlugin());
        return pluginModules;
    }

}
