package com.lith.minecord;

import java.util.logging.Logger;

import com.lith.lithcore.abstractClasses.AbstractConfigKey;

public class Static {
    public static final String pluginName = "MineCord";
    public static final Logger log = Logger.getLogger(Static.pluginName);

    final public static class ConfigKeys {
        public static final class Bot extends AbstractConfigKey {
            public static final String TOKEN = setKey("token");
            public static final String GUILD_ID = setKey("guild_id");
        }
    }
}
