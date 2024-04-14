package com.lith.minecord.events.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import com.lith.minecord.Plugin;
import com.lith.minecord.Static;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

@RequiredArgsConstructor
public class PlayerDeath implements Listener {
    private final Plugin plugin;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Component deathMessageComponent = event.deathMessage();
        if (deathMessageComponent == null)
            return;

        String deathMessage = PlainTextComponentSerializer.plainText().serialize(deathMessageComponent);
        String playerName = event.getEntity().getName();

        plugin.getDiscordManager().sendMessage(
                Static.textChannel,
                deathMessage.replace(playerName, "**" + playerName + "**"));
    }
}
