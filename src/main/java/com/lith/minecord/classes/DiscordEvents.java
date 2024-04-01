package com.lith.minecord.classes;

import javax.annotation.Nonnull;
import com.lith.minecord.Static;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class DiscordEvents implements EventListener {
    @Override
    public void onEvent(@Nonnull GenericEvent event) {
        if (event instanceof ReadyEvent) {
            Static.log.info("API is ready!");
        }
    }
}
