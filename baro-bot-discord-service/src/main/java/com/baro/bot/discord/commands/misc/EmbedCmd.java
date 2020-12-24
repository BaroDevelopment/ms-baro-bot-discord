package com.baro.bot.discord.commands.misc;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.model.api.discord.embed.DiscordEmbedModel;
import com.baro.bot.discord.model.api.discord.embed.EmbedFieldModel;
import com.baro.bot.discord.model.api.discord.embed.EmbedModel;
import com.baro.bot.discord.util.ColorUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

public class EmbedCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "embed";
    public static final Logger LOGGER = LoggerFactory.getLogger(EmbedCmd.class);

    @Override
    public void execute(CommandContext ctx) {
        try {
            DiscordEmbedModel embedModel = new ObjectMapper().readValue(ctx.getArgs(), DiscordEmbedModel.class);
            ctx.getEvent().getChannel().sendMessage(jsonToEmbed(embedModel, ctx.getArgs())).queue();
        } catch (Exception e) {
            EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor()).setDescription(ctx.getArgs());
            ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
        }
    }

    private Message jsonToEmbed(DiscordEmbedModel o, String json) {
        EmbedBuilder eb = new EmbedBuilder();
        EmbedModel em = o.getEmbed();
        // description
        if (em.getDescription() != null)
            eb.setDescription(em.getDescription());
        // title
        if (em.getTitle() != null)
            eb.setTitle(em.getTitle());
        // color
        if (em.getColor() != null)
            eb.setColor(em.getColor());
        // image
        if (em.getImage() != null && em.getImage().getUrl() != null)
            eb.setImage(em.getImage().getUrl());
        // thumbnail
        if (em.getThumbnail() != null && em.getThumbnail().getUrl() != null)
            eb.setThumbnail(em.getThumbnail().getUrl());
        // footer
        if (em.getFooter() != null && em.getFooter().getIcon_url() != null)
            eb.setFooter(em.getFooter().getText(), em.getFooter().getIcon_url());
        else if (em.getFooter() != null && em.getFooter().getText() != null)
            eb.setFooter(em.getFooter().getText());
        // author
        if (em.getAuthor() != null && em.getAuthor().getName() != null && em.getAuthor().getUrl() != null && em.getAuthor().getIcon_url() != null)
            eb.setAuthor(em.getAuthor().getName(), em.getAuthor().getUrl(), em.getAuthor().getIcon_url());
        else if (em.getAuthor() != null && em.getAuthor().getName() != null && em.getAuthor().getUrl() != null)
            eb.setAuthor(em.getAuthor().getName(), em.getAuthor().getUrl());
        else if (em.getAuthor() != null && em.getAuthor().getName() != null)
            eb.setAuthor(em.getAuthor().getName());
        // fields
        if (em.getFields() != null)
            for (EmbedFieldModel field : em.getFields()) {
                eb.addField(field.getName(), field.getValue(), field.isInline());
            }
        // timestamp
        try {
            if (em.getTimestamp() != null)
                eb.setTimestamp(OffsetDateTime.parse(em.getTimestamp()));
        } catch (DateTimeParseException e) {
            // ignore
        }

        return new MessageBuilder().setEmbed(eb.build()).setContent(o.getContent()).build();
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Generate a beautiful and easy to create embed powered by [Embed-Visualizer](https://leovoel.github.io/embed-visualizer/)";
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
    public long getCooldown() {
        return 5;
    }
}
