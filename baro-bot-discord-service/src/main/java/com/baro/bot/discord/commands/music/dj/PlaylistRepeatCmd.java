package com.baro.bot.discord.commands.music.dj;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.model.entities.MusicEntity;
import com.baro.bot.discord.repository.MusicRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaylistRepeatCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "prepeat";

    private final MusicRepository musicRepository;

    public PlaylistRepeatCmd(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @Override
    public void execute(CommandContext ctx) {

        if (!init(ctx)) return;

        long guildId = ctx.getEvent().getGuild().getIdLong();
        Optional<MusicEntity> settings = musicRepository.findById(ctx.getEvent().getGuild().getIdLong());
        boolean value;
        if (ctx.getArgs().isEmpty()) {
            value = settings.isPresent() && !settings.get().isPlaylistRepeat();
        } else if (ctx.getArgs().equalsIgnoreCase("true") || ctx.getArgs().equalsIgnoreCase("on")) {
            value = true;
        } else if (ctx.getArgs().equalsIgnoreCase("false") || ctx.getArgs().equalsIgnoreCase("off")) {
            value = false;
        } else {
            ctx.getEvent().getChannel().sendMessage("Valid options are `on` or `off` (or leave empty to toggle)").queue();
            return;
        }
        musicRepository.setPlaylistRepeat(value, guildId);
        ctx.getEvent().getChannel().sendMessage("Repeat mode is now `" + (value ? "ON" : "OFF") + "`").queue();
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Re-adds music to the queue when finished";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.DJ;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`prepeat ON|OFF` - Puts the player in (or takes it out of) repeat mode. In repeat mode, when songs end naturally (not removed or skipped), they get put back into the queue.");
        return samples;
    }
}
