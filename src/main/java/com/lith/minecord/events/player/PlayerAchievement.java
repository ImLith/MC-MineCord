package com.lith.minecord.events.player;

import org.bukkit.advancement.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import com.lith.minecord.Static;
import com.lith.minecord.config.ConfigManager;
import com.lith.minecord.discord.DiscordManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import io.papermc.paper.advancement.AdvancementDisplay;

public class PlayerAchievement implements Listener {
    @EventHandler
    public void onPlayerAchievement(PlayerAdvancementDoneEvent event) {
        Advancement advancement = event.getAdvancement();

        AdvancementDisplay display = advancement.getDisplay();
        if (display == null)
            return;

        if (!event.getPlayer().getAdvancementProgress(advancement).isDone())
            return;

        Component titleComponent = display.title();
        String advancementTitle = titleComponent != null
                ? PlainTextComponentSerializer.plainText().serialize(titleComponent)
                : "";

        Component descriptionComponent = display.description();
        String advancementDescription = descriptionComponent != null
                ? PlainTextComponentSerializer.plainText().serialize(descriptionComponent)
                : "";

        DiscordManager.init().sendMessage(
                ConfigManager.livechatConfig.channelId,
                ConfigManager.livechatConfig.achievement
                        .replace(Static.MessageKey.PLAYER_NAME, event.getPlayer().getName())
                        .replace(Static.MessageKey.ACHIEVEMENT_NAME, advancementTitle)
                        .replace(Static.MessageKey.ACHIEVEMENT_DESCRIPTION, advancementDescription));
    }
}
