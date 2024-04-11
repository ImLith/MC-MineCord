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

        if (repliedMessage != null)
            targetResponse.add(buildReplySection());

        targetResponse.add(buildMessageSection());

        return join(noSeparators(), targetResponse);
    }

    private TextComponent buildReplySection() {
        TextComponent text = text(ConfigManager.livechatConfig.replyIcon + " ");

        User user = repliedMessage.getAuthor();
        String hoverText = user.isBot()
                ? ConfigManager.livechatConfig.replyBotHoverText
                : ConfigManager.livechatConfig.replyHoverText
                        .replace(Static.MessageKey.USER_NAME,
                                user.getEffectiveName());

        hoverText = hoverText.replace(Static.MessageKey.CONTENT, repliedMessage.getContentStripped());
        text = text.hoverEvent(showText(text(hoverText)));

        if (ConfigManager.livechatConfig.canClick)
            text = text.clickEvent(openUrl(ConfigManager.botConfig.inviteLink));

        return text;
    }

    private TextComponent buildMessageSection() {
        TextComponent text = text(ConfigManager.livechatConfig.formatDiscord
                .replace(Static.MessageKey.USER_NAME, message.getAuthor().getEffectiveName())
                .replace(Static.MessageKey.CONTENT, message.getContentDisplay()));

        if (!ConfigManager.livechatConfig.hoverText.isEmpty())
            text = text.hoverEvent(showText(text(ConfigManager.livechatConfig.hoverText)));

        if (ConfigManager.livechatConfig.canClick)
            text = text.clickEvent(openUrl(ConfigManager.botConfig.inviteLink));

        return text;
    }
}
