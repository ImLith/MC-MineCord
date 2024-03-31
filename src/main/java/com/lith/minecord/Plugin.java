package com.lith.minecord;

import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.minecord.config.ConfigManager;

public class Plugin extends MainPlugin<ConfigManager> {
  public static Plugin plugin;

  public void onEnable() {
    Plugin.plugin = this;

    Static.log.info("Plugin enabled");
  }

  public void onDisable() {
    Static.log.info("Plugin disabled");
  }
}
