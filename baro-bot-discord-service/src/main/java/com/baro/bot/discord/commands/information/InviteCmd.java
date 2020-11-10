
package com.baro.bot.discord.commands.information;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import net.dv8tion.jda.api.Permission;

import java.util.Arrays;
import java.util.List;

public class InviteCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "invite";

    @Override
    public void execute(CommandContext ctx) {
        String inviteUrl = ctx.getBot().getJda().getInviteUrl(Permission.ADMINISTRATOR);
        ctx.getEvent().getChannel().sendMessage(inviteUrl).queue();
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Get bot's invite url";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.INFORMATION;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("inv");
    }

    @Override
    public long getCooldown() {
        return 60;
    }
}
