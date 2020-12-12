package com.baro.bot.discord.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:image.properties")
public class ImageConfig {

    private final String banImage;
    private final String kickImage;

    public ImageConfig(
            @Value("${image.ban}") String banImage,
            @Value("${image.kick}") String kickImage) {
        this.banImage = banImage;
        this.kickImage = kickImage;
    }

    public String getBanImage() {
        return banImage;
    }

    public String getKickImage() {
        return kickImage;
    }
}
