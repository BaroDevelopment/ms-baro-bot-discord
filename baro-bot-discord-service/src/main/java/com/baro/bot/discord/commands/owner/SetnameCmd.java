package com.baro.bot.discord.commands.owner;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import net.dv8tion.jda.api.exceptions.RateLimitedException;

import java.util.ArrayList;
import java.util.List;

public class SetnameCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "setname";

    @Override
    public void execute(CommandContext ctx) {
        try {
            String oldname = ctx.getEvent().getJDA().getSelfUser().getName();
            ctx.getEvent().getJDA().getSelfUser().getManager().setName(ctx.getArgs()).complete(false);
            ctx.getEvent().getChannel().sendMessage("Name changed from `" + oldname + "` to `" + ctx.getArgs() + "`").queue();
        } catch (RateLimitedException e) {
            ctx.getEvent().getChannel().sendMessage("Name can only be changed twice per hour!").queue();
        } catch (Exception e) {
            ctx.getEvent().getChannel().sendMessage("That name is not valid!").queue();
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Sets the name of the bot";
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
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`setnamej <name>`");
        return samples;
    }
}
