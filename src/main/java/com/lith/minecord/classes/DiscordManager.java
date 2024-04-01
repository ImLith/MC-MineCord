package com.lith.minecord.classes;

import com.lith.minecord.config.ConfigManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
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

    private void createBuilder() {
        builder = JDABuilder.createDefault(ConfigManager.botConfig.token);

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.disableCache(CacheFlag.ACTIVITY);
        builder.setChunkingFilter(ChunkingFilter.NONE);
        builder.setLargeThreshold(50);
    }

    private void createClient() {
        try {
            client = builder.build();
            client.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
