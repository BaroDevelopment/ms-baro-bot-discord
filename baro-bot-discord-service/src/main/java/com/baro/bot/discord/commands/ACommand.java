package com.baro.bot.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public abstract class ACommand {

    public void sendSuccess(CommandContext ctx, String text) {
        EmbedBuilder eb = new EmbedBuilder().setColor(Color.GREEN);
        eb.setDescription(text);
        ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
    }

    public void sendError(CommandContext ctx, String text) {
        EmbedBuilder eb = new EmbedBuilder().setColor(Color.RED);
        eb.setDescription(text);
        ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
    }

    public void sendWarning(CommandContext ctx, String text) {
        EmbedBuilder eb = new EmbedBuilder().setColor(Color.ORANGE);
        eb.setDescription(text);
        ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
    }

    public void replayDM(CommandContext ctx, EmbedBuilder eb) {
        ctx.getEvent().getAuthor().openPrivateChannel().queue(
                privateChannel -> privateChannel.sendMessage(eb.build()).queue(),
                throwable -> ctx.getEvent().getChannel().sendMessage(eb.build()).queue()
        );
    }

    public void replayDM(CommandContext ctx, String message) {
        ctx.getEvent().getAuthor().openPrivateChannel().queue(
                privateChannel -> privateChannel.sendMessage(message).queue(),
                throwable -> ctx.getEvent().getChannel().sendMessage(message).queue()
        );
    }
}
