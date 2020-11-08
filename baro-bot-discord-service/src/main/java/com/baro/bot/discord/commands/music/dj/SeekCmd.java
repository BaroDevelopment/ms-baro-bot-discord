package com.baro.bot.discord.commands.music.dj;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;

import java.util.ArrayList;
import java.util.List;

public class SeekCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "seek";

    @Override
    public void execute(CommandContext ctx) {

        if (!init(ctx)) return;

        AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
        long timeInSeconds = 0;

        try {
            timeInSeconds = Long.parseLong(ctx.getArgs());
        } catch (Exception ex) {
            String[] time = ctx.getArgs().split(":");
            if (time.length <= 1 || time.length >= 4) {
                ctx.getEvent().getChannel().sendMessage("Given time must be a number in seconds or something like 1:20").queue();
                return;
            }
            if (time.length == 2) {
                try {
                    timeInSeconds = (Long.parseLong(time[0]) * 60) + (Long.parseLong(time[1]));
                } catch (Exception exe) {
                    ctx.getEvent().getChannel().sendMessage("Given time must be a number in seconds or something like 1:20").queue();
                    return;
                }
            }
            if (time.length == 3) {
                try {
                    timeInSeconds = (Long.parseLong(time[0]) * 60 * 60) + (Long.parseLong(time[1]) * 60) + (Long.parseLong(time[2]));
                } catch (Exception exe) {
                    ctx.getEvent().getChannel().sendMessage("Given time must be a number in seconds or something like 1:20").queue();
                    return;
                }
            }
        }

        if (handler.getPlayer().getPlayingTrack() == null) {
            ctx.getEvent().getChannel().sendMessage("Player is not playing at the moment. Play a song first").queue();
            return;
        }

        if (timeInSeconds * 1000 > handler.getPlayer().getPlayingTrack().getDuration()) {
            ctx.getEvent().getChannel().sendMessage("Time can not be longer than currently playing song duration.").queue();
            return;
        }
        handler.getPlayer().getPlayingTrack().setPosition(timeInSeconds * 1000);
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Navigate through the timeline.\n";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.DJ;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`seek <timestamp>` - i.e. seek 03:40");
        samples.add("`seek <seconds>` - i.e. seek 20");
        return samples;
    }
}
