package com.baro.bot.discord.commands.music;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;
import com.baro.bot.discord.commands.music.audio.QueuedTrack;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

public class RemoveCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "remove";

    @Override
    public void execute(CommandContext ctx) {
        if (!init(ctx)) return;

        AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
        if (handler.getQueue().isEmpty()) {
            ctx.getEvent().getChannel().sendMessage("There is nothing in the queue!").queue();
            return;
        }
        if (ctx.getArgs().equalsIgnoreCase("all")) {
            int count = handler.getQueue().removeAll(ctx.getEvent().getAuthor().getIdLong());
            if (count == 0)
                ctx.getEvent().getChannel().sendMessage("You don't have any songs in the queue!").queue();
            else
                ctx.getEvent().getChannel().sendMessage("Successfully removed your " + count + " entries.").queue();
            return;
        }
        int pos;
        try {
            pos = Integer.parseInt(ctx.getArgs());
        } catch (NumberFormatException e) {
            pos = 0;
        }
        if (pos < 1 || pos > handler.getQueue().size()) {
            ctx.getEvent().getChannel().sendMessage("Position must be a valid integer between 1 and " + handler.getQueue().size() + "!").queue();
            return;
        }
        boolean isDJ = ctx.getEvent().getMember().hasPermission(Permission.MANAGE_SERVER);
        QueuedTrack qt = handler.getQueue().get(pos - 1);
        if (qt.getIdentifier() == ctx.getEvent().getAuthor().getIdLong()) {
            handler.getQueue().remove(pos - 1);
            ctx.getEvent().getChannel().sendMessage("Removed **" + qt.getTrack().getInfo().title + "** from the queue").queue();
        } else if (isDJ) {
            handler.getQueue().remove(pos - 1);
            User u;
            try {
                u = ctx.getEvent().getJDA().getUserById(qt.getIdentifier());
            } catch (Exception e) {
                u = null;
            }
            ctx.getEvent().getChannel().sendMessage("Removed **" + qt.getTrack().getInfo().title
                    + "** from the queue (requested by " + (u == null ? "someone" : "**" + u.getName() + "**") + ")").queue();
        } else {
            ctx.getEvent().getChannel().sendMessage("You cannot remove **" + qt.getTrack().getInfo().title + "** because you didn't add it!").queue();
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Removes a song from the queue";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`remove <position>` - Removes the song at the provided position in the queue. You can only remove " +
                "songs that you added, unless you are an Admin or have the specified DJ role.");
        samples.add("`remove all` - Removes all songs that you have added to the queue");
        return samples;
    }
}
