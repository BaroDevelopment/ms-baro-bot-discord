package com.baro.bot.discord.commands.moderation;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.util.Flags;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class LockCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "lock";

    private EnumSet<Permission> getPermission(Role role, TextChannel channel, boolean allowed) {
        PermissionOverride po = channel.getPermissionOverride(role);
        if (po != null) {
            if (allowed)
                return po.getAllowed();
            else return po.getDenied();
        } else
            return EnumSet.noneOf(Permission.class);
    }

    private void lock(TextChannel channel) {
        Role everyone = channel.getGuild().getPublicRole();
        EnumSet<Permission> allowed = getPermission(everyone, channel, true);
        EnumSet<Permission> deny = getPermission(everyone, channel, false);

        if (allowed != null) {
            allowed.remove(Permission.MESSAGE_WRITE);
        }

        if (!deny.contains(Permission.MESSAGE_WRITE)) {
            // lock channel
            deny.add(Permission.MESSAGE_WRITE);
            channel.getManager().putPermissionOverride(everyone, allowed, deny).queue();
            // change topic
            String topic = channel.getTopic();
            channel.getManager().setTopic(topic + Flags.BAROBOT_LOCK.toString()).queue();
        } else {
            // already locked
        }
    }

    private void lockAll(List<TextChannel> channels) {
        for (TextChannel c : channels)
            lock(c);
    }

    private void unlock(TextChannel channel) {
        String topic = channel.getTopic();
        if (topic != null && topic.contains(Flags.BAROBOT_LOCK.toString())) {
            // unlock channel
            Role everyone = channel.getGuild().getPublicRole();
            EnumSet<Permission> allowed = getPermission(everyone, channel, true);
            EnumSet<Permission> deny = getPermission(everyone, channel, false);
            deny.remove(Permission.MESSAGE_WRITE);
            channel.getManager().putPermissionOverride(everyone, allowed, deny).queue();
            // change topic
            channel.getManager().setTopic(topic.replace(Flags.BAROBOT_LOCK.toString(), "")).queue();
        }
    }

    private void unlockAll(List<TextChannel> channels) {
        for (TextChannel c : channels)
            lock(c);
    }

    @Override
    public void execute(CommandContext ctx) {
        List<TextChannel> channels = ctx.getEvent().getMessage().getMentionedChannels();
        switch (ctx.getInvoke()) {
            case "lock":
                if (!channels.isEmpty())
                    lockAll(channels);
                else
                    lock(ctx.getEvent().getTextChannel());

                sendSuccess(ctx, ":lock: Locked the channel");
                break;
            case "lockall":
                lockAll(ctx.getEvent().getGuild().getTextChannels());
                sendSuccess(ctx, ":lock: Locked ALL channels!");
                break;
            case "unlock":
                if (!channels.isEmpty())
                    unlockAll(channels);
                else
                    unlock(ctx.getEvent().getTextChannel());
                sendSuccess(ctx, ":unlock: Unlocked the channel");
                break;
            case "unlockall":
                unlockAll(ctx.getEvent().getGuild().getTextChannels());
                sendSuccess(ctx, ":unlock: Unlocked ALL channels!");
                break;
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Lock or unlock channels";
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
        return Arrays.asList("lockall", "unlockall", "unlock");
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
