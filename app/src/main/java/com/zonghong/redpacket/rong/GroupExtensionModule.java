package com.zonghong.redpacket.rong;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

public class GroupExtensionModule extends DefaultExtensionModule {

    private RedpackagePlugin myPlugin;

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules = super.getPluginModules(conversationType);

        pluginModules.add(new RedpackagePlugin());
//        pluginModules.add(new ZhuanZhangPlugin());
        pluginModules.add(new MingpianPlugin());
        return pluginModules;
    }

}
