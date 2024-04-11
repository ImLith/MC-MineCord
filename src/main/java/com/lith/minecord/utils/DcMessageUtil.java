package com.lith.minecord.utils;

import javax.annotation.Nonnull;
import com.lith.minecord.classes.DcValidationBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DcMessageUtil {
    public static Boolean isTextMessage(Message message) {
        return message != null && (isReplyTextMessage(message) || isDefaultTextMessage(message));
    }

    public static Boolean isDefaultTextMessage(Message message) {
        return message != null && message.getType() == MessageType.DEFAULT;
    }

    public static boolean isReplyTextMessage(Message message) {
        return message != null && message.getType() == MessageType.INLINE_REPLY;
    }

    public static Boolean isGuildEvent(MessageReceivedEvent event) {
        return event.isFromGuild();
    }

    public static Boolean isInTextChannel(MessageReceivedEvent event) {
        return isInTextChannel(event.getChannel());
    }

    public static Boolean isInTextChannel(MessageChannel channel) {
        return channel instanceof TextChannel;
    }

    public static Boolean isSpecificChannel(MessageReceivedEvent event, String channelId) {
        return isSpecificChannel(event.getChannel(), channelId);
    }

    public static Boolean isSpecificChannel(MessageChannel channel, String channelId) {
        return channel.getId().equals(channelId);
    }

    public static Boolean isBotUser(MessageReceivedEvent event) {
        return isBotUser(event.getAuthor());
    }

    public static Boolean isBotUser(User user) {
        return user.isBot();
    }

    public static Boolean validateMessageOrigin(MessageReceivedEvent event) {
        return validateMessageOrigin(event, new DcValidationBuilder());
    }

    public static Boolean validateMessageOrigin(MessageReceivedEvent e, @Nonnull DcValidationBuilder v) {
        if (e == null)
            return false;

        if (v.isOnlyFromGuild && !isGuildEvent(e))
            return false;

        if (v.isOnlyTextChannel && !isInTextChannel(e))
            return false;

        if (v.requiredChannelId != null && !isSpecificChannel(e, v.requiredChannelId))
            return false;

        if (v.isOnlyUser && isBotUser(e))
            return false;

        if (v.isOnlyTextMessage) {
            Message message = e.getMessage();

            if (v.allowReplyMessage && !isTextMessage(message))
                return false;
            if (!v.allowReplyMessage && !isDefaultTextMessage(message))
                return false;
        }

        return true;
    }
}
