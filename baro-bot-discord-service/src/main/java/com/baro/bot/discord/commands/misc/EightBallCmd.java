package com.baro.bot.discord.commands.misc;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class EightBallCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "eightball";
    public static final Logger LOGGER = LoggerFactory.getLogger(EightBallCmd.class);
    public static final String[] positiveAnswers = {"Absolutely!", "Yes, sure.", "Of course!"};
    public static final String[] negativeAnswers = {"Nah...", "No, really, no...", "I highly doubt that!"};

    @Override
    public void execute(CommandContext ctx) {
        EmbedBuilder eb = new EmbedBuilder();
        String[] mergedAnswers = Stream.concat(Arrays.stream(positiveAnswers.clone()), Arrays.stream(negativeAnswers.clone()))
                .toArray(String[]::new);
        int randInt = new Random().nextInt(mergedAnswers.length);
        String answer = mergedAnswers[randInt];
        ctx.getEvent().getChannel().sendMessage(eb.setDescription(answer)
                .setColor(getColor(answer)).build()).queue();
    }

    private Color getColor(String answer) {
        return Arrays.asList(positiveAnswers).contains(answer) ? Color.GREEN : Color.RED;
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Ask the magic 8ball a special question!";
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
    public List<String> getAliases() {
        return Arrays.asList("8ball", "8b");
    }

    @Override
    public long getCooldown() {
        return 5;
    }
}
