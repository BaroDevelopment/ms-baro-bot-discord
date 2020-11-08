package com.baro.bot.discord.repository;

import com.baro.bot.discord.model.entities.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE music SET dj_role_id = :dj_role_id WHERE server_id = :server_id")
    int setDjRoleId(@Param("dj_role_id") String dj_role_id, @Param("server_id") Long server_id);

    @Transactional
    @Modifying
    @Query("UPDATE music SET text_channel_id = :text_channel_id WHERE server_id = :server_id")
    int setTextChannelId(@Param("text_channel_id") String music_channel_id, @Param("server_id") Long server_id);

    @Transactional
    @Modifying
    @Query("UPDATE music SET voice_channel_id = :voice_channel_id WHERE server_id = :server_id")
    int setVoiceChannelId(@Param("voice_channel_id") String voice_channel_id, @Param("server_id") Long server_id);

    @Transactional
    @Modifying
    @Query("UPDATE music SET playlist_repeat = :playlist_repeat WHERE server_id = :server_id")
    int setPlaylistRepeat(@Param("playlist_repeat") boolean playlist_repeat, @Param("server_id") Long server_id);

    @Transactional
    @Modifying
    @Query("UPDATE music SET track_repeat = :track_repeat WHERE server_id = :server_id")
    int setTracktRepeat(@Param("track_repeat") boolean track_repeat, @Param("server_id") Long server_id);
}
