package com.baro.bot.discord.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "music")
public class MusicEntity {

    @Id
    @Column(unique = true, nullable = false)
    private Long serverId;

    @Column(nullable = false)
    private String djRoleId;
    @Column(nullable = false)
    private String textChannelId;
    @Column(nullable = false)
    private String voiceChannelId;
    @Column(nullable = false)
    private boolean trackRepeat;
    @Column(nullable = false)
    private boolean playlistRepeat;
    @Column(nullable = false)
    private String defaultPlaylist;

    public MusicEntity() {
    }

    public MusicEntity(Long serverId, String djRoleId, String textChannelId, String voiceChannelId, String defaultPlaylist, boolean trackRepeat, boolean playlistRepeat) {
        this.serverId = serverId;
        this.djRoleId = djRoleId;
        this.textChannelId = textChannelId;
        this.voiceChannelId = voiceChannelId;
        this.defaultPlaylist = defaultPlaylist;
        this.trackRepeat = trackRepeat;
        this.playlistRepeat = playlistRepeat;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getDjRoleId() {
        return djRoleId;
    }

    public void setDjRoleId(String djRoleId) {
        this.djRoleId = djRoleId;
    }

    public String getTextChannelId() {
        return textChannelId;
    }

    public void setTextChannelId(String textChannelId) {
        this.textChannelId = textChannelId;
    }

    public String getVoiceChannelId() {
        return voiceChannelId;
    }

    public void setVoiceChannelId(String voiceChannelId) {
        this.voiceChannelId = voiceChannelId;
    }

    public boolean isTrackRepeat() {
        return trackRepeat;
    }

    public void setTrackRepeat(boolean trackRepeat) {
        this.trackRepeat = trackRepeat;
    }

    public boolean isPlaylistRepeat() {
        return playlistRepeat;
    }

    public void setPlaylistRepeat(boolean playlistRepeat) {
        this.playlistRepeat = playlistRepeat;
    }

    public String getDefaultPlaylist() {
        return defaultPlaylist;
    }

    public void setDefaultPlaylist(String defaultPlaylist) {
        this.defaultPlaylist = defaultPlaylist;
    }
}
