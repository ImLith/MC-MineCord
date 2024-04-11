package com.lith.minecord.config;

import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.lithcore.abstractClasses.PluginConfigManager;
import com.lith.minecord.Static.ConfigKeys;
import com.lith.minecord.classes.DcValidationBuilder;

public class ConfigManager extends PluginConfigManager {
    public static BotConfig botConfig;
    public static LivechatConfig livechatConfig;
    public static DcValidationBuilder mcTextValidations;

    public ConfigManager(final MainPlugin<ConfigManager> plugin) {
        super(plugin);

        botConfig = new BotConfig();
        livechatConfig = new LivechatConfig();
        mcTextValidations = new DcValidationBuilder();

        mcTextValidations.isOnlyFromGuild = true;
        mcTextValidations.requiredChannelId = livechatConfig.channelId;
    }

    public final class BotConfig {
        public final String token = getString(ConfigKeys.Bot.TOKEN);
        public final String inviteLink = getString(ConfigKeys.Bot.INVITE_LINK);
        public final Long serverId = getLong(ConfigKeys.Bot.SERVER_ID);
    }

    public final class LivechatConfig {
        public final String channelId = getString(ConfigKeys.Livechat.CHANNEL_ID);
        public final String formatDiscord = getMessage(ConfigKeys.Livechat.FORMAT_DISCORD);
        public final String formatMinecraft = getMessage(ConfigKeys.Livechat.FORMAT_MINECRAFT);
        public final String hoverText = getMessage(ConfigKeys.Livechat.HOVER_TEXT);
        public final Boolean canClick = getBool(ConfigKeys.Livechat.CAN_CLICK);
        public final Boolean onDeath = getBool(ConfigKeys.Livechat.ON_DEATH);
        public final String joinMessage = getString(ConfigKeys.Livechat.JOIN_MESSAGE);
        public final String leaveMessage = getString(ConfigKeys.Livechat.LEAVE_MESSAGE);
        public final String achievement = getString(ConfigKeys.Livechat.ACHIEVEMENT);
        public final String serverOnline = getString(ConfigKeys.Livechat.SERVER_ONLINE);
        public final String serverOffline = getString(ConfigKeys.Livechat.SERVER_OFFLINE);
        public final String replyIcon = getMessage(ConfigKeys.Livechat.REPLY_ICON);
        public final String replyHoverText = getMessage(ConfigKeys.Livechat.REPLY_HOVER_TEXT);
        public final String replyBotHoverText = getMessage(ConfigKeys.Livechat.REPLY_HOVER_TEXT_BOT);
        public final String onlineCounterFormat = getMessage(ConfigKeys.Livechat.ONLINE_COUNTER_FORMAT);
        public final String offlineDescription = getMessage(ConfigKeys.Livechat.OFFLINE_DESCRIPTION);
    }
}
