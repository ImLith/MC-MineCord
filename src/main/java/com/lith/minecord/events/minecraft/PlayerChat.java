package com.lith.minecord.events.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.lith.minecord.MineCordPlugin;
import com.lith.minecord.Static;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

@RequiredArgsConstructor
public class PlayerChat implements Listener {
    private final MineCordPlugin plugin;

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(AsyncChatEvent event) {
        String msg = PlainTextComponentSerializer.plainText().serialize(event.originalMessage());

        plugin.getDiscordManager().sendMessage(
                Static.textChannel,
                plugin.configs.dcMsg.format
                        .replace(Static.MessageKey.PLAYER_NAME, event.getPlayer().getName())
                        .replace(Static.MessageKey.CONTENT, msg));
    }
}
