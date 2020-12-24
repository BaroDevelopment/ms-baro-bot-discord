package com.baro.bot.discord.repository;

import com.baro.bot.discord.model.entities.TagsEntity;
import com.baro.bot.discord.model.entities.TagsEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<TagsEntity, TagsEntityId> {

}
