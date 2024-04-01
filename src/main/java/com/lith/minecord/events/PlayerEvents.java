package com.lith.minecord.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import com.lith.minecord.Static;
import com.lith.minecord.config.ConfigManager;
import com.lith.minecord.discord.DiscordManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class PlayerEvents implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(AsyncChatEvent event) {
        String msg = PlainTextComponentSerializer.plainText().serialize(event.originalMessage());
        String content = ConfigManager.livechatConfig.formatMinecraft
                .replace(Static.MessageKey.PLAYER_NAME, getPlayerName(event))
                .replace(Static.MessageKey.CONTENT, msg);

        send(content);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String msg = ConfigManager.livechatConfig.joinMessage;
        if (msg.isEmpty())
            return;

        send(msg.replace(Static.MessageKey.PLAYER_NAME, getPlayerName(event)));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String msg = ConfigManager.livechatConfig.leaveMessage;
        if (msg.isEmpty())
            return;

        send(msg.replace(Static.MessageKey.PLAYER_NAME, getPlayerName(event)));
    }

    private String getPlayerName(PlayerEvent event) {
        return getPlayer(event).getName();
    }

    private Player getPlayer(PlayerEvent event) {
        return event.getPlayer();
    }

    private void send(String content) {
        DiscordManager.init().sendMessage(ConfigManager.livechatConfig.channelId, content);
    }
}
