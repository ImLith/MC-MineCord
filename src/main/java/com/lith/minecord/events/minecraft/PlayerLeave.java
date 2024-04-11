package com.lith.minecord.events.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import com.lith.minecord.Static;
import com.lith.minecord.classes.DiscordManager;
import com.lith.minecord.config.ConfigManager;

public class PlayerLeave implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        DiscordManager.init().sendMessage(
                Static.textChannel,
                ConfigManager.dcMsg.leave
                        .replace(Static.MessageKey.PLAYER_NAME, event.getPlayer().getName()));
    }
}
