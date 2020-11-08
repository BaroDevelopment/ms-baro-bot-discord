package com.baro.bot.discord.commands.owner;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;

import java.util.ArrayList;
import java.util.List;


public class ShutdownCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "shutdown";

    @Override
    public void execute(CommandContext ctx) {
        ctx.getEvent().getChannel().sendMessage("Shutting down ...").queue();
        ctx.getBot().shutdown();
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Safely shuts down";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.OWNER;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("bb");
        return aliases;
    }

    @Override
    public boolean getArgs() {
        return false;
    }

    @Override
    public boolean guildOnly() {
        return false;
    }
}
