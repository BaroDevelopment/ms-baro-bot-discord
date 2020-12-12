package com.baro.bot.discord.commands.moderation;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class PurgeCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "purge";
    public static final Logger LOGGER = LoggerFactory.getLogger(PurgeCmd.class);


    @Override
    public void execute(CommandContext ctx) {
        PurgeParams purgeParams = new PurgeParams();
        JCommander.newBuilder()
                .addObject(purgeParams)
                .build()
                .parse(ctx.getArgs().split(" "));
        int amount = getAmount(ctx, purgeParams);

        if (ctx.getInvoke().equalsIgnoreCase("purge")) { // purge
            purge(ctx, amount);
        } else { // delete
            delete(ctx, amount);
        }
    }

    /**
     * -purge      Delete messages (max 500) this is slow
     *
     * @param ctx
     * @param amount
     */
    private void purge(CommandContext ctx, int amount) {
        ctx.getEvent().getTextChannel().getIterableHistory().takeAsync(amount).thenApply(messages -> {
            for (Message message : messages) {
                message.delete().queue();
            }
            return null;
        }).thenApply(o -> {
            sendSuccess(ctx, "Succesfully deleted **" + amount + "** messages.");
            return null;
        });
    }

    /**
     * -delete     Bulk delete 100 messages and they must not be older than 2 weeks
     *
     * @param ctx
     * @param amount
     */
    private void delete(CommandContext ctx, int amount) {
        EmbedBuilder eb = new EmbedBuilder();
        int adjustedAmount = Math.min(100, amount);
        new MessageHistory(ctx.getEvent().getTextChannel()).retrievePast(adjustedAmount).queue(messages -> {
            try {
                ctx.getEvent().getTextChannel().deleteMessages(messages).queue(unused -> {
                    sendSuccess(ctx, "Succesfully deleted **" + adjustedAmount + "** messages.");
                });
            } catch (IllegalArgumentException ex) {
                eb.setColor(Color.RED).setDescription("You tried to delete at least a message older than 2 weeks. " +
                        "This is not possible! It's a Discord restriction.");
                ctx.getEvent().getTextChannel().sendMessage(eb.build()).queue();
            }
        });
    }

    private int getAmount(CommandContext ctx, PurgeParams args) {
        int amount = args.amount;
        try {
            amount = Math.min(Integer.parseInt(ctx.getArgs().split(" ")[0]), 500);
            return amount;
        } catch (NumberFormatException e) {
            return amount;
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Bulk-delete messages from a channel";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MODERATION;
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
        return Arrays.asList("prune", "delete", "wipe");
    }

    @Nullable
    @Override
    public EnumSet<Permission> getMemberPermissions() {
        return EnumSet.of(Permission.MESSAGE_MANAGE);
    }

    @Nullable
    @Override
    public EnumSet<Permission> getBotPermissions() {
        return EnumSet.of(Permission.MESSAGE_MANAGE);
    }

    @Override
    public long getCooldown() {
        return 60;
    }

    private class PurgeParams {
        @Parameter
        private List<String> main;
        @Parameter(names = {"-n", "-a", "-amount"})
        private int amount = 10;
        @Parameter(names = {"-p", "-pinned"})
        private boolean pinned = false;
        @Parameter(names = {"-b", "-bots"})
        private boolean bots = false;
    }
}
