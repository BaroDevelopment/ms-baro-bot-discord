package com.baro.bot.discord.commands;

import com.baro.bot.discord.commands.music.audio.AudioHandler;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;

import java.util.List;
import java.util.stream.Collectors;

public abstract class MusicCommand extends ACommand {

    public boolean isPlayingMusic(CommandContext ctx) {
        AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
        return handler.isMusicPlaying(ctx.getEvent().getJDA());
    }

    private void setupHandler(CommandContext ctx) {
        ctx.getBot().getPlayerManager().setUpHandler(ctx.getEvent().getGuild());
    }

    public boolean init(CommandContext ctx) {
        boolean result = isMusicTextChannel(ctx) && validVoiceState(ctx);
        if (result)
            setupHandler(ctx);
        return result;
    }

    public boolean isDj(CommandContext ctx) {
        // Bot owner is dj by default
        if (ctx.getCommandManager().getBotConfig().getBotOwnerIds().contains(ctx.getEvent().getAuthor().getId()))
            return true;
        // members with MANAGE_SERVER permissions are dj by default
        if (ctx.getEvent().getMember().hasPermission(Permission.MANAGE_SERVER)) return true;
        // member has the dj role
        String djRoleId = ctx.getCommandManager().getDjRoleId(ctx.getEvent().getGuild().getIdLong());
        List<String> djRole = ctx.getEvent().getMember().getRoles().stream()
                .map(role -> role.getId())
                .filter(s -> s.equals(djRoleId))
                .collect(Collectors.toList());
        // if no dj is set then user is considered a dj
        if (!djRole.isEmpty()) return true;
        // if dj role id = guild id (@everyone role id) then everyone is dj
        if (ctx.getEvent().getGuild().getId().equals(djRoleId)) return true;

        // no dj role
        ctx.getEvent().getChannel().sendMessage("Only DJ's can execute this command").queue();
        return false;
    }

    private boolean validVoiceState(CommandContext ctx) {
        VoiceChannel current = ctx.getEvent().getGuild().getSelfMember().getVoiceState().getChannel();
        String guildVoiceChannelId = ctx.getCommandManager().getMusicVoiceChannelId(ctx.getEvent().getGuild().getIdLong());
        if (current == null && guildVoiceChannelId != null) {
            current = ctx.getEvent().getGuild().getVoiceChannelById(guildVoiceChannelId);
        }
        GuildVoiceState userState = ctx.getEvent().getMember().getVoiceState();
        if (!userState.inVoiceChannel() || userState.isDeafened() || (current != null && !userState.getChannel().equals(current))) {
            ctx.getEvent().getChannel().sendMessage("You must be listening in " +
                    (current == null ? "a voice channel" : "**" + current.getName() + "**") + " to use that!")
                    .queue();
            return false;
        }

        VoiceChannel afkChannel = userState.getGuild().getAfkChannel();
        if (afkChannel != null && afkChannel.equals(userState.getChannel())) {
            ctx.getEvent().getChannel().sendMessage("You cannot use that command in an AFK channel!").queue();
            return false;
        }

        if (!ctx.getEvent().getGuild().getSelfMember().getVoiceState().inVoiceChannel()) {
            try {
                ctx.getEvent().getGuild().getAudioManager().openAudioConnection(userState.getChannel());
                return true;
            } catch (PermissionException ex) {
                sendError(ctx, "I am unable to connect to **" + userState.getChannel().getName() + "**!");
                return false;
            }
        }
        return true;
    }

    private boolean isMusicTextChannel(CommandContext ctx) {
        String musicChannelId = ctx.getCommandManager().getMusicTextChannelId(ctx.getEvent().getGuild().getIdLong());
        if (musicChannelId == null || musicChannelId.equals(ctx.getEvent().getChannel().getId())) return true;
        TextChannel musicTextChannel = ctx.getEvent().getGuild().getTextChannelById(musicChannelId);
        sendError(ctx, "You can only use that command in" + musicTextChannel.getAsMention() + "!");
        return false;
    }
}
