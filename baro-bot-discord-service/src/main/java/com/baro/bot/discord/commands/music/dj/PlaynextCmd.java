package com.baro.bot.discord.commands.music.dj;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;
import com.baro.bot.discord.commands.music.audio.QueuedTrack;
import com.baro.bot.discord.util.FormatUtil;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class PlaynextCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "playnext";

    // args: <title|URL>
    @Override
    public void execute(CommandContext ctx) {
        if (!init(ctx)) return;

        if (ctx.getArgs().isEmpty() && ctx.getEvent().getMessage().getAttachments().isEmpty()) {
            ctx.getEvent().getChannel().sendMessage("Please include a song title or URL!").queue();
            return;
        }
        String args = ctx.getArgs().startsWith("<") && ctx.getArgs().endsWith(">")
                ? ctx.getArgs().substring(1, ctx.getArgs().length() - 1)
                : ctx.getArgs().isEmpty() ? ctx.getEvent().getMessage().getAttachments().get(0).getUrl() : ctx.getArgs();
        ctx.getEvent().getChannel().sendMessage("Loading... `[" + args + "]`").queue(m -> {
            ctx.getBot().getPlayerManager().loadItemOrdered(ctx.getEvent().getGuild(), args, new ResultHandler(m, ctx, false));
        });
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Plays a single song next";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.DJ;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("pn");
        aliases.add("pt");
        aliases.add("playtop");
        return aliases;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`playnext <URL|song_name>` - Places a single song at the front of the queue to play next " +
                "(or begins playing if nothing is currently playing)");
        return samples;
    }

    private class ResultHandler implements AudioLoadResultHandler {
        private final Message m;
        private final CommandContext ctx;
        private final boolean ytsearch;

        private ResultHandler(Message m, CommandContext ctx, boolean ytsearch) {
            this.m = m;
            this.ctx = ctx;
            this.ytsearch = ytsearch;
        }

        private void loadSingle(AudioTrack track) {

/*            if (bot.getConfig().isTooLong(track)) {
                m.editMessage(FormatUtil.filter(event.getClient().getWarning() + " This track (**" + track.getInfo().title + "**) is longer than the allowed maximum: `"
                        + FormatUtil.formatTime(track.getDuration()) + "` > `" + FormatUtil.formatTime(bot.getConfig().getMaxSeconds() * 1000) + "`")).queue();
                return;
            }*/

            AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
            int pos = handler.addTrackToFront(new QueuedTrack(track, ctx.getEvent().getAuthor())) + 1;
            String addMsg = FormatUtil.filter("Added **" + track.getInfo().title
                    + "** (`" + FormatUtil.formatTime(track.getDuration()) + "`) " + (pos == 0 ? "to begin playing" : " to the queue at position " + pos));
            m.editMessage(addMsg).queue();
        }

        @Override
        public void trackLoaded(AudioTrack track) {
            loadSingle(track);
        }

        @Override
        public void playlistLoaded(AudioPlaylist playlist) {
            AudioTrack single;
            if (playlist.getTracks().size() == 1 || playlist.isSearchResult())
                single = playlist.getSelectedTrack() == null ? playlist.getTracks().get(0) : playlist.getSelectedTrack();
            else if (playlist.getSelectedTrack() != null)
                single = playlist.getSelectedTrack();
            else
                single = playlist.getTracks().get(0);
            loadSingle(single);
        }

        @Override
        public void noMatches() {
            if (ytsearch)
                m.editMessage(FormatUtil.filter("No results found for `" + ctx.getArgs() + "`.")).queue();
            else
                ctx.getBot().getPlayerManager().loadItemOrdered(ctx.getEvent().getGuild(),
                        "ytsearch:" + ctx.getArgs(), new ResultHandler(m, ctx, true));
        }

        @Override
        public void loadFailed(FriendlyException throwable) {
            if (throwable.severity == FriendlyException.Severity.COMMON)
                m.editMessage("Error loading: " + throwable.getMessage()).queue();
            else
                m.editMessage("Error loading track.").queue();
        }
    }
}
