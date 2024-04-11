package com.lith.minecord;

import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.lithcore.classes.commands.ReloadConfigCmd;
import com.lith.minecord.classes.DiscordManager;
import com.lith.minecord.config.ConfigManager;
import com.lith.minecord.events.player.PlayerAchievement;
import com.lith.minecord.events.player.PlayerChat;
import com.lith.minecord.events.player.PlayerDeath;
import com.lith.minecord.events.player.PlayerJoin;
import com.lith.minecord.events.player.PlayerLeave;
import com.lith.minecord.utils.DcMessageUtil;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import java.util.ArrayList;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class Plugin extends MainPlugin<ConfigManager> {
  private ArrayList<Listener> registeredEvents = new ArrayList<>();

  public static Plugin plugin;

  public void onEnable() {
    Plugin.plugin = this;

    registerConfigs();
    registerCommands();

    if (isChannelValid() && !ConfigManager.dcMsg.serverOn.isEmpty()) {
      DiscordManager.init().sendMessage(
          Static.textChannel,
          ConfigManager.dcMsg.serverOn);
    }

    Static.log.info("Plugin enabled");
  }

  public void onDisable() {
    if (isChannelValid() && !ConfigManager.dcMsg.serverOff.isEmpty())
      DiscordManager.init().sendMessage(
          Static.textChannel,
          ConfigManager.dcMsg.serverOff);

    DiscordManager.init().stop();
    Static.log.info("Plugin disabled");
  }

  @Override
  public void registerConfigs() {
    Static.textChannel = null;

    unregisterEvents();
    new ConfigManager(this);

    if (!DiscordManager.init().isOnline())
      DiscordManager.init().start();

    validateChannel();

    if (!isChannelValid()) {
      Static.log.warning("Text channel not found! Check your configs");
    } else {
      registerEvents();
    }
  }

  private void registerCommands() {
    new ReloadConfigCmd<Plugin>(this, Static.Command.PermissionKeys.RELOAD, Static.Command.Names.RELOAD);
  }

  private void registerEvents() {
    if (!ConfigManager.dcMsg.format.isEmpty())
      registerEvent(new PlayerChat());

    if (!ConfigManager.dcMsg.join.isEmpty())
      registerEvent(new PlayerJoin());

    if (!ConfigManager.dcMsg.leave.isEmpty())
      registerEvent(new PlayerLeave());

    if (!ConfigManager.dcMsg.achievement.isEmpty())
      registerEvent(new PlayerAchievement());

    if (ConfigManager.dcMsg.onDeath)
      registerEvent(new PlayerDeath());
  }

  private void registerEvent(Listener event) {
    getServer().getPluginManager().registerEvents(event, this);
    registeredEvents.add(event);
  }

  private void unregisterEvents() {
    for (Listener event : registeredEvents) {
      HandlerList.unregisterAll(event);
    }

    registeredEvents.clear();
  }

  private void validateChannel() {
    TextChannel channel = DiscordManager.init().getClient().getTextChannelById(ConfigManager.botConfig.channelId);
    Static.textChannel = DcMessageUtil.isInTextChannel(channel) ? channel : null;
  }

  private Boolean isChannelValid() {
    return Static.textChannel != null;
  }
}
