package com.baro.bot.discord.util;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.net.URL;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

public class FormatUtil {

    public static String formatTime(OffsetDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
                .withLocale(Locale.ENGLISH));
    }

    public static String formatTime(long duration) {
        if (duration == Long.MAX_VALUE)
            return "LIVE";
        long seconds = Math.round(duration / 1000.0);
        long hours = seconds / (60 * 60);
        seconds %= 60 * 60;
        long minutes = seconds / 60;
        seconds %= 60;
        return (hours > 0 ? hours + ":" : "") + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
    }

    public static String progressBar(double percent) {
        String str = "";
        for (int i = 0; i < 12; i++)
            if (i == (int) (percent * 12))
                str += "\uD83D\uDD18"; // 🔘
            else
                str += "▬";
        return str;
    }

    public static String volumeIcon(int volume) {
        if (volume == 0)
            return "\uD83D\uDD07"; // 🔇
        if (volume < 30)
            return "\uD83D\uDD08"; // 🔈
        if (volume < 70)
            return "\uD83D\uDD09"; // 🔉
        return "\uD83D\uDD0A";     // 🔊
    }

    public static String listOfTChannels(List<TextChannel> list, String query) {
        String out = " Multiple text channels found matching \"" + query + "\":";
        for (int i = 0; i < 6 && i < list.size(); i++)
            out += "\n - " + list.get(i).getName() + " (<#" + list.get(i).getId() + ">)";
        if (list.size() > 6)
            out += "\n**And " + (list.size() - 6) + " more...**";
        return out;
    }

    public static String listOfVChannels(List<VoiceChannel> list, String query) {
        String out = " Multiple voice channels found matching \"" + query + "\":";
        for (int i = 0; i < 6 && i < list.size(); i++)
            out += "\n - " + list.get(i).getName() + " (ID:" + list.get(i).getId() + ")";
        if (list.size() > 6)
            out += "\n**And " + (list.size() - 6) + " more...**";
        return out;
    }

    public static String listOfRoles(List<Role> list, String query) {
        String out = " Multiple text channels found matching \"" + query + "\":";
        for (int i = 0; i < 6 && i < list.size(); i++)
            out += "\n - " + list.get(i).getName() + " (ID:" + list.get(i).getId() + ")";
        if (list.size() > 6)
            out += "\n**And " + (list.size() - 6) + " more...**";
        return out;
    }

    public static String filter(String input) {
        return input.replace("\u202E", "")
                .replace("@everyone", "@\u0435veryone") // cyrillic letter e
                .replace("@here", "@h\u0435re") // cyrillic letter e
                .trim();
    }

    public static boolean isUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static String getTimestamp(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

        if (hours > 0)
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        else
            return String.format("%02d:%02d", minutes, seconds);
    }
}
