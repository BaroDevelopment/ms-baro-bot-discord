package com.baro.bot.discord.commands.music.dj;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;
import com.baro.bot.discord.commands.music.audio.QueuedTrack;
import com.baro.bot.discord.commands.music.queue.FairQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Command that provides users the ability to move a track in the playlist.
 */
public class MoveTrackCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "movetrack";

    private static boolean isUnavailablePosition(FairQueue<QueuedTrack> queue, int position) {
        return (position < 1 || position > queue.size());
    }

    @Override
    public void execute(CommandContext ctx) {

        if (!init(ctx)) return;

        int from;
        int to;

        String[] parts = ctx.getArgs().split("\\s+", 2);
        if (parts.length < 2) {
            ctx.getEvent().getChannel().sendMessage("Please include two valid indexes.").queue();
            return;
        }

        try {
            // Validate the args
            from = Integer.parseInt(parts[0]);
            to = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            ctx.getEvent().getChannel().sendMessage("Please provide two valid indexes.").queue();
            return;
        }

        if (from == to) {
            ctx.getEvent().getChannel().sendMessage("Can't move a track to the same position.").queue();
            return;
        }

        // Validate that from and to are available
        AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
        FairQueue<QueuedTrack> queue = handler.getQueue();
        if (isUnavailablePosition(queue, from)) {
            String reply = String.format("`%d` is not a valid position in the queue!", from);
            ctx.getEvent().getChannel().sendMessage(reply).queue();
            return;
        }
        if (isUnavailablePosition(queue, to)) {
            String reply = String.format("`%d` is not a valid position in the queue!", to);
            ctx.getEvent().getChannel().sendMessage(reply).queue();
            return;
        }

        // Move the track
        QueuedTrack track = queue.moveItem(from - 1, to - 1);
        String trackTitle = track.getTrack().getInfo().title;
        String reply = String.format("Moved **%s** from position `%d` to `%d`.", trackTitle, from, to);
        ctx.getEvent().getChannel().sendMessage(reply).queue();
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Moves a track in the current queue to a different position";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.DJ;
    }

    @Override
    public boolean getArgs() {
        return true;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("mt");
        aliases.add("move");
        return aliases;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`movetrack <from> <to>` - Moves the track at position in the queue to position");
        return samples;
    }
}