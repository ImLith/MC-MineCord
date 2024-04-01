package com.lith.minecord.config;

import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.lithcore.abstractClasses.PluginConfigManager;
import com.lith.minecord.Static.ConfigKeys;

public class ConfigManager extends PluginConfigManager {
    public static BotConfig botConfig;
    public static LivechatConfig livechatConfig;

    public ConfigManager(final MainPlugin<ConfigManager> plugin) {
        super(plugin);

        botConfig = new BotConfig();
        livechatConfig = new LivechatConfig();
    }

    public final class BotConfig {
        public final String token = getString(ConfigKeys.Bot.TOKEN);
    }

    public final class LivechatConfig {
        public final String channelId = getString(ConfigKeys.Livechat.CHANNEL_ID);
    }
}
