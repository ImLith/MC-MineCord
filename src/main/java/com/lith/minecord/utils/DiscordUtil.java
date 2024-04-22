package com.lith.minecord.utils;

import javax.annotation.Nonnull;
import org.jetbrains.annotations.NotNull;
import com.lith.minecord.classes.DiscordValidationBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.GenericSessionEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Message;

public class DiscordUtil {
    public static String getBotName(@NotNull GenericSessionEvent event) {
        return getBotName(event.getJDA());
    }

    public static String getBotName(@NotNull JDA client) {
        return getBotName(client.getSelfUser());
    }

    public static String getBotName(@NotNull SelfUser selfUser) {
        return selfUser.getName();
    }

    public static Boolean isBotUser(MessageReceivedEvent event) {
        return isBotUser(event.getAuthor());
    }

    public static Boolean isBotUser(User user) {
        return user.isBot();
    }

    public static Boolean validateMessageOrigin(MessageReceivedEvent event) {
        return validateMessageOrigin(event, new DiscordValidationBuilder());
    }

    public static Boolean validateMessageOrigin(MessageReceivedEvent e, @Nonnull DiscordValidationBuilder v) {
        if (e == null)
            return false;

        if (v.isOnlyFromGuild && !DiscordEventUtil.isGuildEvent(e))
            return false;

        if (v.isOnlyTextChannel && !DiscordChannelUtil.isTextChannel(e))
            return false;

        if (v.requiredChannelId != null && !DiscordChannelUtil.isSpecificChannel(e, v.requiredChannelId))
            return false;

        if (v.isOnlyUser && isBotUser(e))
            return false;

        if (v.isOnlyTextMessage) {
            Message message = e.getMessage();

            if (v.allowReplyMessage && !DiscordMessageUtil.isTextMessage(message))
                return false;

            if (!v.allowReplyMessage && !DiscordMessageUtil.isDefaultMessage(message))
                return false;
        }

        return true;
    }
}
