package com.lith.minecord.interfaces;

import org.jetbrains.annotations.NotNull;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface ISlashCommand {
    @NotNull
    String getCommandName();

    @NotNull
    String getCommandDescription();

    void run(SlashCommandInteractionEvent event);
}
