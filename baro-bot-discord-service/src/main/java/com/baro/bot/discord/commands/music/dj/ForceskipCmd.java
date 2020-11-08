package com.baro.bot.discord.commands.music.dj;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;
import net.dv8tion.jda.api.entities.User;

import java.util.Collections;
import java.util.List;

public class ForceskipCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "forceskip";

    @Override
    public void execute(CommandContext ctx) {
        if (!init(ctx)) return;
        AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
        if (handler.getPlayer().getPlayingTrack() == null) {
            sendError(ctx, "Player is not playing right now.");
            return;
        }
        User u = ctx.getEvent().getJDA().getUserById(handler.getRequester());
        ctx.getEvent().getChannel().sendMessage("Skipped **" + handler.getPlayer().getPlayingTrack().getInfo().title
                + "** (requested by " + (u == null ? "someone" : "**" + u.getName() + "**") + ")").queue();
        handler.getPlayer().stopTrack();
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Forcibly skips the current song, regardless of who added it and how many votes there are to skip it";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.DJ;
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("fs");
    }
}
