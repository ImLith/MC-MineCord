package com.lith.minecord;

import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.minecord.classes.DiscordManager;
import com.lith.minecord.config.ConfigManager;

public class Plugin extends MainPlugin<ConfigManager> {
  public static Plugin plugin;

  public void onEnable() {
    Plugin.plugin = this;

    registerConfig();

    Static.log.info("Plugin enabled");

    DiscordManager.init().start();
  }

  public void onDisable() {
    Static.log.info("Plugin disabled");
  }

  public void registerConfig() {
    new ConfigManager(this);
  }
}
