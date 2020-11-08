package com.baro.bot.discord.features;

import com.baro.bot.discord.service.BaroBot;
import com.baro.bot.discord.util.EmoteUtil;
import com.baro.bot.discord.util.Flags;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class VoteSystem {

    public void handleVotes(BaroBot bot, MessageReceivedEvent event) {
        if (!event.getChannel().getType().equals(ChannelType.TEXT)) return;
        if (event.getTextChannel().getTopic() != null
                && event.getTextChannel().getTopic().toUpperCase().contains(Flags.BAROBOT_VOTE.toString())
                && !event.getAuthor().isBot()) {
            EmoteUtil eo = new EmoteUtil(bot);
            event.getMessage().addReaction(eo.getEmote("plus1")).queue();
            event.getMessage().addReaction(eo.getEmote("minus1")).queue();
        }
    }
}
