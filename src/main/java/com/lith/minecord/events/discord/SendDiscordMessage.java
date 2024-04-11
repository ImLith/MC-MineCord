package com.lith.minecord.events.discord;

import javax.annotation.Nonnull;
import com.lith.minecord.Plugin;
import com.lith.minecord.classes.McMessageBuilder;
import com.lith.minecord.config.ConfigManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.Component;

public class SendDiscordMessage extends ListenerAdapter {
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (ConfigManager.mcMsg.format.isEmpty())
            return;

        McMessageBuilder msgBuilder = new McMessageBuilder(event);
        if (!msgBuilder.isValid())
            return;

        Component msg = msgBuilder.addReplier().build();
        if (msg == null)
            return;

        Plugin.plugin.getServer().broadcast(msg);
    }
}
