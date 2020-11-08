package com.baro.bot.discord.util;

import com.baro.bot.discord.service.BaroBot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;

public class StatusUtil {

    BaroBot bot;

    public StatusUtil(BaroBot bot) {
        this.bot = bot;
    }

    public Emote getStatusEmote(Member member) {
        EmoteUtil eu = new EmoteUtil(bot);
        switch (member.getOnlineStatus().name()) {
            case "DO_NOT_DISTURB":
                return eu.getEmote("dnd");
            case "ONLINE":
                return eu.getEmote("online");
            case "OFFLINE":
                return eu.getEmote("offline");
            case "IDLE":
                return eu.getEmote("idle");
            default:
                return eu.getEmote("streaming");
        }
    }

    public Emote getStatusEmote(OnlineStatus status) {
        EmoteUtil eu = new EmoteUtil(bot);
        switch (status.name()) {
            case "DO_NOT_DISTURB":
                return eu.getEmote("dnd");
            case "ONLINE":
                return eu.getEmote("online");
            case "OFFLINE":
                return eu.getEmote("offline");
            case "IDLE":
                return eu.getEmote("idle");
            default:
                return eu.getEmote("streaming");
        }
    }
}
