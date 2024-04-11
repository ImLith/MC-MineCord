package com.lith.minecord.events.player;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.lith.minecord.utils.OnlineCounterUtil;

public class UpdateChannelTopic implements Listener {
    public static int playerCount = 0;

    public UpdateChannelTopic() {
        UpdateChannelTopic.playerCount = 0;

        OnlineCounterUtil.setPlayerOnlineCounter();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UpdateChannelTopic.playerCount += 1;

        OnlineCounterUtil.setPlayerOnlineCounter();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UpdateChannelTopic.playerCount -= 1;

        OnlineCounterUtil.setPlayerOnlineCounter();
    }
}
