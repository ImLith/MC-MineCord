package com.lith.minecord.utils;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DiscordChannelUtil {
    public static Boolean isTextChannel(MessageReceivedEvent event) {
        return isTextChannel(event.getChannel());
    }

    public static Boolean isTextChannel(MessageChannel channel) {
        return channel instanceof TextChannel;
    }

    public static Boolean isSpecificChannel(MessageReceivedEvent event, String channelId) {
        return isSpecificChannel(event.getChannel(), channelId);
    }

    public static Boolean isSpecificChannel(MessageChannel channel, String channelId) {
        return isSpecificChannel(channel.getId(), channelId);
    }

    public static Boolean isSpecificChannel(String id, String channelId) {
        return id.equals(channelId);
    }
}
