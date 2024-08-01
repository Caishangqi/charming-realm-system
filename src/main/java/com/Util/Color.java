package com.Util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Color {

    // 私有构造函数防止实例化
    private Color() {
    }

    // 静态内部类，包含唯一的Color实例
    private static class SingletonHelper {
        private static final Color INSTANCE = new Color();
    }

    // 公共方法返回唯一的实例
    public static Color getInstance() {
        return SingletonHelper.INSTANCE;
    }

    // 解析包含HexColor的单行文本
    public static String parseColor(String message) {
        return getInstance().color(message);
    }

    // 解析包含HexColor的多行文本
    public static List<String> parseColor(List<String> messages) {
        if (messages != null && messages.size() > 0) {
            return getInstance().color(messages);
        } else return null;
    }

    public static String parseColorAndPlaceholder(OfflinePlayer player, String message) {
        return PlaceholderAPI.setPlaceholders(player, parseColor(message));
    }

    protected String color(String message) {
        if (message == null) return null;

        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, String.valueOf(net.md_5.bungee.api.ChatColor.of(color)));
            matcher = pattern.matcher(message);
        }

        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
    }

    protected List<String> color(List<String> messages) {
        return messages.stream().map(this::color).collect(Collectors.toList());
    }
}
