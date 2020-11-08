package com.baro.bot.discord.commands.misc;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PollCmd implements ICommand {

    public static final String COMMAND_NAME = "poll";

    @Override
    public void execute(CommandContext ctx) {
        String[] splittedResult = ctx.getArgs().trim().split("\\|");
        List<String> answerList = new ArrayList<>();
        for (String s : splittedResult) {
            s.trim();
            answerList.add(s);
        }

        String question = answerList.get(0); // first arg is question
        answerList.remove(0); // remove the question from list ... now only answers there

        List<String> emojiListString = new ArrayList<>();
        List<String> emojiListUniCode = new ArrayList<>();

        emojiListString.add(":one:");
        emojiListString.add(":two:");
        emojiListString.add(":three:");
        emojiListString.add(":four:");
        emojiListString.add(":five:");
        emojiListString.add(":six:");
        emojiListString.add(":seven:");
        emojiListString.add(":eight:");
        emojiListString.add(":nine:");

        emojiListUniCode.add("\u0031\u20E3");
        emojiListUniCode.add("\u0032\u20E3");
        emojiListUniCode.add("\u0033\u20E3");
        emojiListUniCode.add("\u0034\u20E3");
        emojiListUniCode.add("\u0035\u20E3");
        emojiListUniCode.add("\u0036\u20E3");
        emojiListUniCode.add("\u0037\u20E3");
        emojiListUniCode.add("\u0038\u20E3");
        emojiListUniCode.add("\u0039\u20E3");
        emojiListUniCode.add("\uD83D\uDD1F");

        String line = ctx.getEvent().getMessage().getContentDisplay();
        int count = line.length() - line.replace("|", "").length();
        if (count > 9) {
            ctx.getEvent().getChannel().sendMessage("Max. only 9 answeres allowed.").queue();
        } else {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setColor(Color.orange)
                    .setAuthor(ctx.getEvent().getAuthor().getName() + "'s POLL ", null, ctx.getEvent().getAuthor().getEffectiveAvatarUrl());

            String answers = "**:pencil: " + question + "**" + "\n\n";
            for (int i = 0; i < answerList.size(); i++) {
                answers += "\n" + emojiListString.get(i) + "  " + answerList.get(i).trim() + "\n";
            }

            embedBuilder.setDescription(answers);

            Message m = ctx.getEvent().getTextChannel().sendMessage(embedBuilder.build()).complete();

            //add reactions to the message
            for (int i = 0; i < answerList.size(); i++) {
                m.addReaction(emojiListUniCode.get(i)).queue();
            }
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Vote System using Discord reactions.";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.INFORMATION;
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
    public EmbedBuilder getUsage() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Usage");
        eb.setDescription("`poll <QUESTION | 1. Answer | 2. Answer | 3. Answer ....>`");
        return eb;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`\", \"__Example__:\\n\" + prefix + \"poll Do you love me? | YES | NO | MAYBE`");
        return samples;
    }
}
