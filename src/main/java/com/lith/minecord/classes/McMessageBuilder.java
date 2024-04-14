package com.lith.minecord.classes;

import java.util.ArrayList;
import com.lith.minecord.Plugin;
import com.lith.minecord.Static;
import com.lith.minecord.utils.DcMessageUtil;
import lombok.Getter;
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
    private final Plugin plugin;
    @Getter
    private boolean isValid = false;
    private Message message = null;
    private Message repliedMessage = null;

    public McMessageBuilder(Plugin plugin, MessageReceivedEvent event) {
        this.plugin = plugin;

        if (DcMessageUtil.validateMessageOrigin(event, plugin.configs.mcTextValidations)) {
            this.message = event.getMessage();
            this.isValid = true;
        }
    };

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

        if (!plugin.configs.mcMsg.prefix.isEmpty())
            targetResponse.add(buildMessagePrefix());

        if (repliedMessage != null && !plugin.configs.mcMsg.reply.icon.isEmpty())
            targetResponse.add(buildReplySection());

        targetResponse.add(buildMessageSection());
        Component responseComponent = join(noSeparators(), targetResponse);

        if (plugin.configs.mcMsg.isClickable)
            responseComponent = responseComponent.clickEvent(openUrl(plugin.configs.botConfig.inviteLink));

        return responseComponent;
    }

    private TextComponent buildReplySection() {
        return addHoverText(text(plugin.configs.mcMsg.reply.icon), repliedMessage.getAuthor());
    }

    private TextComponent buildMessageSection() {
        TextComponent text = text(plugin.configs.mcMsg.format
                .replace(Static.MessageKey.USER_NAME, message.getAuthor().getEffectiveName())
                .replace(Static.MessageKey.CONTENT, message.getContentDisplay()));

        if (repliedMessage != null)
            text = addHoverText(text, repliedMessage.getAuthor());

        return text;
    }

    private TextComponent buildMessagePrefix() {
        TextComponent text = text(plugin.configs.mcMsg.prefix
                .replace(Static.MessageKey.USER_NAME, message.getAuthor().getEffectiveName()));

        if (!plugin.configs.mcMsg.hover.isEmpty())
            text = text.hoverEvent(showText(text(plugin.configs.mcMsg.hover)));

        return text;
    }

    private TextComponent addHoverText(TextComponent text, User user) {
        String hoverText = null;

        if (user.isBot()) {
            if (!plugin.configs.mcMsg.reply.hoverBot.isEmpty())
                hoverText = plugin.configs.mcMsg.reply.hoverBot;
        } else {
            if (!plugin.configs.mcMsg.reply.hoverUser.isEmpty())
                hoverText = plugin.configs.mcMsg.reply.hoverUser
                        .replace(Static.MessageKey.USER_NAME, user.getEffectiveName());
        }

        if (hoverText != null) {
            hoverText = hoverText.replace(Static.MessageKey.CONTENT, repliedMessage.getContentStripped());
            text = text.hoverEvent(showText(text(hoverText)));
        }

        return text;
    }
}
