package com.baro.bot.discord.util;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class OtherUtil {

    public static String loadResource(Object clazz, String name) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clazz.getClass().getResourceAsStream(name)))) {
            StringBuilder sb = new StringBuilder();
            reader.lines().forEach(line -> sb.append("\r\n").append(line));
            return sb.toString().trim();
        } catch (IOException ex) {
            return null;
        }
    }

    public static InputStream imageFromUrl(String url) {
        if (url == null)
            return null;
        try {
            URL u = new URL(url);
            URLConnection urlConnection = u.openConnection();
            urlConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
            return urlConnection.getInputStream();
        } catch (IOException | IllegalArgumentException ignore) {
        }
        return null;
    }

    public static Activity parseGame(String game) {
        if (game == null || game.trim().isEmpty() || game.trim().equalsIgnoreCase("default"))
            return null;
        String lower = game.toLowerCase();
        if (lower.startsWith("playing"))
            return Activity.playing(game.substring(7).trim());
        if (lower.startsWith("listening to"))
            return Activity.listening(game.substring(12).trim());
        if (lower.startsWith("listening"))
            return Activity.listening(game.substring(9).trim());
        if (lower.startsWith("watching"))
            return Activity.watching(game.substring(8).trim());
        if (lower.startsWith("streaming")) {
            String[] parts = game.substring(9).trim().split("\\s+", 2);
            if (parts.length == 2) {
                return Activity.streaming(parts[1], "https://twitch.tv/" + parts[0]);
            }
        }
        return Activity.playing(game);
    }

    public static OnlineStatus parseStatus(String status) {
        if (status == null || status.trim().isEmpty())
            return OnlineStatus.ONLINE;
        OnlineStatus st = OnlineStatus.fromKey(status);
        return st == null ? OnlineStatus.ONLINE : st;
    }
}

