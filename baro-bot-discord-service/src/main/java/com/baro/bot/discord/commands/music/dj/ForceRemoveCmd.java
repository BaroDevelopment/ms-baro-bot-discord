package com.baro.bot.discord.commands.music.dj;

import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.commands.MusicCommand;
import com.baro.bot.discord.commands.music.audio.AudioHandler;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import com.jagrosh.jdautilities.menu.OrderedMenu;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ForceRemoveCmd extends MusicCommand implements ICommand {

    public static final String COMMAND_NAME = "forceremove";

    private void removeAllEntries(User target, CommandContext ctx) {
        int count = ((AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler()).getQueue().removeAll(target.getIdLong());
        if (count == 0) {
            ctx.getEvent().getChannel().sendMessage("**" + target.getName() + "** doesn't have any songs in the queue!").queue();
        } else {
            ctx.getEvent().getChannel().sendMessage("Successfully removed `" + count + "` entries from **" + target.getName() + "**#" + target.getDiscriminator() + ".").queue();
        }
    }

    @Override
    public void execute(CommandContext ctx) {

        if (!init(ctx)) return;

        AudioHandler handler = (AudioHandler) ctx.getEvent().getGuild().getAudioManager().getSendingHandler();
        if (handler.getQueue().isEmpty()) {
            ctx.getEvent().getChannel().sendMessage("There is nothing in the queue!").queue();
            return;
        }

        User target;
        List<Member> found = FinderUtil.findMembers(ctx.getArgs(), ctx.getEvent().getGuild());

        if (found.isEmpty()) {
            ctx.getEvent().getChannel().sendMessage("Unable to find the user!").queue();
            return;
        } else if (found.size() > 1) {
            OrderedMenu.Builder builder = new OrderedMenu.Builder();
            for (int i = 0; i < found.size() && i < 4; i++) {
                Member member = found.get(i);
                builder.addChoice("**" + member.getUser().getName() + "**#" + member.getUser().getDiscriminator());
            }

            builder
                    .setSelection((msg, i) -> removeAllEntries(found.get(i - 1).getUser(), ctx))
                    .setText("Found multiple users:")
                    .setColor(ctx.getEvent().getGuild().getSelfMember().getColor())
                    .useNumbers()
                    .setUsers(ctx.getEvent().getAuthor())
                    .useCancelButton(true)
                    .setCancel((msg) -> {
                    })
                    .setEventWaiter(ctx.getBot().getEventWaiter())
                    .setTimeout(1, TimeUnit.MINUTES)
                    .build().display(ctx.getEvent().getChannel());
            return;
        } else {
            target = found.get(0).getUser();
        }
        removeAllEntries(target, ctx);
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Removes all entries by a user from the queue";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.DJ;
    }

    @Override
    public boolean getArgs() {
        return true;
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("fr");
        aliases.add("fd");
        aliases.add("forcedelete");
        return aliases;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`forceremove <user_id|user_name|@user>` - Forcibly removes all songs that were added by the specified user");
        return samples;
    }
}
