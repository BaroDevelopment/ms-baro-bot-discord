package com.baro.bot.discord.commands.owner;

import com.baro.bot.discord.commands.ACommand;
import com.baro.bot.discord.commands.CommandCategory;
import com.baro.bot.discord.commands.CommandContext;
import com.baro.bot.discord.commands.ICommand;
import com.baro.bot.discord.util.OtherUtil;
import net.dv8tion.jda.api.entities.Icon;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SetavatarCmd extends ACommand implements ICommand {

    public static final String COMMAND_NAME = "setavatar";

    @Override
    public void execute(CommandContext ctx) {
        String url;
        if (ctx.getArgs().isEmpty())
            if (!ctx.getEvent().getMessage().getAttachments().isEmpty() && ctx.getEvent().getMessage().getAttachments().get(0).isImage())
                url = ctx.getEvent().getMessage().getAttachments().get(0).getUrl();
            else
                url = null;
        else
            url = ctx.getArgs();
        InputStream s = OtherUtil.imageFromUrl(url);
        if (s == null) {
            ctx.getEvent().getChannel().sendMessage("Invalid or missing URL").queue();
        } else {
            try {
                ctx.getEvent().getJDA().getSelfUser().getManager().setAvatar(Icon.from(s)).queue(
                        v -> ctx.getEvent().getChannel().sendMessage("Successfully changed avatar.").queue(),
                        t -> ctx.getEvent().getChannel().sendMessage("Failed to set avatar.").queue());
            } catch (IOException e) {
                ctx.getEvent().getChannel().sendMessage("Could not load from provided URL.").queue();
            }
        }
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Sets the avatar of the bot";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.OWNER;
    }

    @Override
    public boolean getArgs() {
        return true;
    }

    @Override
    public boolean guildOnly() {
        return false;
    }

    @Override
    public List<String> getExamples() {
        List<String> samples = new ArrayList<>();
        samples.add("`setavatarj <URL>`");
        return samples;
    }
}
