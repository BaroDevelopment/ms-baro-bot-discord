package com.baro.bot.discord.commands.music;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

public class SkipCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "skip";

    @Override
    public void execute(CommandContext ctx) {
        if (!init(ctx)) return;

        AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
        if (ctx.getEvent().getAuthor().getIdLong() == handler.getRequester()) {
            ctx.getEvent().getChannel().sendMessage("Skipped **" + handler.getPlayer().getPlayingTrack().getInfo().title + "**").queue();
            handler.getPlayer().stopTrack();
        } else {
            int listeners = (int) ctx.getEvent().getGuild().getSelfMember().getVoiceState().getChannel().getMembers().stream()
                    .filter(m -> !m.getUser().isBot() && !m.getVoiceState().isDeafened()).count();
            String msg;
            if (handler.getVotes().contains(ctx.getEvent().getAuthor().getId()))
                msg = "You already voted to skip this song `[";
            else {
                msg = "You voted to skip the song `[";
                handler.getVotes().add(ctx.getEvent().getAuthor().getId());
            }
            int skippers = (int) ctx.getEvent().getGuild().getSelfMember().getVoiceState().getChannel().getMembers().stream()
                    .filter(m -> handler.getVotes().contains(m.getUser().getId())).count();
            int required = (int) Math.ceil(listeners * .55);
            msg += skippers + " votes, " + required + "/" + listeners + " needed]`";
            if (skippers >= required) {
                User u = ctx.getEvent().getJDA().getUserById(handler.getRequester());
                msg += "\n" + "Skipped **" + handler.getPlayer().getPlayingTrack().getInfo().title
                        + "**" + (handler.getRequester() == 0 ? "" : " (requested by " + (u == null ? "someone" : "**" + u.getName() + "**") + ")");
                handler.getPlayer().stopTrack();
            }
            ctx.getEvent().getChannel().sendMessage(msg).queue();
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Skips a song if you added it. If you didn't add it, it adds your vote to skip it. " +
                "Approximately 60% of active listeners need to vote to skip a song for it to be skipped.";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("voteskip");
        return aliases;
    }
}
