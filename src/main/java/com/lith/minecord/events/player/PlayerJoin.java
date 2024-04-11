package com.lith.minecord.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.lith.minecord.Static;
import com.lith.minecord.classes.DiscordManager;
import com.lith.minecord.config.ConfigManager;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        DiscordManager.init().sendMessage(
                Static.textChannel,
                ConfigManager.dcMsg.join
                        .replace(Static.MessageKey.PLAYER_NAME, event.getPlayer().getName()));
    }
}
