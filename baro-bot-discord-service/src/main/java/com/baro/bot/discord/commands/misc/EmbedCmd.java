package com.baro.bot.discord.commands.misc;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbedCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "embed";
    public static final Logger LOGGER = LoggerFactory.getLogger(EmbedCmd.class);

    @Override
    public void execute(CommandContext ctx) {
        DataObject
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Generate a beautiful and easy to create embed powered by [Embed-Visualizer](https://leovoel.github.io/embed-visualizer/)";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MISC;
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
    public long getCooldown() {
        return 5;
    }
}
