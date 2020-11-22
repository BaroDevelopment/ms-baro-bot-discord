package com.baro.bot.discord.model.api.urban;

import java.io.Serializable;

public class UrbanDictionaryEntryModel implements Serializable {

    private String definition;
    private String permalink;
    private Integer thumbs_up;
    private String[] sound_urls;
    private String author;
    private String word;
    private Integer defid;
    private String current_vote;
    private String written_on;
    private String example;
    private Integer thumbs_down;

    public UrbanDictionaryEntryModel() {
    }

    public UrbanDictionaryEntryModel(String definition, String permalink, Integer thumbs_up, String[] sound_urls, String author, String word, Integer defid, String current_vote, String written_on, String example, Integer thumbs_down) {
        this.definition = definition;
        this.permalink = permalink;
        this.thumbs_up = thumbs_up;
        this.sound_urls = sound_urls;
        this.author = author;
        this.word = word;
        this.defid = defid;
        this.current_vote = current_vote;
        this.written_on = written_on;
        this.example = example;
        this.thumbs_down = thumbs_down;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public Integer getThumbs_up() {
        return thumbs_up;
    }

    public void setThumbs_up(Integer thumbs_up) {
        this.thumbs_up = thumbs_up;
    }

    public String[] getSound_urls() {
        return sound_urls;
    }

    public void setSound_urls(String[] sound_urls) {
        this.sound_urls = sound_urls;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getDefid() {
        return defid;
    }

    public void setDefid(Integer defid) {
        this.defid = defid;
    }

    public String getCurrent_vote() {
        return current_vote;
    }

    public void setCurrent_vote(String current_vote) {
        this.current_vote = current_vote;
    }

    public String getWritten_on() {
        return written_on;
    }

    public void setWritten_on(String written_on) {
        this.written_on = written_on;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public Integer getThumbs_down() {
        return thumbs_down;
    }

    public void setThumbs_down(Integer thumbs_down) {
        this.thumbs_down = thumbs_down;
    }
}
