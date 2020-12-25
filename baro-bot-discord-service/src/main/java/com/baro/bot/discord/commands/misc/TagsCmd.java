package com.baro.bot.discord.commands.misc;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.model.entities.TagsEntity;
import com.baro.bot.discord.model.entities.TagsEntityId;
import com.baro.bot.discord.repository.TagsRepository;
import com.baro.bot.discord.util.ColorUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TagsCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "tag";
    public static final Logger LOGGER = LoggerFactory.getLogger(TagsCmd.class);
    private final TagsRepository tagsRepository;

    public TagsCmd(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    private void createTag(Long serverId, String tag, String value, TextChannel textChannel) {
        TagsEntityId entityId = new TagsEntityId(serverId, tag);
        TagsEntity entity = new TagsEntity(entityId, value);
        tagsRepository.save(entity);

        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new ColorUtil().getRandomColor())
                .setTitle("Successfully added new Tag")
                .addField("Name", tag, true);

        textChannel.sendMessage(eb.build()).queue();
    }

    private void removeTag(Long serverId, String tag, CommandContext ctx) {
        TagsEntityId entityId = new TagsEntityId(serverId, tag);

        try {
            tagsRepository.deleteById(entityId);

            EmbedBuilder eb = new EmbedBuilder()
                    .setColor(new ColorUtil().getRandomColor())
                    .setTitle("Successfully deleted Tag")
                    .addField("Name", tag, true);

            ctx.getEvent().getTextChannel().sendMessage(eb.build()).queue();
        } catch (EmptyResultDataAccessException e) {
            sendError(ctx, String.format("Tag %s doesn't exist", tag));
        }
    }

    public void handleTagMessage(Long serverId, String tag, TextChannel channel) {
        TagsEntityId entityId = new TagsEntityId(serverId, tag);
        Optional<TagsEntity> entity = tagsRepository.findById(entityId);
        entity.ifPresent(tagsEntity -> {
            try {
                channel.sendMessage(EmbedCmd.jsonToEmbed(entity.get().getValue())).queue();
            } catch (JsonProcessingException e) {
                channel.sendMessage(tagsEntity.getValue()).queue();
            }
        });
    }

    private void displayTags(CommandContext ctx) {
        List<TagsEntity> entities = tagsRepository.findByTagsEntityIdServerId(ctx.getEvent().getGuild().getIdLong());
        String tags = String.join("\n", entities.stream().map(tagsEntity -> tagsEntity.getTagsEntityId().getTag()).collect(Collectors.toList()));
        ctx.getEvent().getChannel().sendMessage(new EmbedBuilder()
                .setColor(new ColorUtil().getRandomColor())
                .setDescription(tags)
                .setTitle("Tags")
                .build())
                .queue();
    }

    @Override
    public void execute(CommandContext ctx) {
        if (ctx.getArgs().isEmpty() || ctx.getInvoke().equals("tags")) {
            displayTags(ctx);
        } else if (ctx.getArgs().split(" ").length > 3 && ctx.getArgs().split(" ")[0].equalsIgnoreCase("add")) {
            Long serverId = ctx.getEvent().getGuild().getIdLong();
            String tag = ctx.getArgs().split(" ")[1];
            String value = String.join(" ", Arrays.copyOfRange(ctx.getArgs().split(" "), 2, ctx.getArgs().split(" ").length));
            createTag(serverId, tag, value, ctx.getEvent().getTextChannel());
        } else if (ctx.getArgs().split(" ").length == 2 &&
                (ctx.getArgs().split(" ")[0].equalsIgnoreCase("remove") ||
                        ctx.getArgs().split(" ")[0].equalsIgnoreCase("delete"))) {
            Long serverId = ctx.getEvent().getGuild().getIdLong();
            String tag = ctx.getArgs().split(" ")[1];
            removeTag(serverId, tag, ctx);
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Display/Add/Remove custom commands.";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MISC;
    }

    @Override
    public boolean getArgs() {
        return false;
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
        return Arrays.asList("tags");
    }

    @Nullable
    @Override
    public EnumSet<Permission> getMemberPermissions() {
        return EnumSet.of(Permission.ADMINISTRATOR);
    }

    @Override
    public long getCooldown() {
        return 5;
    }

    @Override
    public String getDocumentationUrl() {
        return null;
    }
}
