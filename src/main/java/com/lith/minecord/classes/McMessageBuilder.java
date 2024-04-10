package com.lith.minecord.classes;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageType;

public class McMessageBuilder {
    private Message message = null;
    private Message repliedMessage = null;

    public McMessageBuilder() {
    };

    public McMessageBuilder(Message message) {
        if (isTextMessage(message))
            this.message = message;
    };

    public McMessageBuilder setMessage(Message message) {
        if (isTextMessage(message))
            this.message = message;

        return this;
    }

    public McMessageBuilder setRepliedMessage(Message message) {
        if (isReplyTextMessage(message))
            this.repliedMessage = message;

        return this;
    }

    public McMessageBuilder addReplier() {
        if (isReplyTextMessage(this.message))
            this.repliedMessage = this.message.getReferencedMessage();

        return this;
    }

    public Boolean isTextMessage() {
        return isTextMessage(this.message);
    }

    public static Boolean isTextMessage(Message message) {
        return isReplyTextMessage(message) || isDefaultTextMessage(message);
    }

    public static Boolean isDefaultTextMessage(Message message) {
        return message.getType() == MessageType.DEFAULT;
    }

    public static boolean isReplyTextMessage(Message message) {
        return message.getType() == MessageType.INLINE_REPLY;
    }
}
