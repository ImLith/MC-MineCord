package com.lith.minecord.events.discord;

import javax.annotation.Nonnull;
import org.jetbrains.annotations.NotNull;
import com.lith.minecord.MineCordPlugin;
import com.lith.minecord.utils.DiscordUtil;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;

@RequiredArgsConstructor
public class BotEvent extends ListenerAdapter {
    @NotNull
    private final MineCordPlugin plugin;

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        plugin.log.info("Bot " + DiscordUtil.getBotName(event) + " is Ready!");
    }

    @Override
    public void onShutdown(@Nonnull ShutdownEvent event) {
        plugin.log.info("Bot " + DiscordUtil.getBotName(event) + " shutdown!");
    }
}
