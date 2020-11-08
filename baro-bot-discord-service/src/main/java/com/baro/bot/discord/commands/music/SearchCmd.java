package com.baro.bot.discord.commands.music;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;
import com.baro.bot.discord.commands.music.audio.QueuedTrack;
import com.baro.bot.discord.service.BaroBot;
import com.baro.bot.discord.util.FormatUtil;
import com.jagrosh.jdautilities.menu.OrderedMenu;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException.Severity;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SearchCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "search";

    private final OrderedMenu.Builder builder;

    public SearchCmd(BaroBot bot) {
        builder = new OrderedMenu.Builder()
                .allowTextInput(true)
                .useNumbers()
                .useCancelButton(true)
                .setEventWaiter(bot.getEventWaiter())
                .setTimeout(1, TimeUnit.MINUTES);
    }

    @Override
    public void execute(CommandContext ctx) {
        String searchPrefix = ctx.getInvoke().toLowerCase().contains("scsearch") ? "scsearch:" : "ytsearch:";
        if (!init(ctx)) return;
        if (ctx.getArgs().isEmpty()) {
            ctx.getEvent().getChannel().sendMessage("Please include a query.").queue();
            return;
        }
        ctx.getEvent().getChannel().sendMessage("Searching... `[" + ctx.getArgs() + "]`").queue(m -> {
            ctx.getBot().getPlayerManager().loadItemOrdered(ctx.getEvent().getGuild(), searchPrefix + ctx.getArgs(), new ResultHandler(m, ctx));
        });
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Shows the top YouTube/SoundCloud results for a search and allows you to select one to add to the queue\n";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("scsearch");
        aliases.add("ytsearch");
        return aliases;
    }


    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }

    private class ResultHandler implements AudioLoadResultHandler {
        private final Message m;
        private final CommandContext ctx;

        private ResultHandler(Message m, CommandContext ctx) {
            this.m = m;
            this.ctx = ctx;
        }

        @Override
        public void trackLoaded(AudioTrack track) {

/*            if (bot.getConfig().isTooLong(track)) {
                m.editMessage(FormatUtil.filter(event.getClient().getWarning() + " This track (**" + track.getInfo().title + "**) is longer than the allowed maximum: `"
                        + FormatUtil.formatTime(track.getDuration()) + "` > `" + bot.getConfig().getMaxTime() + "`")).queue();
                return;
            }*/

            AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
            int pos = handler.addTrack(new QueuedTrack(track, ctx.getEvent().getAuthor())) + 1;
            m.editMessage(FormatUtil.filter("Added **" + track.getInfo().title
                    + "** (`" + FormatUtil.formatTime(track.getDuration()) + "`) " + (pos == 0 ? "to begin playing"
                    : " to the queue at position " + pos))).queue();
        }

        @Override
        public void playlistLoaded(AudioPlaylist playlist) {
            builder.setColor(ctx.getEvent().getGuild().getSelfMember().getColor())
                    .setText(FormatUtil.filter("Search results for `" + ctx.getArgs() + "`:"))
                    .setChoices(new String[0])
                    .setSelection((msg, i) ->
                    {
                        AudioTrack track = playlist.getTracks().get(i - 1);

/*                        if (bot.getConfig().isTooLong(track)) {
                            event.replyWarning("This track (**" + track.getInfo().title + "**) is longer than the allowed maximum: `"
                                    + FormatUtil.formatTime(track.getDuration()) + "` > `" + bot.getConfig().getMaxTime() + "`");
                            return;
                        }*/

                        AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
                        int pos = handler.addTrack(new QueuedTrack(track, ctx.getEvent().getAuthor())) + 1;
                        ctx.getEvent().getChannel().sendMessage("Added **" + FormatUtil.filter(track.getInfo().title)
                                + "** (`" + FormatUtil.formatTime(track.getDuration()) + "`) " + (pos == 0 ? "to begin playing"
                                : " to the queue at position " + pos)).queue();
                    })
                    .setCancel((msg) -> {
                    })
                    .setUsers(ctx.getEvent().getAuthor())
            ;
            for (int i = 0; i < 10 && i < playlist.getTracks().size(); i++) {
                AudioTrack track = playlist.getTracks().get(i);
                builder.addChoices("`[" + FormatUtil.formatTime(track.getDuration()) + "]` [**" + track.getInfo().title + "**](" + track.getInfo().uri + ")");
            }
            builder.build().display(m);
        }

        @Override
        public void noMatches() {
            m.editMessage(FormatUtil.filter("No results found for `" + ctx.getArgs() + "`.")).queue();
        }

        @Override
        public void loadFailed(FriendlyException throwable) {
            if (throwable.severity == Severity.COMMON)
                m.editMessage("Error loading: " + throwable.getMessage()).queue();
            else
                m.editMessage("Error loading track.").queue();
        }
    }
}
