package com.baro.bot.discord.commands.owner;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import net.dv8tion.jda.api.entities.Activity;

import java.util.ArrayList;
import java.util.List;

public class SetgameCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "setgame";

    @Override
    public void execute(CommandContext ctx) {

        String args = ctx.getArgs().toLowerCase();
        switch (args.split(" ")[0]) {
            case "":
                printUsage(ctx);
                break;
            case "playing":
                handlePlaying(ctx);
                break;
            case "stream":
            case "streaming":
            case "twitch":
                handleStreaming(ctx);
                break;
            case "listen":
            case "listening":
                handleListening(ctx);
                break;
            case "watch":
            case "watching":
                handleWatching(ctx);
                break;
        }
    }

    private void printUsage(CommandContext ctx) {
        StringBuilder builder = new StringBuilder("Command usage:\n");
        builder.append("`" + ctx.getPrefix() + "setgame playing <title> - sets the game the bot is playing`\n");
        builder.append("`" + ctx.getPrefix() + "setgame listen <title> - sets the game the bot is listening to`\n");
        builder.append("`" + ctx.getPrefix() + "setgame watch <title> - sets the game the bot is watching`\n");
        builder.append("`" + ctx.getPrefix() + "setgame stream <username> <game> - sets the game the bot is playing to a stream`\n");
        ctx.getEvent().getChannel().sendMessage(builder.toString()).queue();
    }

    private void handleWatching(CommandContext ctx) {
        if (ctx.getArgs().split(" ", 3).length < 2) {
            ctx.getEvent().getChannel().sendMessage("Please include a title to watch!").queue();
            return;
        }
        String title = ctx.getArgs().split(" ", 2)[1];
        try {
            ctx.getEvent().getJDA().getPresence().setActivity(Activity.watching(title));
            ctx.getEvent().getChannel().sendMessage("**" + ctx.getEvent().getJDA().getSelfUser().getName() + "** is now watching `" + title + "`").queue();
        } catch (Exception e) {
            ctx.getEvent().getChannel().sendMessage("The game could not be set!").queue();
        }
    }

    private void handlePlaying(CommandContext ctx) {
        if (ctx.getArgs().split(" ", 3).length < 2) {
            ctx.getEvent().getChannel().sendMessage("Please include a title to play!").queue();
            return;
        }
        String title = ctx.getArgs().substring(7).trim();
        try {
            ctx.getEvent().getJDA().getPresence().setActivity(Activity.playing(title));
            ctx.getEvent().getChannel().sendMessage("**" + ctx.getEvent().getJDA().getSelfUser().getName()
                    + "** is now playing `" + title + "`").queue();
        } catch (Exception e) {
            ctx.getEvent().getChannel().sendMessage("The game could not be set!").queue();
        }
    }

    private void handleStreaming(CommandContext ctx) {
        if (ctx.getArgs().split(" ", 4).length < 3) {
            ctx.getEvent().getChannel().sendMessage("Please include a username and game!").queue();
            return;
        }
        String[] parts = ctx.getArgs().split(" ", 2)[1].split("\\s+", 2);
        if (parts.length < 2) {
            ctx.getEvent().getChannel().sendMessage("Please include a twitch username and the name of the game to 'stream'").queue();
            return;
        }
        try {
            ctx.getEvent().getJDA().getPresence().setActivity(Activity.streaming(parts[1], "https://twitch.tv/" + parts[0]));
            ctx.getEvent().getChannel().sendMessage("**" + ctx.getEvent().getJDA().getSelfUser().getName()
                    + "** is now streaming `" + parts[1] + "`").queue();
        } catch (Exception e) {
            ctx.getEvent().getChannel().sendMessage("The game could not be set!").queue();
        }
    }

    private void handleListening(CommandContext ctx) {
        if (ctx.getArgs().split(" ", 3).length < 2) {
            ctx.getEvent().getChannel().sendMessage("Please include a title to listen to!").queue();
            return;
        }
        String title = ctx.getArgs().toLowerCase().startsWith("to") ? ctx.getArgs().substring(2).trim() : ctx.getArgs().split(" ", 2)[1];
        try {
            ctx.getEvent().getJDA().getPresence().setActivity(Activity.listening(title));
            ctx.getEvent().getChannel().sendMessage("**" + ctx.getEvent().getJDA().getSelfUser().getName() + "** is now listening to `" + title + "`").queue();
        } catch (Exception e) {
            ctx.getEvent().getChannel().sendMessage("The game could not be set!").queue();
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Sets the game the bot is playing";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.OWNER;
    }

    @Override
    public boolean getArgs() {
        return false;
    }

    @Override
    public boolean guildOnly() {
        return false;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`setgamej playing <title>` - sets the game the bot is playing");
        samples.add("`setgamej listen <title>` - sets the game the bot is listening to");
        samples.add("`setgamej watch <title>` - sets the game the bot is watching");
        samples.add("`setgamej stream <username> <game>` - sets the game the bot is playing to a stream");
        return samples;
    }
}
