package com.lith.minecord.utils;

import net.dv8tion.jda.api.events.message.GenericMessageEvent;

public class DiscordEventUtil {
    public static Boolean isGuildEvent(GenericMessageEvent event) {
        return event.isFromGuild();
    }
}
