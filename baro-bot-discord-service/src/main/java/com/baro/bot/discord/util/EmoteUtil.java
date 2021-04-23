package com.baro.bot.discord.util;

import com.baro.bot.discord.service.BaroBot;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;

public class EmoteUtil {

    private final BaroBot bot;

    public EmoteUtil(BaroBot bot) {
        this.bot = bot;
    }

    public Emote getBooleanEmote(boolean val) {
        Guild guild = bot.getJda().getGuildById(bot.getBotConfig().getEmoteGuildId());
        if (val)
            return guild.getEmotesByName("enabled", false).get(0);
        return guild.getEmotesByName("disabled", false).get(0);
    }

    public String getEmojiAsString(String name) {
        String result = "Emoji not found";
        try {
            result = bot.getJda().getGuildById(bot.getBotConfig().getEmoteGuildId()).getEmotesByName(name, false).get(0).getAsMention() + " ";
            return result;
        } catch (Exception ex) {
            System.out.println("Emoji not found. ");
        }
        return result;
    }

    public Emote getEmote(String name) {
        Emote result = null;
        try {
            result = bot.getJda().getGuildById(bot.getBotConfig().getEmoteGuildId()).getEmotesByName(name, false).get(0);
            return result;
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            System.out.println("Emoji not found. ");
        }
        return result;
    }
}
