package com.lith.minecord.utils;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageType;

public class DiscordMessageUtil {
    public static Boolean isTextMessage(Message message) {
        return isDefaultMessage(message) || isReplyMessage(message);
    }

    public static Boolean isDefaultMessage(Message message) {
        return message != null && message.getType() == MessageType.DEFAULT;
    }

    public static boolean isReplyMessage(Message message) {
        return message != null && message.getType() == MessageType.INLINE_REPLY;
    }

}
