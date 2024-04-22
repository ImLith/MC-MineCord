package com.lith.minecord.config;

import com.lith.lithcore.abstractClasses.AbstractConfigManager;
import com.lith.minecord.MineCordPlugin;
import com.lith.minecord.Static.ConfigKeys;
import lombok.Getter;

public class ConfigManager extends AbstractConfigManager<MineCordPlugin, ConfigManager> {
    @Getter
    private String token = null;
    @Getter
    private Long serverId = null;
    @Getter
    private String inviteLink = null;

    public ConfigManager(final MineCordPlugin plugin) {
        super(plugin);
    }

    @Override
    public void load() {
        super.load();

        token = config.getString(ConfigKeys.TOKEN);
        serverId = config.getLong(ConfigKeys.SERVER_ID);
        inviteLink = config.getString(ConfigKeys.INVITE_LINK);
    }
}
