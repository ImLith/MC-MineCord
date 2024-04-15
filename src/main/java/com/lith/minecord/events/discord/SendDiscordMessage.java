package com.lith.minecord.events.discord;

import javax.annotation.Nonnull;
import org.jetbrains.annotations.NotNull;
import com.lith.minecord.MineCordPlugin;
import com.lith.minecord.classes.McMessageBuilder;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.Component;

@RequiredArgsConstructor
public class SendDiscordMessage extends ListenerAdapter {
    @NotNull
    private final MineCordPlugin plugin;

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (plugin.configs.mcMsg.format.isEmpty())
            return;

        McMessageBuilder msgBuilder = new McMessageBuilder(plugin, event);
        if (!msgBuilder.isValid())
            return;

        Component msg = msgBuilder.addReplier().build();
        if (msg == null)
            return;

        plugin.getServer().broadcast(msg);
    }
}
