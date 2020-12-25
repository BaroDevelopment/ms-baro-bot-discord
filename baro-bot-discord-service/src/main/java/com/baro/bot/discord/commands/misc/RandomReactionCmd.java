package com.baro.bot.discord.commands.misc;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class RandomReactionCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "randomreaction";
    public static final Logger LOGGER = LoggerFactory.getLogger(RandomReactionCmd.class);
    public static final int MAX_RANDOM_USERS = 30;

    @Override
    public void execute(CommandContext ctx) {
        CompletableFuture<Message> waitMessage = sendWaitMessage(ctx, "Calculating ... please wait.");
        ctx.getEvent().getGuild().loadMembers().onSuccess(members -> {
            RandomReactionParams params = new RandomReactionParams();
            JCommander.newBuilder()
                    .addObject(params)
                    .build()
                    .parse(ctx.getArgs().split(" "));

            ctx.getEvent().getChannel().retrieveMessageById(params.main).queue(message -> {
                int count = message.getReactions().get(0).getCount();
                List<MessageReaction> reactions = message.getReactions();
                if (reactions.isEmpty()) {
                    sendError(ctx, "Message has no reactions. Please notice the difference between a reaction and emotes!");
                } else if (reactions.size() > 1) {
                    sendError(ctx, "Message has more than one reaction. Please remove all reactions and keep only one.");
                } else {
                    try {
                        List<User> usersReacted = reactions.get(0).retrieveUsers().takeAsync(count).get();
                        List<User> randomUsers = takeRandomUsers(usersReacted, members, Math.min(Math.min(params.amount, count), MAX_RANDOM_USERS));

                        if (randomUsers.isEmpty()) {
                            sendError(ctx, "The reacted users are not in the server");
                            return;
                        }
                        announceRandomUsers(randomUsers, ctx.getEvent().getTextChannel(), waitMessage);
                    } catch (InterruptedException | ExecutionException e) {
                        sendError(ctx, "Something went wrong.");
                    }
                }
            }, throwable -> sendError(ctx, "Message not found"));
        });
    }

    private void announceRandomUsers(List<User> users, TextChannel channel, CompletableFuture<Message> waitMessage) {
        for (User user : users) {
            EmbedBuilder eb = new EmbedBuilder()
                    .setTitle("RANDOM USER")
                    .addField("Name", user.getName(), true)
                    .addField("ID", user.getId(), true)
                    .addField("Mention", user.getAsMention(), true)
                    .setThumbnail(user.getEffectiveAvatarUrl());
            channel.sendMessage(eb.build()).queue();
        }
        waitMessage.thenAccept(message -> message.delete().queue());
    }

    private List<User> takeRandomUsers(List<User> usersTotal, List<Member> members, int amount) {
        List<User> usersInGuild = usersTotal.stream().filter(user -> userInGuild(user, members)).distinct().collect(Collectors.toList());
        Collections.shuffle(usersInGuild);
        return usersInGuild.subList(0, Math.min(usersInGuild.size(), amount));
    }

    private boolean userInGuild(User user, List<Member> members) {
        return members.stream().map(ISnowflake::getId)
                .collect(Collectors.toList())
                .contains(user.getId());
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Picks a random reacted user from a message";
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
        return true;
    }

    @Override
    public boolean isNsfw() {
        return false;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("rr");
    }

    @Override
    public long getCooldown() {
        return 5;
    }

    private class RandomReactionParams {
        @Parameter
        private String main;
        @Parameter(names = {"-a", "-amount", "-n"})
        private int amount = 1;
    }
}
