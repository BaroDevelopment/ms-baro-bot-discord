
package com.baro.bot.discord.commands.owner;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import net.dv8tion.jda.api.entities.ChannelType;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.List;

public class EvalCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "eval";

    @Override
    public void execute(CommandContext ctx) {
        ScriptEngine se = new ScriptEngineManager().getEngineByName("Nashorn");
        se.put("bot", ctx.getBot());
        se.put("event", ctx.getEvent());
        se.put("message", ctx.getEvent().getMessage());
        se.put("jda", ctx.getEvent().getJDA());
        se.put("channel", ctx.getEvent().getChannel());

        if (ctx.getEvent().getChannelType() == ChannelType.TEXT) {
            se.put("guild", ctx.getEvent().getGuild());
        }
        try {
            ctx.getEvent().getChannel().sendMessage("Evaluated Successfully:\n```\n" + se.eval(ctx.getArgs()) + " ```").queue();
        } catch (Exception e) {
            ctx.getEvent().getChannel().sendMessage("An exception was thrown:\n```\n" + e + " ```").queue();
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Evaluates nashorn code (Java or JavaScript)";
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
        samples.add("`eval <code>`");
        samples.add("");
        samples.add("**__EXAMPLE 1__**\neval return 5 + 5");
        samples.add("");
        samples.add("**__EXAMPLE 2__**\n```css\neval channel.sendMessage(new EmbedBuilder()\n" +
                ".setAuthor(message.author.getName(), null, message.author.avatarUrl)\n" +
                ".setTitle(\"Field Test\")\n" +
                ".addField(\"Title - Value text\", \"YOOOOOOOO\", false)\n" +
                ".setDescription(\"Testing horizontal field\").build()).queue()\n```);");
        samples.add("");
        samples.add("**__EXAMPLE 3__**\neval Math.pow(2,5)");
        return samples;
    }
}