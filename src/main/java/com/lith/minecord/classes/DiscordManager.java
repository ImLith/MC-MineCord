package com.lith.minecord.classes;

import org.jetbrains.annotations.NotNull;
import com.lith.minecord.Static;
import com.lith.minecord.config.ConfigManager;
import com.lith.minecord.events.discord.BotEvent;
import com.lith.minecord.events.discord.SendDiscordMessage;
import com.lith.minecord.events.discord.SlashCommandEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class DiscordManager {
    private static DiscordManager init = null;
    private JDABuilder builder = null;
    private JDA client = null;

    private DiscordManager() {
        createBuilder();
    }

    public static DiscordManager init() {
        if (init == null)
            init = new DiscordManager();

        return init;
    }

    public JDA getClient() {
        return client;
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
                    Static.log.warning("Failed to send message to Discord: " + error);
                });
    }

    private void createBuilder() {
        builder = JDABuilder.createDefault(ConfigManager.botConfig.token);

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.disableCache(CacheFlag.ACTIVITY);
        builder.setChunkingFilter(ChunkingFilter.NONE);
        builder.setLargeThreshold(50);
        builder.enableIntents(Static.gatewayIntents);
    }

    private void createClient() {
        try {
            client = builder.build();
            client.addEventListener(new SendDiscordMessage());
            client.addEventListener(new BotEvent());

            if (ConfigManager.slashCommands.commandsEnabled)
                client.addEventListener(new SlashCommandEvent());

            client.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
