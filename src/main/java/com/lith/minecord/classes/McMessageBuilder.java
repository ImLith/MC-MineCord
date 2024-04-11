package com.lith.minecord.classes;

import java.util.ArrayList;
import com.lith.minecord.Static;
import com.lith.minecord.config.ConfigManager;
import com.lith.minecord.utils.DcMessageUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.dv8tion.jda.api.entities.User;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.event.HoverEvent.showText;
import static net.kyori.adventure.text.event.ClickEvent.openUrl;

public class McMessageBuilder {
    private boolean isValid = false;
    private Message message = null;
    private Message repliedMessage = null;

    public McMessageBuilder(MessageReceivedEvent event) {
        if (DcMessageUtil.validateMessageOrigin(event, ConfigManager.mcTextValidations)) {
            this.message = event.getMessage();
            this.isValid = true;
        }
    };

    public Boolean isValid() {
        return this.isValid;
    }

    public McMessageBuilder setMessage(Message message) {
        if (DcMessageUtil.isTextMessage(message))
            this.message = message;

        return this;
    }

    public McMessageBuilder setRepliedMessage(Message message) {
        if (DcMessageUtil.isReplyTextMessage(message))
            this.repliedMessage = message;

        return this;
    }

    public McMessageBuilder addReplier() {
        if (DcMessageUtil.isReplyTextMessage(this.message))
            this.repliedMessage = this.message.getReferencedMessage();

        return this;
    }

    public Component build() {
        if (message == null)
            return null;

        ArrayList<TextComponent> targetResponse = new ArrayList<>();

        if (!ConfigManager.mcMsg.prefix.isEmpty())
            targetResponse.add(buildMessagePrefix());

        if (repliedMessage != null && !ConfigManager.mcMsg.reply.icon.isEmpty())
            targetResponse.add(buildReplySection());

        targetResponse.add(buildMessageSection());
        Component responseComponent = join(noSeparators(), targetResponse);

        if (ConfigManager.mcMsg.isClickable)
            responseComponent = responseComponent.clickEvent(openUrl(ConfigManager.botConfig.inviteLink));

        return responseComponent;
    }

    private TextComponent buildReplySection() {
        return addHoverText(text(ConfigManager.mcMsg.reply.icon), repliedMessage.getAuthor());
    }

    private TextComponent buildMessageSection() {
        TextComponent text = text(ConfigManager.mcMsg.format
                .replace(Static.MessageKey.USER_NAME, message.getAuthor().getEffectiveName())
                .replace(Static.MessageKey.CONTENT, message.getContentDisplay()));

        if (repliedMessage != null)
            text = addHoverText(text, repliedMessage.getAuthor());

        return text;
    }

    private TextComponent buildMessagePrefix() {
        TextComponent text = text(ConfigManager.mcMsg.prefix
                .replace(Static.MessageKey.USER_NAME, message.getAuthor().getEffectiveName()));

        if (!ConfigManager.mcMsg.hover.isEmpty())
            text = text.hoverEvent(showText(text(ConfigManager.mcMsg.hover)));

        return text;
    }

    private TextComponent addHoverText(TextComponent text, User user) {
        String hoverText = null;

        if (user.isBot()) {
            if (!ConfigManager.mcMsg.reply.hoverBot.isEmpty())
                hoverText = ConfigManager.mcMsg.reply.hoverBot;
        } else {
            if (!ConfigManager.mcMsg.reply.hoverUser.isEmpty())
                hoverText = ConfigManager.mcMsg.reply.hoverUser
                        .replace(Static.MessageKey.USER_NAME, user.getEffectiveName());
        }

        if (hoverText != null) {
            hoverText = hoverText.replace(Static.MessageKey.CONTENT, repliedMessage.getContentStripped());
            text = text.hoverEvent(showText(text(hoverText)));
        }

        return text;
    }
}
