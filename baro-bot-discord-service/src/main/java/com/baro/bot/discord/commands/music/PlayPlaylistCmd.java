package com.baro.bot.discord.commands.music;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;
import com.baro.bot.discord.commands.music.audio.QueuedTrack;
import com.baro.bot.discord.commands.music.playlist.PlaylistLoader;
import com.baro.bot.discord.util.FormatUtil;

import java.util.ArrayList;
import java.util.List;

public class PlayPlaylistCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "pplaylist";

    @Override
    public void execute(CommandContext ctx) {
        if (ctx.getArgs().isEmpty()) {
            ctx.getEvent().getChannel().sendMessage("Please include a playlist name.").queue();
            return;
        }
        if (!init(ctx)) return;

        PlaylistLoader.Playlist playlist = ctx.getBot().getPlaylistLoader().getPlaylist(ctx.getArgs());
        if (playlist == null) {
            ctx.getEvent().getChannel().sendMessage("I could not find `" + ctx.getArgs() + ".txt` in the Playlists folder.").queue();
            return;
        }
        ctx.getEvent().getChannel().sendMessage(" Loading playlist **" + ctx.getArgs() + "**... (" + playlist.getItems().size() + " items)").queue(m ->
        {
            AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
            playlist.loadTracks(ctx.getBot().getPlayerManager(), (at) -> handler.addTrack(new QueuedTrack(at, ctx.getEvent().getAuthor())), () -> {
                StringBuilder builder = new StringBuilder(playlist.getTracks().isEmpty()
                        ? "No tracks were loaded!"
                        : "Loaded **" + playlist.getTracks().size() + "** tracks!");
                if (!playlist.getErrors().isEmpty())
                    builder.append("\nThe following tracks failed to load:");
                playlist.getErrors().forEach(err -> builder.append("\n`[").append(err.getIndex() + 1).append("]` **").append(err.getItem()).append("**: ").append(err.getReason()));
                String str = builder.toString();
                if (str.length() > 2000)
                    str = str.substring(0, 1994) + " (...)";
                m.editMessage(FormatUtil.filter(str)).queue();
            });
        });
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("playlistplay");
        aliases.add("pp");
        return aliases;
    }

    @Override
    public String getDescription() {
        return "Plays the provided playlist";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`pplaylist <playlist_name>`");
        samples.add("");
        samples.add("Use the **playlists** command to see all available playlists");
        return samples;
    }
}
