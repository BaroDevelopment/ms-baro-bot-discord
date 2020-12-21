package com.baro.bot.discord.util;

import com.baro.bot.discord.commands.music.audio.QueuedTrack;
import com.baro.bot.discord.commands.music.queue.FairQueue;
import com.baro.bot.discord.service.BaroBot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jagrosh.jdautilities.menu.Paginator;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.PermissionException;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.http.protocol.HTTP.USER_AGENT;

@Component
@Profile("!test")
public class MusicUtils {

    private final BaroBot bot;

    public MusicUtils(BaroBot bot) {
        this.bot = bot;
    }

    public void spotifyPlaylistAddEmbed(List<String> tracks, User user, TextChannel channel) {
        int page = 1;
        Paginator.Builder pbuilder = new Paginator.Builder().setColumns(1)
                .setItemsPerPage(15)
                .showPageNumbers(true)
                .waitOnSinglePage(false)
                .useNumberedItems(true)
                .setFinalAction(m -> {
                    try {
                        m.clearReactions().queue();
                    } catch (PermissionException ex) {
                        m.delete().queue();
                    }
                })
                .setEventWaiter(bot.getEventWaiter())
                .setTimeout(1, TimeUnit.MINUTES);

        pbuilder.clearItems();
        tracks.stream()
                .map(this::titleShortner)
                .forEach(pbuilder::addItems);
        ColorUtil colorUtil = new ColorUtil();
        Paginator p = pbuilder.setColor(colorUtil.getRandomColor())
                .setText("[DJ] " + user.getName() + " - Added " + tracks.size() + " tracks to queue")
                .build();
        p.paginate(channel, page);
    }

    public void addPlaylistEmbed(AudioPlaylist playlist, Message message) {

        int page = 1;
        Paginator.Builder pbuilder = new Paginator.Builder().setColumns(1)
                .setItemsPerPage(15)
                .showPageNumbers(true)
                .waitOnSinglePage(false)
                .useNumberedItems(true)
                .setFinalAction(m -> {
                    try {
                        m.clearReactions().queue();
                    } catch (PermissionException ex) {
                        m.delete().queue();
                    }
                })
                .setEventWaiter(bot.getEventWaiter())
                .setTimeout(1, TimeUnit.MINUTES);

        pbuilder.clearItems();
        playlist.getTracks().stream()
                .map(at -> "[" + titleShortner(at.getInfo().title) + "](" + at.getInfo().uri + ")")
                .forEach(pbuilder::addItems);
        ColorUtil colorUtil = new ColorUtil();
        Paginator p = pbuilder.setColor(colorUtil.getRandomColor())
                .setText("[DJ] " + message.getAuthor().getName() + " - Added " + playlist.getTracks().size() + " tracks to queue")
//                .setUsers(message.getAuthor())
                .build();
        p.paginate(message.getChannel(), page);
    }

    public EmbedBuilder addMusicEmbed(AudioTrack track, User user) {
        // TODO: Add Fields [Queue position, Estimated time until playing, Channel/Author, Duration]
        ColorUtil colorUtil = new ColorUtil();
        EmbedBuilder eb = new EmbedBuilder().setColor(colorUtil.getRandomColor());
        String platform = getPlatform(track);
        String platform_icon = getPlatformIcon(platform);
        eb.setAuthor("[DJ] " + user.getName() + " - Added to queue", null, user.getEffectiveAvatarUrl());
        eb.setTitle(platform_icon + "  " + track.getInfo().title, track.getInfo().uri);
        eb.setThumbnail(getThumbnail(platform, track));
        return eb;
    }

    public String getPlatformIcon(String platform) {
        EmoteUtil eu = new EmoteUtil(bot);
        if (platform.equals("YouTube"))
            return eu.getEmojiAsString("youtube");
        if (platform.equals("SoundCloud"))
            return eu.getEmojiAsString("soundcloud");
        if (platform.equals("Vimeo"))
            return eu.getEmojiAsString("vimeo");
        if (platform.equals("Twitch"))
            return eu.getEmojiAsString("twitch");
        if (platform.equals("Local"))
            return "[Local]";
        return "NOT_FOUND";
    }

    public String getPlatform(AudioTrack track) {
        if (track.getInfo().uri.toLowerCase().contains("youtube"))
            return "YouTube";
        if (track.getInfo().uri.toLowerCase().contains("soundcloud"))
            return "SoundCloud";
        if (track.getInfo().uri.toLowerCase().contains("vimeo"))
            return "Vimeo";
        if (track.getInfo().uri.toLowerCase().contains("twitch"))
            return "Twitch";
        if (track.getInfo().uri.contains("discordapp.com/attachments/"))
            return "Local";
        return "NOT_FOUND";
    }

    public String getThumbnail(String platform, AudioTrack track) {
        String thumbnail = Statics.IMAGE_YOUTUBE_GIF;

        switch (platform) {
            case "YouTube":
                try {
                    thumbnail = new URL("http://img.youtube.com/vi/" + track.getIdentifier() + "/hqdefault.jpg").toString();
                } catch (MalformedURLException e) {
                }
                break;
            case "SoundCloud":
                String html = getPlainHtml(track.getInfo().uri);
                int pos = html.indexOf("\"artwork_url\":") + 15;
                thumbnail = html.substring(pos, html.indexOf("-large.jpg\"", pos) + 10);
                break;
            case "Twitch":
                thumbnail = Statics.IMAGE_TWITCH;
                break;
        }
        return thumbnail;
    }

