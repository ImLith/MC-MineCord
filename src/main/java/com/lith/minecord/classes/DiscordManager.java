package com.lith.minecord.classes;

import com.lith.minecord.Static;
import com.lith.minecord.config.ConfigManager;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import reactor.core.publisher.Mono;

public class DiscordManager {
    private static DiscordManager init = null;
    private DiscordClient client = null;
    private GatewayDiscordClient login = null;

    private DiscordManager() {
    }

    public static DiscordManager init() {
        if (init == null)
            init = new DiscordManager();

        return init;
    }

    public void start() {
        if (isLoggedIn()) {
            Static.log.warning(
                    "The Discord client is already logged in!\nFirst stop it if you want to re=login!");
            return;
        }

        createClient();
        login();
    }

    public void stop() {
        if (isLoggedIn()) {
            login.logout().block();
            login = null;
        }

        Static.log.info("Bot stopped offline");
    }

    private void createClient() {
        if (isInit())
            return;

        client = DiscordClient.create(ConfigManager.botConfig.token);
    }

    private void login() {
        if (isLoggedIn())
            return;

        Mono<Void> login = client.withGateway(
                (GatewayDiscordClient gateway) -> gateway.on(ReadyEvent.class, event -> DiscordEvents.onReady(event)));
        login.block();
    }

    private long getAppId() {
        return login.getRestClient().getApplicationId().block();
    };

    private boolean isInit() {
        return client != null;
    }

    private boolean isLoggedIn() {
        return login != null;
    }
}
