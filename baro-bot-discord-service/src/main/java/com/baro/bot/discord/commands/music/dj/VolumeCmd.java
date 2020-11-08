package com.baro.bot.discord.commands.music.dj;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;
import com.baro.bot.discord.util.FormatUtil;

import java.util.ArrayList;
import java.util.List;

public class VolumeCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "volume";

    @Override
    public void execute(CommandContext ctx) {
        if (!init(ctx)) return;

        AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
        int volume = handler.getPlayer().getVolume();
        if (ctx.getArgs().isEmpty()) {
            ctx.getEvent().getChannel().sendMessage(FormatUtil.volumeIcon(volume) + " Current volume is `" + volume + "`").queue();
        } else {
            int nvolume;
            try {
                nvolume = Integer.parseInt(ctx.getArgs());
            } catch (NumberFormatException e) {
                nvolume = -1;
            }
            if (nvolume < 0 || nvolume > 150) {
                ctx.getEvent().getChannel().sendMessage("Volume must be a valid integer between 0 and 150!").queue();
            } else {
                handler.getPlayer().setVolume(nvolume);
//                settings.setVolume(nvolume);
                ctx.getEvent().getChannel().sendMessage(FormatUtil.volumeIcon(nvolume) + " Volume changed from `" + volume + "` to `" + nvolume + "`").queue();
            }
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Shows or sets the current volume. For best performance, it is recommended to leave this at 100 and adjust volume on an individual basis within Discord";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.DJ;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("vol");
        return aliases;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`volume [0-150]`");
        return samples;
    }
}
