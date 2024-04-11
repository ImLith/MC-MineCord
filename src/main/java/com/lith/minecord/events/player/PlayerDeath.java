package com.lith.minecord.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import com.lith.minecord.Static;
import com.lith.minecord.classes.DiscordManager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class PlayerDeath implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Component deathMessageComponent = event.deathMessage();
        if (deathMessageComponent == null)
            return;

        String deathMessage = PlainTextComponentSerializer.plainText().serialize(deathMessageComponent);
        String playerName = event.getEntity().getName();

        DiscordManager.init().sendMessage(
                Static.textChannel,
                deathMessage.replace(playerName, "**" + playerName + "**"));
    }
}
