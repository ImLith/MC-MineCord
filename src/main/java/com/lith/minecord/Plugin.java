package com.lith.minecord;

import com.lith.lithcore.abstractClasses.AbstractPlugin;
import com.lith.lithcore.helpers.ReloadConfigCmd;
import com.lith.minecord.classes.DiscordManager;
import com.lith.minecord.config.ConfigManager;
import com.lith.minecord.events.minecraft.PlayerAchievement;
import com.lith.minecord.events.minecraft.PlayerChat;
import com.lith.minecord.events.minecraft.PlayerDeath;
import com.lith.minecord.events.minecraft.PlayerJoin;
import com.lith.minecord.events.minecraft.PlayerLeave;
import com.lith.minecord.utils.DcMessageUtil;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class Plugin extends AbstractPlugin<Plugin, ConfigManager> {
  public void onEnable() {
    configs = new ConfigManager(this);
    DiscordManager.setPlugin(this);
    super.onEnable();
  }

  @Override
  public void onDisable() {
    if (isValid() && !ConfigManager.dcMsg.serverOff.isEmpty())
      DiscordManager.init().sendMessage(
          Static.textChannel,
          ConfigManager.dcMsg.serverOff);

    DiscordManager.init().stop();
    super.onDisable();
  }

  @Override
  protected void preRegisterRunnables() {
    if (isValid() && !ConfigManager.dcMsg.serverOn.isEmpty()) {
      DiscordManager.init().sendMessage(
          Static.textChannel,
          ConfigManager.dcMsg.serverOn);
    }
  }

  @Override
  public void registerConfigs() {
    Static.textChannel = null;

    unregisterAllEvents();
    if (!DiscordManager.init().isOnline())
      DiscordManager.init().start();

    validateChannel();
  }

  @Override
  protected void registerCommands() {
    new ReloadConfigCmd<Plugin>(this, Static.Command.PermissionKeys.RELOAD, Static.Command.Names.RELOAD);
  }

  @Override
  protected void registerEvents() {
    if (!isValid()) {
      log.warning("Text channel not found! Check your configs");
      return;
    }

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

  private void validateChannel() {
    TextChannel channel = DiscordManager.init().getClient().getTextChannelById(ConfigManager.botConfig.channelId);
    Static.textChannel = DcMessageUtil.isInTextChannel(channel) ? channel : null;
  }

  private Boolean isValid() {
    return Static.textChannel != null;
  }
}