    public String getPlainHtml(String path) {
        URL url;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(path);
            URLConnection c = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String titleShortner(String title_old) {
        String title = title_old
                .replaceAll("\\[", "")
                .replaceAll("]", "")
                .replaceAll("\\*", "");

        if (title.length() < 40) {
            return title;
        }
        return title.substring(0, 40) + "...";
    }

    public String getSpotifyLink(AudioPlayer player) throws Exception {

        String title = player.getPlayingTrack().getInfo().title;
        String modifiedTitle = title
                .split("\\(")[0]
                .replace("remix", "")
                .split("\\|")[0]
                .split("\\[")[0]
                .toLowerCase()
                .replace("&", "")
                .replace("", "")
                .replace("-", "")
                .replace("ft.", "")
                .replace("feat.", "")
                .replace("offical", "")
                .replace(".", "");
        String url = "https://api.spotify.com/v1/search?q=" + URLEncoder.encode(modifiedTitle, StandardCharsets.UTF_8) + "&type=track";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json");

        String access_token = getSpotifyAccessToken();

        con.setRequestProperty("Authorization", "Bearer " + access_token);
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine + "\n");
        }
        in.close();

        JSONObject jsonObj = new JSONObject(response.toString());
        JSONArray items = jsonObj.getJSONObject("tracks").getJSONArray("items");
        String spotifyURL = items.getJSONObject(0).getJSONObject("external_urls").get("spotify").toString();

        return spotifyURL;
    }

    public String getSpotifyAccessToken() {
        final OkHttpClient httpClient = new OkHttpClient();
        // form parameters
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();

        Request request = new Request.Builder()
                .url("https://accounts.spotify.com/api/token")
                .addHeader("User-Agent", "Paladin Bot")
                .addHeader("Authorization", "Basic NzQyNmM3OGJlYjYyNGJiYWFkZWI1ODQ0MzMwMzA2OWU6ZDcyMmViOTBhZDRkNDBjOGFlNTYzNTE2NzZiZjAxMjM=")
                .post(formBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                System.out.println("Unexpected code " + response);
            }

            // Get response body
            JSONObject jsonObj = new JSONObject(response.body().string());
            return jsonObj.get("access_token").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSpotifyTrackName(String track_url) throws IOException {

        String url = "https://api.spotify.com/v1/tracks/" + track_url.substring(31, 53);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json");

        String access_token = getSpotifyAccessToken();

        con.setRequestProperty("Authorization", "Bearer " + access_token);
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine + "\n");
        }
        in.close();

        JSONObject jsonObj = new JSONObject(response.toString());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(jsonObj);

        String trackName = jsonObj.getString("name");
        String artist = jsonObj.getJSONArray("artists").getJSONObject(0).getString("name");
        return trackName + " " + artist;
    }

    public List<String> getSpotifyPlaylist(String playlist_url) throws IOException {

        Pattern pattern = Pattern.compile("/\\w*\\?");
        Matcher matcher = pattern.matcher(playlist_url);

        matcher.find();
        String playlist_id = matcher.group().replace("/", "").replace("?", "");

        String url = "https://api.spotify.com/v1/playlists/" + playlist_id + "/tracks?offset=0&limit=100";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json");

        String access_token = getSpotifyAccessToken();

        con.setRequestProperty("Authorization", "Bearer " + access_token);
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine + "\n");
        }
        in.close();

        JSONObject jsonObj = new JSONObject(response.toString());
        JSONArray items = jsonObj.getJSONArray("items");

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        JsonParser jp = new JsonParser();
//        JsonElement je = jp.parse(jsonObj);
//        String prettyJsonString = gson.toJson(je);

//        System.out.println(prettyJsonString);

        List<String> entries = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            String trackName = items.getJSONObject(i).getJSONObject("track").getString("name");
            String artist = items.getJSONObject(i).getJSONObject("track").getJSONArray("artists").getJSONObject(0).getString("name");

            entries.add(trackName + " " + artist);
        }
        return entries;
    }

    public Pair<Boolean, String> handleLyrics(String title) throws IOException {

        String modifiedTitle = title.split("\\(")[0].replace("remix", "").split("\\|")[0];
        String url = "https://api.genius.com/search?q=" + URLEncoder.encode(modifiedTitle, StandardCharsets.UTF_8);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Authorization", "Bearer 20Ak6S4MIDFkGt_bXpSPh5iEsLDXEJ2Y4V6AZezGCSxZ00zJZcbnLCqb7rhDdvkb");
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine + "\n");
        }
        in.close();
        int id = 0;
        try {
            JSONObject jsonObj = new JSONObject(response.toString());
            JSONArray hits = jsonObj.getJSONObject("response").getJSONArray("hits");
            for (int i = 0; i < hits.length(); i++) {
                String lyricsURL = hits.getJSONObject(i).getJSONObject("result").get("url").toString();
                id = Integer.parseInt(hits.getJSONObject(i).getJSONObject("result").get("id").toString());
                String firstWord = modifiedTitle.split(" ")[0];
                if (lyricsURL.contains("-lyrics") && lyricsURL.contains(firstWord)) {
                    return new Pair<>(true, lyricsURL);
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return new Pair<>(false, null);
    }

    public String calcTotalQueueTime(FairQueue<QueuedTrack> queue) {
        long time = 0;

        for (QueuedTrack track : queue.getList()) {
            time += track.getTrack().getDuration();
        }
        return FormatUtil.getTimestamp(time);
    }
}
