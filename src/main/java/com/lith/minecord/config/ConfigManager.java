package com.lith.minecord.config;

import com.lith.lithcore.abstractClasses.AbstractConfigManager;
import com.lith.minecord.Plugin;
import com.lith.minecord.Static.ConfigKeys;
import com.lith.minecord.classes.DcValidationBuilder;

public class ConfigManager extends AbstractConfigManager<Plugin, ConfigManager> {
    public static BotConfig botConfig;
    public static DiscordMessage dcMsg;
    public static MinecraftMessage mcMsg;
    public static DcValidationBuilder mcTextValidations;
    public static SlashCommands slashCommands;

    public ConfigManager(final Plugin plugin) {
        super(plugin);

        mcTextValidations = new DcValidationBuilder();
        mcTextValidations.isOnlyFromGuild = true;
        mcTextValidations.requiredChannelId = botConfig.channelId;
    }

    @Override
    public void load() {
        botConfig = new BotConfig();
        dcMsg = new DiscordMessage();
        mcMsg = new MinecraftMessage();
        slashCommands = new SlashCommands();
    }

    public final class BotConfig {
        public final String token = config.getString(ConfigKeys.Bot.TOKEN);
        public final Long serverId = config.getLong(ConfigKeys.Bot.SERVER_ID);
        public final String inviteLink = config.getString(ConfigKeys.Bot.INVITE_LINK);
        public final String channelId = config.getString(ConfigKeys.Bot.CHANNEL_ID);
    }

    public final class DiscordMessage {
        public final String join = config.getString(ConfigKeys.Discord_Messages.JOIN);
        public final String leave = config.getString(ConfigKeys.Discord_Messages.LEAVE);
        public final String achievement = config.getString(ConfigKeys.Discord_Messages.ACHIEVEMENT);
        public final String serverOn = config.getString(ConfigKeys.Discord_Messages.SERVER_ON);
        public final String serverOff = config.getString(ConfigKeys.Discord_Messages.SERVER_OFF);
        public final String format = config.getString(ConfigKeys.Discord_Messages.FORMAT);
        public final Boolean onDeath = config.getBoolean(ConfigKeys.Discord_Messages.DEATH);
    }

    public final class MinecraftMessage {
        public final Reply reply = new Reply();;

        public final String prefix = getMessage(ConfigKeys.Minecraft_Messages.PREFIX);
        public final String hover = getMessage(ConfigKeys.Minecraft_Messages.HOVER);
        public final String format = getMessage(ConfigKeys.Minecraft_Messages.FORMAT);
        public final Boolean isClickable = config.getBoolean(ConfigKeys.Minecraft_Messages.CLICKABLE)
                && !config.getString(ConfigKeys.Bot.INVITE_LINK).isEmpty();

        public final class Reply {
            public final String icon = getMessage(ConfigKeys.Minecraft_Messages.Reply.ICON);
            public final String hoverUser = getMessage(ConfigKeys.Minecraft_Messages.Reply.HOVER_USER);
            public final String hoverBot = getMessage(ConfigKeys.Minecraft_Messages.Reply.HOVER_BOT);
        }
    }

    public final class SlashCommands {
        public final Boolean commandsEnabled = config.getBoolean(ConfigKeys.Slash_Commands.ENABLE_COMMANDS);

        public final Online online = new Online();

        public final class Online {
            public final Boolean enabled = config.getBoolean(ConfigKeys.Slash_Commands.Online.ENABLED);
            public final String name = config.getString(ConfigKeys.Slash_Commands.Online.NAME);
            public final String description = config.getString(ConfigKeys.Slash_Commands.Online.DESCRIPTION);
            public final Boolean isEphemeral = config.getBoolean(ConfigKeys.Slash_Commands.Online.IS_EPHEMERAL);
            public final String format = config.getString(ConfigKeys.Slash_Commands.Online.FORMAT);
        }
    }
}
