package com.lith.minecord.config;

import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.lithcore.abstractClasses.PluginConfigManager;
import com.lith.minecord.Static.ConfigKeys;

public class ConfigManager extends PluginConfigManager {
    public static String botToken;

    public ConfigManager(final MainPlugin<ConfigManager> plugin) {
        super(plugin);

        botToken = getString(ConfigKeys.BOT_TOKEN);
    }
}
