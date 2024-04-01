package com.lith.minecord.discord;

import javax.annotation.Nonnull;
import com.lith.minecord.Plugin;
import com.lith.minecord.Static;
import com.lith.minecord.config.ConfigManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.TextComponent;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.event.HoverEvent.showText;
import static net.kyori.adventure.text.event.ClickEvent.openUrl;

public class DiscordEvents extends ListenerAdapter {
    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        String botName = event.getJDA().getSelfUser().getName();
        Static.log.info("Bot " + botName + " is Ready!");
    }

    @Override
    public void onShutdown(@Nonnull ShutdownEvent event) {
        Static.log.info("Discord Bot shutdown!");
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!event.isFromGuild())
            return;

        MessageChannel channel = event.getChannel();
        if (!(channel instanceof TextChannel))
            return;

        if (!channel.getId().equals(ConfigManager.livechatConfig.channelId))
            return;

        User author = event.getAuthor();
        if (author.isBot())
            return;

        Message message = event.getMessage();
        if (message.getType() != MessageType.DEFAULT)
            return;

        TextComponent text = text(ConfigManager.livechatConfig.formatDiscord
                .replace(Static.MessageKey.USER_NAME, author.getName())
                .replace(Static.MessageKey.CONTENT, message.getContentRaw()));

        if (ConfigManager.livechatConfig.hoverText.length() > 1)
            text = text.hoverEvent(showText(text(ConfigManager.livechatConfig.hoverText)));

        if (ConfigManager.livechatConfig.canClick)
            text = text.clickEvent(openUrl(ConfigManager.botConfig.inviteLink));

        Plugin.plugin.getServer().broadcast(text);
    }
}
