package com.baro.bot.discord.commands.owner;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import net.dv8tion.jda.api.OnlineStatus;

import java.util.ArrayList;
import java.util.List;

public class SetstatusCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "setstatus";

    @Override
    public void execute(CommandContext ctx) {
        try {
            OnlineStatus status = OnlineStatus.fromKey(ctx.getArgs());
            if (status == OnlineStatus.UNKNOWN) {
                ctx.getEvent().getChannel().sendMessage("Please include one of the following statuses: `ONLINE`, `IDLE`, `DND`, `INVISIBLE`").queue();
            } else {
                ctx.getEvent().getJDA().getPresence().setStatus(status);
                ctx.getEvent().getChannel().sendMessage("Set the status to `" + status.getKey().toUpperCase() + "`").queue();
            }
        } catch (Exception e) {
            ctx.getEvent().getChannel().sendMessage("The status could not be set!").queue();
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Sets the status the bot displays";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.OWNER;
    }

    @Override
    public boolean guildOnly() {
        return false;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`setstatusj ONLINE|INVISIBLE|DND|IDLE`");
        return samples;
    }
}
