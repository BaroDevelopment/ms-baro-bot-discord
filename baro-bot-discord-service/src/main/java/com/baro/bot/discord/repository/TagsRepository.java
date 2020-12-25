package com.baro.bot.discord.repository;

import com.baro.bot.discord.model.entities.TagsEntity;
import com.baro.bot.discord.model.entities.TagsEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository<TagsEntity, TagsEntityId> {
    List<TagsEntity> findByTagsEntityIdServerId(Long serverId);
}
