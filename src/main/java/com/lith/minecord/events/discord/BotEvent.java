package com.lith.minecord.events.discord;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import org.jetbrains.annotations.NotNull;
import com.lith.minecord.MineCordPlugin;
import com.lith.minecord.interfaces.ISlashCommand;
import com.lith.minecord.utils.DiscordUtil;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;

@RequiredArgsConstructor
public class BotEvent extends ListenerAdapter {
    @NotNull
    private final MineCordPlugin plugin;

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        plugin.log.info("Bot " + DiscordUtil.getBotName(event) + " is Ready!");

        registerGuildCommands(event.getJDA());
    }

    @Override
    public void onShutdown(@Nonnull ShutdownEvent event) {
        plugin.log.info("Bot " + DiscordUtil.getBotName(event) + " shutdown!");
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (MineCordPlugin.getDiscordManager() == null || !plugin.configs.isEnableSlashCommands())
            return;

        String eventCmdName = event.getName();
        ISlashCommand cmd = MineCordPlugin.getDiscordManager().getCommands().get(eventCmdName);

        if (cmd != null)
            cmd.run(event);
    }

    private void registerGuildCommands(JDA client) {
        if (!plugin.configs.isEnableSlashCommands() || MineCordPlugin.getDiscordManager() == null)
            return;

        HashMap<String, ISlashCommand> commands = MineCordPlugin.getDiscordManager().getCommands();
        if (commands.size() == 0)
            return;

        Long serverId = plugin.configs.getServerId();
        if (serverId == null)
            return;

        Guild guild = client.getGuildById(serverId);
        if (guild == null)
            return;

        for (Map.Entry<String, ISlashCommand> entry : commands.entrySet()) {
            ISlashCommand cmd = entry.getValue();
            String cmdName = cmd.getCommandName().toLowerCase();

            guild.updateCommands().addCommands(
                    Commands.slash(
                            cmdName,
                            cmd.getCommandDescription()))
                    .queue();

            plugin.log.info("Register Discord command '" + cmdName + "'");
        }
    }
}
