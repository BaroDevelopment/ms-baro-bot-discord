package com.baro.bot.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public interface ICommand {

    void execute(CommandContext ctx);

    String getName();

    String getDescription();

    CommandCategory getCategory();

    default boolean getArgs() {
        return false;
    }

    default boolean guildOnly() {
        return true;
    }

    default boolean isNsfw() {
        return false;
    }


    default EmbedBuilder getUsage() {
        return new EmbedBuilder();
    }

    default List<String> getExamples() {
        return Collections.emptyList();
    }

    default List<String> getAliases() {
        return Collections.emptyList();
    }

    @Nullable
    default EnumSet<Permission> getMemberPermissions() {
        return null;
    }

    @Nullable
    default EnumSet<Permission> getBotPermissions() {
        return null;
    }

    default long getCooldown() {
        return 10;
    }

    default String getDocumentationUrl() {
        return "";
    }
}
