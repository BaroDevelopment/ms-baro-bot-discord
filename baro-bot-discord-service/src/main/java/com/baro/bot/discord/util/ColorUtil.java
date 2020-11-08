package com.baro.bot.discord.util;

import java.awt.*;
import java.util.Random;

public class ColorUtil {

    public Color getRandomColor() {
        int R = (int) (Math.random() * 256);
        int G = (int) (Math.random() * 256);
        int B = (int) (Math.random() * 256);
        Color color_rand = new Color(R, G, B); //random color, but can be bright or dull

        //to get rainbow, pastel colors
        Random random = new Random();
        final float hue = random.nextFloat();
        final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
        final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
        Color color_hsl = Color.getHSBColor(hue, saturation, luminance);

        return color_hsl;
    }
}
