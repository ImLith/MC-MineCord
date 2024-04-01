package com.lith.minecord.discord;

import org.jetbrains.annotations.NotNull;
import com.lith.minecord.Static;
import com.lith.minecord.config.ConfigManager;
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

    public void start() {
        if (client == null) {
            createClient();
        }
    }

    public void stop() {
        if (client != null) {
            client.shutdownNow();
            client = null;
        }
    }

    public void sendMcMessage(@NotNull String playerName, @NotNull String content) {
        if (client == null)
            return;

        if (playerName.isEmpty() || content.isEmpty())
            return;

        String channelId = ConfigManager.livechatConfig.channelId;
        if (channelId == null)
            return;

        TextChannel channel = client.getTextChannelById(channelId);
        if (channel == null)
            return;

        String msg = ConfigManager.livechatConfig.formatMinecraft
                .replace(Static.MessageKey.PLAYER_NAME, playerName)
                .replace(Static.MessageKey.CONTENT, content);
        if (msg == null)
            return;

        channel.sendMessage(msg).queue(
                error -> {
                    Static.log.warning("Failed to send message to Discord: " + error.getContentRaw());
                });
    }

    @SuppressWarnings("null")
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
            client.addEventListener(new DiscordEvents());
            client.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
