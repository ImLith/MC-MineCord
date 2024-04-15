package com.lith.minecord.events.discord;

import javax.annotation.Nonnull;
import org.bukkit.Bukkit;
import java.util.List;
import com.lith.lithcore.utils.PlayerUtil;
import com.lith.minecord.MineCordPlugin;
import com.lith.minecord.Static;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import java.awt.Color;

@RequiredArgsConstructor
public class SlashCommandEvent extends ListenerAdapter {
    private final MineCordPlugin plugin;

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (event.getName().equals(plugin.configs.slashCommands.online.name.toLowerCase())) {
            event.deferReply(plugin.configs.slashCommands.online.isEphemeral).queue();

            List<String> playerNames = PlayerUtil.getOnlinePlayerNames();
            int onlinePlayers = playerNames.size();
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setColor(Color.RED)
                    .setAuthor(plugin.configs.slashCommands.online.format
                            .replace(Static.MessageKey.CURRENT, String.valueOf(onlinePlayers))
                            .replace(Static.MessageKey.MAX, String.valueOf(Bukkit.getMaxPlayers())));

            if (onlinePlayers > 0)
                embedBuilder.setDescription("**" + String.join("** | **", playerNames) + "**");

            event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }
}
