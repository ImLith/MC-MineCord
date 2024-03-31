package com.lith.minecord.classes;

import com.lith.minecord.Static;

import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

public class DiscordEvents {
    public static Mono<Object> onReady(ReadyEvent event) {
        return Mono.fromRunnable(() -> {
            final User self = event.getSelf();

            Static.log.info("Logged in as " + self.getUsername());
        });
    }
}
