package com.baro.bot.discord.commands.owner;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LeaveServerCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "leaveserver";


    @Override
    public void execute(CommandContext ctx) {
        Guild guild = ctx.getBot().getJda().getGuildById(ctx.getArgs());
        if (guild == null) {
            sendError(ctx, "Server not found.");
        } else {
            sendSuccess(ctx, "Successfully left " + guild.getName());
            guild.leave().queue();
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Let the bot leave a Server";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.OWNER;
    }

    @Override
    public boolean getArgs() {
        return true;
    }

    @Override
    public boolean guildOnly() {
        return false;
    }

    @Override
    public boolean isNsfw() {
        return false;
    }

    @Override
    public EmbedBuilder getUsage() {
        return null;
    }

    @Override
    public List<String> getExamples() {
        return Collections.singletonList("leaveguild <guild_id>");
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("ls", "leaveguild", "lg");
    }

    @Override
    public long getCooldown() {
        return 0;
    }

    @Override
    public String getDocumentationUrl() {
        return null;
    }
}
