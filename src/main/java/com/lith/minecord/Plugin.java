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
import lombok.Getter;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class Plugin extends AbstractPlugin<Plugin, ConfigManager> {
  @Getter
  private DiscordManager discordManager = null;

  @Override
  public void onEnable() {
    configs = new ConfigManager(this);
    super.onEnable();
  }

  @Override
  public void onDisable() {
    if (discordManager != null) {
      if (isValid() && !configs.dcMsg.serverOff.isEmpty())
        discordManager.sendMessage(
            Static.textChannel,
            configs.dcMsg.serverOff);

      discordManager.stop();
    }

    super.onDisable();
  }

  @Override
  public void reloadConfigs() {
    super.reloadConfigs();
    registerEvents();
  }

  @Override
  protected void registerConfigs() {
    super.registerConfigs();
    Static.textChannel = null;

    unregisterAllEvents();

    if (discordManager == null)
      discordManager = new DiscordManager(this);

    if (!discordManager.isOnline())
      discordManager.start();

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

    if (!configs.dcMsg.format.isEmpty())
      registerEvent(new PlayerChat(this));

    if (!configs.dcMsg.join.isEmpty())
      registerEvent(new PlayerJoin(this));

    if (!configs.dcMsg.leave.isEmpty())
      registerEvent(new PlayerLeave(this));

    if (!configs.dcMsg.achievement.isEmpty())
      registerEvent(new PlayerAchievement(this));

    if (configs.dcMsg.onDeath)
      registerEvent(new PlayerDeath(this));
  }

  @Override
  protected void preRegisterRunnables() {
    if (isValid() && !configs.dcMsg.serverOn.isEmpty()) {
      discordManager.sendMessage(
          Static.textChannel,
          configs.dcMsg.serverOn);
    }
  }

  private void validateChannel() {
    TextChannel channel = discordManager.getClient().getTextChannelById(configs.botConfig.channelId);
    Static.textChannel = DcMessageUtil.isInTextChannel(channel) ? channel : null;
  }

  private Boolean isValid() {
    return Static.textChannel != null;
  }
}
