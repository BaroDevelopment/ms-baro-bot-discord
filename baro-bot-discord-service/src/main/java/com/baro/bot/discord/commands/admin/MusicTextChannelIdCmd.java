package com.baro.bot.discord.commands.admin;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.repository.MusicRepository;
import com.baro.bot.discord.util.FormatUtil;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class MusicTextChannelIdCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "settc";
    private final MusicRepository musicRepository;

    public MusicTextChannelIdCmd(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @Override
    public void execute(CommandContext ctx) {
        Long guildId = ctx.getEvent().getGuild().getIdLong();
        if (ctx.getArgs().isEmpty()) {
            musicRepository.setTextChannelId("", guildId);
            sendSuccess(ctx, "Music commands can now be used in any channel");

            return;
        }
        List<TextChannel> list = FinderUtil.findTextChannels(ctx.getArgs(), ctx.getEvent().getGuild());
        if (list.isEmpty()) {
            sendError(ctx, "No Text Channels found matching \"" + ctx.getArgs() + "\"");
        } else if (list.size() > 1)
            ctx.getEvent().getChannel().sendMessage(FormatUtil.listOfTChannels(list, ctx.getArgs())).queue();
        else {
            musicRepository.setTextChannelId(list.get(0).getId(), guildId);
            sendSuccess(ctx, "Music commands can now only be used in <#" + list.get(0).getId() + ">");
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Sets the text channel for music commands";
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
    public CommandCategory getCategory() {
        return CommandCategory.ADMIN;
    }

    @Nullable
    @Override
    public EnumSet<Permission> getMemberPermissions() {
        return EnumSet.of(Permission.ADMINISTRATOR);
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`settc` - music commands can be used in any channel");
        samples.add("`settc <channel_id | channel_name | #channel>` - Sets the text channel for music commands. " +
                "Using music commands in other channels will result in them being deleted (if possible), " +
                "and a warning sent via DMs to use the correct channel. Additionally, if the bot has the Manage Channel " +
                "permission in the set channel, it will adjust the topic to show the current track.");
        return samples;
    }
}
