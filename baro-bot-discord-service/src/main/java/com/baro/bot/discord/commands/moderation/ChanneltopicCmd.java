package com.baro.bot.discord.commands.moderation;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class ChanneltopicCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "channeltopic";
    public static final Logger LOGGER = LoggerFactory.getLogger(ChanneltopicCmd.class);

    @Override
    public void execute(CommandContext ctx) {
        if (ctx.getEvent().getChannel().getType() == ChannelType.TEXT) {
            String topic = ctx.getArgs();
            ctx.getEvent().getChannel().sendMessage("Processing .... this might take a while").queue(message -> {
                ctx.getEvent().getTextChannel().getManager().setTopic(topic).submit().thenRunAsync(() -> {
                    message.editMessage("Done").queue();
                });
            });
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Change the topic of a channel";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MODERATION;
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
        return Collections.singletonList("ctopic");
    }

    @Nullable
    @Override
    public EnumSet<Permission> getMemberPermissions() {
        return EnumSet.of(Permission.MANAGE_CHANNEL);
    }

    @Nullable
    @Override
    public EnumSet<Permission> getBotPermissions() {
        return EnumSet.of(Permission.MANAGE_CHANNEL);
    }

    @Override
    public long getCooldown() {
        return 5;
    }
}
