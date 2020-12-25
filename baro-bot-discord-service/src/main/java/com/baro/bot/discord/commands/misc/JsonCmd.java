package com.baro.bot.discord.commands.misc;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.model.api.discord.embed.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

public class JsonCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "json";
    public static final Logger LOGGER = LoggerFactory.getLogger(JsonCmd.class);
    public static final String FILE_NAME = "embed.json";

    @Override
    public void execute(CommandContext ctx) {
        ctx.getEvent().getChannel().retrieveMessageById(ctx.getArgs()).queue(this::handleJson,
                throwable -> sendError(ctx, "Message not found in this channel."));
    }

    private void handleJson(Message message) {
        String json = getJson(message);
        if (json == null) {
            message.getChannel().sendMessage("Error parsing the json").queue();
        } else {
            message.getChannel().sendFile(json.getBytes(), FILE_NAME).queue();
        }
    }

    @Nullable
    private String getJson(Message message) {
        DiscordEmbedModel embedModel = new DiscordEmbedModel();
        message.getContentRaw();
        if (!message.getContentRaw().isEmpty())
            embedModel.setContent(message.getContentRaw());
        if (!message.getEmbeds().isEmpty()) {
            MessageEmbed me = message.getEmbeds().get(0);
            EmbedModel em = new EmbedModel();
            if (me.getColor() != null) {
                em.setColor(me.getColor().getRGB());
            }
            if (me.getTitle() != null) {
                em.setTitle(me.getTitle());
            }
            if (me.getDescription() != null) {
                em.setDescription(me.getDescription());
            }
            if (me.getUrl() != null) {
                em.setUrl(me.getUrl());
            }
            if (me.getTimestamp() != null) {
                em.setTimestamp(me.getTimestamp().toString());
            }
            if (me.getFooter() != null) {
                em.setFooter(new EmbedFooterModel(me.getFooter().getIconUrl(), me.getFooter().getText()));
            }
            if (me.getAuthor() != null) {
                em.setAuthor(new EmbedAuthorModel(me.getAuthor().getName(), me.getAuthor().getUrl(), me.getAuthor().getIconUrl()));
            }
            if (me.getImage() != null) {
                em.setImage(new EmbedImageModel(me.getImage().getUrl()));
            }
            if (me.getThumbnail() != null) {
                em.setThumbnail(new EmbedThumbnailModel(me.getThumbnail().getUrl()));
            }
            if (!me.getFields().isEmpty()) {
                em.setFields(me.getFields().stream()
                        .map(field -> new EmbedFieldModel(field.getName(), field.getValue(), field.isInline()))
                        .toArray(EmbedFieldModel[]::new));
            }
            embedModel.setEmbed(em);
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(embedModel);
        } catch (JsonProcessingException e) {
            return null;
        }
    }


    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Get the json code of a MessageEmbed";
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
