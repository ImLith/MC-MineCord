package com.lith.minecord.discord;

import javax.annotation.Nonnull;
import com.lith.minecord.Plugin;
import com.lith.minecord.Static;
import com.lith.minecord.classes.McMessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.Component;

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
        McMessageBuilder msgBuilder = new McMessageBuilder(event);
        if (!msgBuilder.isValid())
            return;

        Component msg = msgBuilder.addReplier().build();
        if (msg == null)
            return;

        Plugin.plugin.getServer().broadcast(msg);
    }
}
