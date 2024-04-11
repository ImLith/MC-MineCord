package com.lith.minecord;

import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.lithcore.classes.commands.ReloadConfigCmd;
import com.lith.minecord.config.ConfigManager;
import com.lith.minecord.discord.DiscordManager;
import com.lith.minecord.events.player.PlayerAchievement;
import com.lith.minecord.events.player.PlayerChat;
import com.lith.minecord.events.player.PlayerDeath;
import com.lith.minecord.events.player.PlayerJoin;
import com.lith.minecord.events.player.PlayerLeave;
import com.lith.minecord.events.player.UpdateChannelTopic;

public class Plugin extends MainPlugin<ConfigManager> {
  public static Plugin plugin;

  public void onEnable() {
    Plugin.plugin = this;

    registerConfigs();
    DiscordManager.init().start();
    registerEvents();
    registerCommands();

    Static.log.info("Plugin enabled");

    if (!ConfigManager.livechatConfig.serverOnline.isEmpty())
      DiscordManager.init().sendMessage(
          ConfigManager.livechatConfig.channelId,
          ConfigManager.livechatConfig.serverOnline);
  }

  public void onDisable() {
    if (!ConfigManager.livechatConfig.serverOffline.isEmpty())
      DiscordManager.init().sendMessage(
          ConfigManager.livechatConfig.channelId,
          ConfigManager.livechatConfig.serverOffline);

    if (!ConfigManager.livechatConfig.offlineDescription.isEmpty())
      DiscordManager.init().setChannelTopic(
          ConfigManager.botConfig.serverId,
          ConfigManager.livechatConfig.channelId,
          ConfigManager.livechatConfig.offlineDescription);

    DiscordManager.init().stop();
    Static.log.info("Plugin disabled");
  }

  @Override
  public void registerConfigs() {
    new ConfigManager(this);
  }

  private void registerCommands() {
    new ReloadConfigCmd<Plugin>(this, Static.Command.PermissionKeys.RELOAD, Static.Command.Names.RELOAD);
  }

  private void registerEvents() {
    this.getServer().getPluginManager().registerEvents(new PlayerChat(), this);

    if (!ConfigManager.livechatConfig.joinMessage.isEmpty())
      this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

    if (!ConfigManager.livechatConfig.leaveMessage.isEmpty())
      this.getServer().getPluginManager().registerEvents(new PlayerLeave(), this);

    if (!ConfigManager.livechatConfig.achievement.isEmpty())
      this.getServer().getPluginManager().registerEvents(new PlayerAchievement(), this);

    if (ConfigManager.livechatConfig.onDeath)
      this.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);

    if (!ConfigManager.livechatConfig.onlineCounterFormat.isEmpty())
      this.getServer().getPluginManager().registerEvents(new UpdateChannelTopic(), this);
  }
}
