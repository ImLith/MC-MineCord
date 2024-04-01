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
        public final String inviteLink = getString(ConfigKeys.Bot.INVITE_LINK);
    }

    public final class LivechatConfig {
        public final String channelId = getString(ConfigKeys.Livechat.CHANNEL_ID);
        public final String formatDiscord = getMessage(ConfigKeys.Livechat.FORMAT_DISCORD);
        public final String formatMinecraft = getMessage(ConfigKeys.Livechat.FORMAT_MINECRAFT);
        public final String hoverText = getMessage(ConfigKeys.Livechat.HOVER_TEXT);
        public final Boolean canClick = config.getBoolean(ConfigKeys.Livechat.CAN_CLICK);
        public final String joinMessage = getString(ConfigKeys.Livechat.JOIN_MESSAGE);
        public final String leaveMessage = getString(ConfigKeys.Livechat.LEAVE_MESSAGE);
    }
}
