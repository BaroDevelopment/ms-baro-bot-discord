package com.baro.bot.discord.repository;

import com.baro.bot.discord.model.entities.CommandDisabledEntity;
import com.baro.bot.discord.model.entities.CommandDisabledEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandDisabledRepository extends JpaRepository<CommandDisabledEntity, CommandDisabledEntityId> {

    List<CommandDisabledEntity> findByCommandDisabledEntityIdServerId(String serverId);

    List<CommandDisabledEntity> findByCommandDisabledEntityIdServerIdAndCommandDisabledEntityIdName(String serverId, String name);
}
