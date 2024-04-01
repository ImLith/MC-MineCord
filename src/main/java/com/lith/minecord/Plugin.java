package com.lith.minecord;

import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.minecord.config.ConfigManager;
import com.lith.minecord.discord.DiscordManager;
import com.lith.minecord.events.PlayerEvents;

public class Plugin extends MainPlugin<ConfigManager> {
  public static Plugin plugin;

  public void onEnable() {
    Plugin.plugin = this;

    registerConfig();
    DiscordManager.init().start();
    registerEvents();

    Static.log.info("Plugin enabled");
  }

  public void onDisable() {
    DiscordManager.init().stop();
    Static.log.info("Plugin disabled");
  }

  public void registerConfig() {
    new ConfigManager(this);
  }

  private void registerEvents() {
    this.getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
  }
}
