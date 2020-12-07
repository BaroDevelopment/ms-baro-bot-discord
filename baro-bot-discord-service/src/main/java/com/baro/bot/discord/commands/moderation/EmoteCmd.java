package com.baro.bot.discord.commands.moderation;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Icon;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.EnumSet;

public class EmoteCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "emote";
    public static final Logger LOGGER = LoggerFactory.getLogger(EmoteCmd.class);

    @Override
    public void execute(CommandContext ctx) {
        try {
            String name = ctx.getArgs().split(" ")[0];
            String url = ctx.getArgs().split(" ")[1];
            ctx.getEvent().getGuild().createEmote(name, Icon.from(new URL(url).openStream())).queue();
            sendSuccess(ctx, "Successfully added new emote " + name);
        } catch (Exception e) {
            sendError(ctx, "Failed to add Emote: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Add emotes to your server";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MODERATION;
    }

    @Override
    public boolean getArgs() {
        return true;
    }

    @Override
    public boolean guildOnly() {
        return true;
    }

    @Override
    public boolean isNsfw() {
        return false;
    }

    @Nullable
    @Override
    public EnumSet<Permission> getMemberPermissions() {
        return EnumSet.of(Permission.MANAGE_EMOTES);
    }

    @Nullable
    @Override
    public EnumSet<Permission> getBotPermissions() {
        return EnumSet.of(Permission.MANAGE_EMOTES);
    }

    @Override
    public long getCooldown() {
        return 5;
    }
}
