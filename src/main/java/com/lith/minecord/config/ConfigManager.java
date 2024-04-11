package com.lith.minecord.config;

import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.lithcore.abstractClasses.PluginConfigManager;
import com.lith.minecord.Static.ConfigKeys;
import com.lith.minecord.classes.DcValidationBuilder;

public class ConfigManager extends PluginConfigManager {
    public static BotConfig botConfig;
    public static DiscordMessage dcMsg;
    public static MinecraftMessage mcMsg;
    public static DcValidationBuilder mcTextValidations;
    public static SlashCommands slashCommands;

    public ConfigManager(final MainPlugin<ConfigManager> plugin) {
        super(plugin);

        botConfig = new BotConfig();
        dcMsg = new DiscordMessage();
        mcMsg = new MinecraftMessage();
        slashCommands = new SlashCommands();
        mcTextValidations = new DcValidationBuilder();

        mcTextValidations.isOnlyFromGuild = true;
        mcTextValidations.requiredChannelId = botConfig.channelId;
    }

    public final class BotConfig {
        public final String token = getString(ConfigKeys.Bot.TOKEN);
        public final Long serverId = getLong(ConfigKeys.Bot.SERVER_ID);
        public final String inviteLink = getString(ConfigKeys.Bot.INVITE_LINK);
        public final String channelId = getString(ConfigKeys.Bot.CHANNEL_ID);
    }

    public final class DiscordMessage {
        public final String join = getString(ConfigKeys.Discord_Messages.JOIN);
        public final String leave = getString(ConfigKeys.Discord_Messages.LEAVE);
        public final String achievement = getString(ConfigKeys.Discord_Messages.ACHIEVEMENT);
        public final String serverOn = getString(ConfigKeys.Discord_Messages.SERVER_ON);
        public final String serverOff = getString(ConfigKeys.Discord_Messages.SERVER_OFF);
        public final String format = getString(ConfigKeys.Discord_Messages.FORMAT);
        public final Boolean onDeath = getBool(ConfigKeys.Discord_Messages.DEATH);
    }

    public final class MinecraftMessage {
        public final Reply reply = new Reply();;

        public final String prefix = getMessage(ConfigKeys.Minecraft_Messages.PREFIX);
        public final String hover = getMessage(ConfigKeys.Minecraft_Messages.HOVER);
        public final String format = getMessage(ConfigKeys.Minecraft_Messages.FORMAT);
        public final Boolean isClickable = getBool(ConfigKeys.Minecraft_Messages.CLICKABLE)
                && !getString(ConfigKeys.Bot.INVITE_LINK).isEmpty();

        public final class Reply {
            public final String icon = getMessage(ConfigKeys.Minecraft_Messages.Reply.ICON);
            public final String hoverUser = getMessage(ConfigKeys.Minecraft_Messages.Reply.HOVER_USER);
            public final String hoverBot = getMessage(ConfigKeys.Minecraft_Messages.Reply.HOVER_BOT);
        }
    }

    public final class SlashCommands {
        public final Boolean commandsEnabled = getBool(ConfigKeys.Slash_Commands.ENABLE_COMMANDS);

        public final Online online = new Online();

        public final class Online {
            public final Boolean enabled = getBool(ConfigKeys.Slash_Commands.Online.ENABLED);
            public final String name = getString(ConfigKeys.Slash_Commands.Online.NAME);
            public final String description = getString(ConfigKeys.Slash_Commands.Online.DESCRIPTION);
            public final Boolean isEphemeral = getBool(ConfigKeys.Slash_Commands.Online.IS_EPHEMERAL);
            public final String format = getString(ConfigKeys.Slash_Commands.Online.FORMAT);
        }
    }
}
