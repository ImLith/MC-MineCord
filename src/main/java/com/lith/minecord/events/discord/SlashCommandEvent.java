package com.lith.minecord.events.discord;

import javax.annotation.Nonnull;
import org.bukkit.Bukkit;
import java.util.List;
import com.lith.lithcore.utils.PlayerUtil;
import com.lith.minecord.config.ConfigManager;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import java.awt.Color;

public class SlashCommandEvent extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (event.getName().equals(ConfigManager.slashCommands.online.name.toLowerCase())) {
            event.deferReply(true).queue();

            List<String> playerNames = PlayerUtil.getOnlinePlayerNames();
            int onlinePlayers = playerNames.size();
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setColor(Color.RED)
                    .setAuthor("Currently online " + onlinePlayers + " players out of " + Bukkit.getMaxPlayers());

            if (onlinePlayers > 0)
                embedBuilder.setDescription("**" + String.join("** | **", playerNames) + "**");

            event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }
}
