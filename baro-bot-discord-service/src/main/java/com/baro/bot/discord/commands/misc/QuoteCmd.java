package com.baro.bot.discord.commands.misc;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.util.ColorUtil;
import com.baro.bot.discord.util.FormatUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class QuoteCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "quote";
    public static final Logger LOGGER = LoggerFactory.getLogger(QuoteCmd.class);

    @Override
    public void execute(CommandContext ctx) {
        ctx.getEvent().getTextChannel().sendMessage(new EmbedBuilder()
                .setColor(new ColorUtil().getRandomColor())
                .setDescription("\u231B searching message ...")
                .build()).queue(waitingMessage -> {
            Message target = findMessage(ctx);
            if (target == null)
                waitingMessage.editMessage(new EmbedBuilder().setColor(Color.RED).setDescription("Message not found.").build()).queue();
            else printQuote(target, waitingMessage);
        });
    }

    private Message findMessage(CommandContext ctx) {
        // find message by message-id
        CompletableFuture<Message> messageById = null;

        if (ctx.getArgs().split(" ").length == 1) {
            messageById = findMessageById(ctx.getBot().getJda(), ctx.getEvent().getTextChannel().getId(), ctx.getArgs());
        }

        if (messageById == null) {
            String channelId = ctx.getArgs().split(" ")[0];
            String messageId = ctx.getArgs().split(" ")[1];
            messageById = findMessageById(ctx.getBot().getJda(), channelId, messageId);
        }

        if (messageById != null) {
            try {
                return messageById.get();
            } catch (InterruptedException | ExecutionException e) {
                // continue
            }
        }

        // find message by query (text in message)
        Message messageByQuery = null;

        try {
            String textChannelId = ctx.getArgs().split(" ")[0];
            String[] query = Arrays.copyOfRange(ctx.getArgs().split(" "), 1, ctx.getArgs().split(" ").length);
            messageByQuery = findMessageByText(ctx.getBot().getJda(), textChannelId, String.join(" ", query), ctx.getEvent().getMessageId());

            if (messageByQuery == null)
                messageByQuery = findMessageByText(ctx.getBot().getJda(), ctx.getEvent().getTextChannel().getId(), ctx.getArgs(), ctx.getEvent().getMessageId());
        } catch (Exception e) {
            messageByQuery = findMessageByText(ctx.getBot().getJda(), ctx.getEvent().getTextChannel().getId(), ctx.getArgs(), ctx.getEvent().getMessageId());
        }

        return messageByQuery;
    }

    private void printQuote(Message toQuote, Message waitingMessage) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor());

        if (!toQuote.getEmbeds().isEmpty()) {
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.append("**" + toQuote.getAuthor().getName() + "#" + toQuote.getAuthor().getDiscriminator() + "**\n" +
                    toQuote.getContentRaw());
            messageBuilder.setEmbed(toQuote.getEmbeds().get(0));
            waitingMessage.editMessage(messageBuilder.build()).queue();
            return;
        }
        if (!toQuote.getAttachments().isEmpty()) {
            EmbedBuilder attach = new EmbedBuilder();
            attach.setColor(new ColorUtil().getRandomColor())
                    .setDescription(toQuote.getContentRaw())
                    .setAuthor(toQuote.getAuthor().getName() + "#" + toQuote.getAuthor().getDiscriminator(),
                            null, toQuote.getAuthor().getEffectiveAvatarUrl())
                    .setFooter(toQuote.getGuild().getName() + " #" + toQuote.getTextChannel().getName() + " |  " +
                            FormatUtil.getDateAndTimestamps(toQuote.getTimeCreated()), toQuote.getGuild().getIconUrl());
            if (toQuote.getAttachments().get(0).isImage())
                attach.setImage(toQuote.getAttachments().get(0).getUrl());
            else
                attach.addField("Attachmand", "[" + toQuote.getAttachments().get(0).getFileName() + "]"
                        + toQuote.getAttachments().get(0).getUrl(), true);
            waitingMessage.editMessage(attach.build()).queue();
            return;
        }

        eb.setAuthor(toQuote.getAuthor().getName(), null, toQuote.getAuthor().getEffectiveAvatarUrl());
        eb.setDescription(toQuote.getContentRaw());
        eb.setFooter(toQuote.getGuild().getName() + " #" + toQuote.getTextChannel().getName() + " |  " +
                FormatUtil.getDateAndTimestamps(toQuote.getTimeCreated()), toQuote.getGuild().getIconUrl());

        waitingMessage.editMessage(eb.build()).queue();

    }

    @Nullable
    private CompletableFuture<Message> findMessageById(JDA api, String channelId, String messageId) {
        if (!channelId.matches("-?\\d+") || !messageId.matches("-?\\d+")) return null;
        TextChannel channel = api.getTextChannelById(channelId);
        if (channel == null) return null;
        return channel.retrieveMessageById(messageId).submit();
    }

    @Nullable
    private Message findMessageByText(JDA api, String channelId, String message, String eventMessageId) {
        if (!channelId.matches("-?\\d+")) return null;
        TextChannel channel = api.getTextChannelById(channelId);
        if (channel == null) return null;
        try {
            List<Message> msgs = channel.getIterableHistory().takeAsync(500).get();
            for (int i = 0; i < msgs.size(); i++) {
                if (msgs.get(i).getContentDisplay().toLowerCase().trim().contains(message.toLowerCase().trim()) && !eventMessageId.equals(msgs.get(i).getId()))
                    return msgs.get(i);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Quote a message";
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
        return Arrays.asList("q");
    }

    @Override
    public long getCooldown() {
        return 30;
    }
}
