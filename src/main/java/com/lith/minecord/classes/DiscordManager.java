package com.lith.minecord.classes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import com.lith.minecord.MineCordPlugin;
import com.lith.minecord.events.discord.BotEvent;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class DiscordManager {
    private final @NotNull MineCordPlugin plugin;
    private Set<GatewayIntent> gatewayIntents = new HashSet<>();
    private Set<ListenerAdapter> events = new HashSet<>();
    @Getter
    private JDA client = null;

    public DiscordManager(@NotNull MineCordPlugin plugin) {
        this.plugin = plugin;

        addEvent(new BotEvent(plugin));
    }

    public void addGatewayIntent(@NotNull GatewayIntent gatewayIntent) {
        this.gatewayIntents.add(gatewayIntent);
    }

    public void addGatewayIntent(@NotNull GatewayIntent... gatewayIntents) {
        for (GatewayIntent gatewayIntent : gatewayIntents)
            this.gatewayIntents.add(gatewayIntent);
    }

    public void addGatewayIntent(@NotNull Collection<GatewayIntent> gatewayIntents) {
        for (GatewayIntent gatewayIntent : gatewayIntents)
            this.gatewayIntents.add(gatewayIntent);
    }

    public void addEvent(@NotNull ListenerAdapter event) {
        this.events.add(event);
    }

    public void addEvent(@NotNull ListenerAdapter... events) {
        for (ListenerAdapter event : events)
            this.events.add(event);
    }

    public void start() {
        try {
            if (!isOnline())
                run();
            else
                plugin.log.warning("Bot is already running! First stop it!");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (isOnline()) {
            client.shutdown();
            client = null;
        }
    }

    public Boolean isOnline() {
        return client != null;
    }

    public void sendMessage(@NotNull String channelId, @NotNull String content) {
        if (!isOnline())
            return;

        if (channelId.isEmpty() || content.isEmpty())
            return;

        TextChannel channel = client.getTextChannelById(channelId);
        if (channel == null)
            return;

        sendMessage(channel, content);
    }

    public void sendMessage(@NotNull TextChannel channel, @NotNull String content) {
        if (!isOnline())
            return;

        channel.sendMessage(content).queue(
                success -> {
                },
                error -> {
                    plugin.log.warning("Failed to send message to Discord: " + error);
                });
    }

    private void createClient() {
        client = generateBuilder().build();
    }

    private JDABuilder generateBuilder() {
        return JDABuilder.createDefault(plugin.configs.getToken())
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .disableCache(CacheFlag.ACTIVITY)
                .setChunkingFilter(ChunkingFilter.NONE)
                .setLargeThreshold(50)
                .enableIntents(gatewayIntents);
    }

    private void run() throws InterruptedException {
        createClient();

        for (ListenerAdapter event : events)
            client.addEventListener(event);

        client.awaitReady();
    }
}
