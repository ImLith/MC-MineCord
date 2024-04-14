package com.lith.minecord.classes;

import org.jetbrains.annotations.NotNull;
import com.lith.minecord.Plugin;
import com.lith.minecord.Static;
import com.lith.minecord.events.discord.BotEvent;
import com.lith.minecord.events.discord.SendDiscordMessage;
import com.lith.minecord.events.discord.SlashCommandEvent;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class DiscordManager {
    private final Plugin plugin;
    private JDABuilder builder = null;
    @Getter
    private JDA client = null;

    public DiscordManager(@NotNull Plugin plugin) {
        this.plugin = plugin;
        createBuilder();
    }

    public Boolean isOnline() {
        return client != null;
    }

    public void start() {
        if (!isOnline())
            createClient();
    }

    public void stop() {
        if (isOnline()) {
            client.shutdown();
            client = null;
        }
    }

    public void sendMessage(@NotNull String channelId, @NotNull String content) {
        if (client == null)
            return;

        if (channelId.isEmpty() || content.isEmpty())
            return;

        TextChannel channel = client.getTextChannelById(channelId);
        if (channel == null)
            return;

        sendMessage(channel, content);
    }

    public void sendMessage(@NotNull TextChannel channel, @NotNull String content) {
        if (client == null)
            return;

        channel.sendMessage(content).queue(
                success -> {
                },
                error -> {
                    plugin.log.warning("Failed to send message to Discord: " + error);
                });
    }

    private void createBuilder() {
        builder = JDABuilder.createDefault(plugin.configs.botConfig.token);

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.disableCache(CacheFlag.ACTIVITY);
        builder.setChunkingFilter(ChunkingFilter.NONE);
        builder.setLargeThreshold(50);
        builder.enableIntents(Static.gatewayIntents);
    }

    private void createClient() {
        try {
            client = builder.build();
            client.addEventListener(new SendDiscordMessage(plugin));
            client.addEventListener(new BotEvent(plugin));

            if (plugin.configs.slashCommands.commandsEnabled)
                client.addEventListener(new SlashCommandEvent(plugin));

            client.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
