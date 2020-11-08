package com.baro.bot.discord.commands.music;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;
import com.baro.bot.discord.commands.music.audio.QueuedTrack;
import com.baro.bot.discord.model.entities.MusicEntity;
import com.baro.bot.discord.repository.MusicRepository;
import com.baro.bot.discord.service.BaroBot;
import com.baro.bot.discord.util.FormatUtil;
import com.jagrosh.jdautilities.menu.Paginator;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.exceptions.PermissionException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.baro.bot.discord.util.Statics.*;

public class QueueCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "queue";

    // TODO: USE THOSE AS PROPERTIES
    public final String STOP_EMOJI = "\u23F9";
    private final Paginator.Builder builder;
    private final MusicRepository musicRepository;

    public QueueCmd(BaroBot bot, MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
        this.builder = new Paginator.Builder()
                .setColumns(1)
                .setFinalAction(m -> {
                    try {
                        m.clearReactions().queue();
                    } catch (PermissionException ignore) {
                    }
                })
                .setItemsPerPage(10)
                .waitOnSinglePage(false)
                .useNumberedItems(true)
                .showPageNumbers(true)
                .wrapPageEnds(true)
                .setEventWaiter(bot.getEventWaiter())
                .setTimeout(1, TimeUnit.MINUTES);
    }

    private String getQueueTitle(AudioHandler ah, int songslength, long total, long guildId) {
        StringBuilder sb = new StringBuilder();
        if (ah.getPlayer().getPlayingTrack() != null) {
            sb.append(ah.getPlayer().isPaused() ? PAUSE_EMOJI : PLAY_EMOJI).append(" **")
                    .append(ah.getPlayer().getPlayingTrack().getInfo().title).append("**\n");
        }
        Optional<MusicEntity> musicSettingsEntity = musicRepository.findById(guildId);
        boolean repeat = musicSettingsEntity.isPresent() && musicSettingsEntity.get().isPlaylistRepeat();
        return FormatUtil.filter(sb.append(" Current Queue | ").append(songslength)
                .append(" entries | `").append(FormatUtil.formatTime(total)).append("` ")
                .append(repeat ? "| " + REPEAT : "").toString());
    }

    @Override
    public void execute(CommandContext ctx) {
        if (!init(ctx)) return;

        int pagenum = 1;
        try {
            pagenum = Integer.parseInt(ctx.getArgs());
        } catch (NumberFormatException ignore) {
        }
        AudioHandler ah = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
        List<QueuedTrack> list = ah.getQueue().getList();
        if (list.isEmpty()) {
            Message nowp = ah.getNowPlaying(ctx.getEvent().getJDA(), ctx.getBot());
            Message nonowp = ah.getNoMusicPlaying(ctx.getEvent().getJDA());
            Message built = new MessageBuilder()
                    .setContent("There is no music in the queue!")
                    .setEmbed((nowp == null ? nonowp : nowp).getEmbeds().get(0)).build();
            ctx.getEvent().getChannel().sendMessage(built).queue(m -> {
                if (nowp != null)
                    ctx.getBot().getNowplayingHandler().setLastNPMessage(m);
            });
            return;
        }
        String[] songs = new String[list.size()];
        long total = 0;
        for (int i = 0; i < list.size(); i++) {
            total += list.get(i).getTrack().getDuration();
            songs[i] = list.get(i).toString();
        }
        long fintotal = total;
        builder.setText((i1, i2) -> getQueueTitle(ah, songs.length, fintotal, ctx.getEvent().getGuild().getIdLong()))
                .setItems(songs)
                .setUsers(ctx.getEvent().getAuthor())
                .setColor(ctx.getEvent().getGuild().getSelfMember().getColor())
        ;
        builder.build().paginate(ctx.getEvent().getChannel(), pagenum);
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Shows the current queue";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("list");
        return aliases;
    }

    @Nullable
    @Override
    public EnumSet<Permission> getBotPermissions() {
        return EnumSet.of(Permission.MESSAGE_ADD_REACTION);
    }
}
