package com.baro.bot.discord.commands.music;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;
import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class NowplayingCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "nowplaying";

    @Override
    public void execute(CommandContext ctx) {
        if (!init(ctx)) return;

        AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
        Message m = handler.getNowPlaying(ctx.getEvent().getJDA(), ctx.getBot());
        if (m == null) {
            ctx.getEvent().getChannel().sendMessage(handler.getNoMusicPlaying(ctx.getEvent().getJDA())).queue();
            ctx.getBot().getNowplayingHandler().clearLastNPMessage(ctx.getEvent().getGuild());
        } else {
            ctx.getEvent().getChannel().sendMessage(m).queue(msg -> {
                ctx.getBot().getNowplayingHandler().setLastNPMessage(msg);
            });
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Shows the song that is currently playing";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("np");
        return aliases;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }
}
