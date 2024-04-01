package com.lith.minecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;
import com.lith.lithcore.abstractClasses.AbstractConfigKey;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Static {
    public static final String pluginName = "MineCord";
    public static final Logger log = Logger.getLogger(Static.pluginName);

    final public static class ConfigKeys {
        public static final class Bot extends AbstractConfigKey {
            public static final String TOKEN = setKey("token");
        }

        public static final class Livechat extends AbstractConfigKey {
            public static final String CHANNEL_ID = setKey("channel_id");
        }
    }

    public static final Collection<GatewayIntent> gatewayIntents = Collections
            .unmodifiableCollection(Arrays.asList(GatewayIntent.MESSAGE_CONTENT));
}
