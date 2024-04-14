package com.lith.minecord.events.discord;

import javax.annotation.Nonnull;
import org.jetbrains.annotations.NotNull;
import com.lith.minecord.Plugin;
import com.lith.minecord.classes.McMessageBuilder;
import com.lith.minecord.config.ConfigManager;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.Component;

@RequiredArgsConstructor
public class SendDiscordMessage extends ListenerAdapter {
    @NotNull
    private final Plugin plugin;

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

        plugin.getServer().broadcast(msg);
    }
}
