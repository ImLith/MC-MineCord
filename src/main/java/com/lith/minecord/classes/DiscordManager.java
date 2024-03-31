package com.lith.minecord.classes;

import com.lith.minecord.Static;
import com.lith.minecord.config.ConfigManager;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;

public class DiscordManager {
    private GatewayDiscordClient client = null;

    public GatewayDiscordClient getClient() {
        return client;
    }

    public void start() {
        if (isInit()) {
            Static.log.warning(
                    "The Discord client is already initilized!\nFirst stop it if you want to re-initialize it!");
            return;
        }

        client = DiscordClient.create(ConfigManager.botConfig.token).login().block();
    }

    public void stop() {
        if (isInit()) {
            client.logout().block();
        }
    }

    private boolean isInit() {
        return client != null;
    }
}
