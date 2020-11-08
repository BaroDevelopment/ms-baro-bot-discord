package com.baro.bot.discord.commands.music;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;
import com.jagrosh.jlyrics.LyricsClient;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.List;

public class LyricsCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "lyrics";
    private final LyricsClient client = new LyricsClient();

    @Override
    public void execute(CommandContext ctx) {

        if (!init(ctx)) return;
        if (!isDj(ctx)) return;

        ctx.getEvent().getChannel().sendTyping().queue();
        String title;
        if (ctx.getArgs().isEmpty())
            title = ((AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler()).getPlayer().getPlayingTrack().getInfo().title;
        else
            title = ctx.getArgs();
        client.getLyrics(title).thenAccept(lyrics ->
        {
            if (lyrics == null) {
                ctx.getEvent().getChannel().sendMessage("Lyrics for `" + title + "` could not be found!" +
                        (ctx.getArgs().isEmpty() ? " Try entering the song name manually (`lyrics [song name]`)" : ""))
                        .queue();
                return;
            }

            EmbedBuilder eb = new EmbedBuilder()
                    .setAuthor(lyrics.getAuthor())
                    .setColor(ctx.getEvent().getGuild().getSelfMember().getColor())
                    .setTitle(lyrics.getTitle(), lyrics.getURL());
            if (lyrics.getContent().length() > 15000) {
                ctx.getEvent().getChannel().sendMessage("Lyrics for `" + title + "` found but likely not correct: "
                        + lyrics.getURL()).queue();
            } else if (lyrics.getContent().length() > 2000) {
                String content = lyrics.getContent().trim();
                while (content.length() > 2000) {
                    int index = content.lastIndexOf("\n\n", 2000);
                    if (index == -1)
                        index = content.lastIndexOf("\n", 2000);
                    if (index == -1)
                        index = content.lastIndexOf(" ", 2000);
                    if (index == -1)
                        index = 2000;
                    ctx.getEvent().getChannel().sendMessage(eb.setDescription(content.substring(0, index).trim()).build()).queue();
                    content = content.substring(index).trim();
                    eb.setAuthor(null).setTitle(null, null);
                }
                ctx.getEvent().getChannel().sendMessage(eb.setDescription(content).build()).queue();
            } else
                ctx.getEvent().getChannel().sendMessage(eb.setDescription(lyrics.getContent()).build()).queue();
        });
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Shows the lyrics to the currently-playing song";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`lyrics <song_name>`");
        return samples;
    }
}
