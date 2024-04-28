package com.lith.minecord;

import com.lith.lithcore.abstractClasses.AbstractPlugin;
import com.lith.lithcore.helpers.ReloadConfigCmd;
import com.lith.minecord.classes.DiscordManager;
import com.lith.minecord.config.ConfigManager;
import com.lith.minecord.events.minecraft.ServerEvent;
import lombok.Getter;

public class MineCordPlugin extends AbstractPlugin<MineCordPlugin, ConfigManager> {
  @Getter
  private static MineCordPlugin plugin = null;
  @Getter
  private static DiscordManager discordManager = null;

  @Override
  public void onEnable() {
    plugin = this;
    configs = new ConfigManager(this);
    super.onEnable();
  }

  @Override
  public void onDisable() {
    if (discordManager != null)
      discordManager.stop();

    super.onDisable();
  }

  @Override
  public void reloadConfigs() {
    super.reloadConfigs();
    registerEvents();

    if (discordManager.isOnline())
      discordManager.stop();

    if (!discordManager.isOnline())
      discordManager.start();
  }

  @Override
  protected void registerConfigs() {
    super.registerConfigs();

    if (discordManager == null)
      discordManager = new DiscordManager(this);
  }

  @Override
  protected void registerEvents() {
    registerEvent(new ServerEvent(), true);
  }

  @Override
  protected void registerCommands() {
    new ReloadConfigCmd<MineCordPlugin>(this, Static.Command.PermissionKeys.RELOAD, Static.Command.Names.RELOAD);
  }
}
