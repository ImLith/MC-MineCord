package com.lith.minecord;

import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.minecord.classes.DiscordManager;
import com.lith.minecord.config.ConfigManager;

public class Plugin extends MainPlugin<ConfigManager> {
  public static Plugin plugin;

  public void onEnable() {
    Plugin.plugin = this;

    registerConfig();

    DiscordManager.init().start();
    Static.log.info("Plugin enabled");
  }

  public void onDisable() {
    DiscordManager.init().stop();
    Static.log.info("Plugin disabled");
  }

  public void registerConfig() {
    new ConfigManager(this);
  }
}
