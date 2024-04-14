package com.lith.minecord.events.discord;

import javax.annotation.Nonnull;
import org.jetbrains.annotations.NotNull;
import com.lith.minecord.Plugin;
import com.lith.minecord.config.ConfigManager;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;

@RequiredArgsConstructor
public class BotEvent extends ListenerAdapter {
    @NotNull
    private final Plugin plugin;

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        JDA client = event.getJDA();
        String botName = client.getSelfUser().getName();

        plugin.log.info("Bot " + botName + " is Ready!");

        if (ConfigManager.slashCommands.commandsEnabled)
            registerGuildCommands(client);
    }

    @Override
    public void onShutdown(@Nonnull ShutdownEvent event) {
        plugin.log.info("Discord Bot shutdown!");
    }

    private void registerGuildCommands(JDA client) {
        Guild guild = client.getGuildById(ConfigManager.botConfig.serverId);
        if (guild == null)
            return;

        if (ConfigManager.slashCommands.online.enabled
                && !ConfigManager.slashCommands.online.name.isEmpty()) {

            guild.updateCommands().addCommands(
                    Commands.slash(
                            ConfigManager.slashCommands.online.name.toLowerCase(),
                            ConfigManager.slashCommands.online.description))
                    .queue();
        }
    }
}
