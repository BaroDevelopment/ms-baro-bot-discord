
package com.baro.bot.discord.commands.information;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.util.ColorUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AvatarCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "avatar";

    @Override
    public void execute(CommandContext ctx) {

        for (User user : findUser(ctx)) {
            displayAvatar(user.getEffectiveAvatarUrl(), user.getName(), ctx.getEvent().getTextChannel());
        }
    }

    private List<User> findUser(CommandContext ctx) {
        if (ctx.getArgs().isEmpty()) return Collections.singletonList(ctx.getEvent().getAuthor());

        if (!ctx.getEvent().getMessage().getMentionedMembers().isEmpty())
            return ctx.getEvent().getMessage().getMentionedMembers()
                    .stream()
                    .map(Member::getUser)
                    .collect(Collectors.toList());

        return ctx.getEvent().getGuild().getMembers()
                .stream()
                .filter(member -> member.getUser().getId().equals(ctx.getArgs()) || member.getUser().getName().equals(ctx.getArgs()))
                .map(Member::getUser)
                .collect(Collectors.toList());
    }

    public void displayAvatar(String avatarUrl, String username, TextChannel channel) {
        EmbedBuilder eb = new EmbedBuilder().setColor(new ColorUtil().getRandomColor())
                .setAuthor(username, avatarUrl, avatarUrl)
                .setImage(avatarUrl + "?size=1024");
        channel.sendMessage(eb.build()).queue();
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Get the avatar URL of a specific user(s), or your own avatar.";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.INFORMATION;
    }

    @Override
    public boolean getArgs() {
        return false;
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("pfp");
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`avatar Baro`");
        return samples;
    }
}
