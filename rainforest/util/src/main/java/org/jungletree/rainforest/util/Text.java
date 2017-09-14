package org.jungletree.rainforest.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Text {

    private String text;
    private boolean bold;
    private boolean italic;
    private boolean underlined;
    private boolean strikethrough;
    private boolean obfuscated;
    private ChatColor color;
    private List<Text> extra;

    public static Text build(String message) {
        Text result = new Text();
        result.text = message;
        return result;
    }

    public static Text bold(String message) {
        Text result = new Text();
        result.text = message;
        result.bold = true;
        return result;
    }

    public static Text italic(String message) {
        Text result = new Text();
        result.text = message;
        result.italic = true;
        return result;
    }

    public static Text underlined(String message) {
        Text result = new Text();
        result.text = message;
        result.underlined = true;
        return result;
    }

    public static Text strikethrough(String message) {
        Text result = new Text();
        result.text = message;
        result.strikethrough = true;
        return result;
    }

    public static Text obfuscated(String message) {
        Text result = new Text();
        result.text = message;
        result.obfuscated = true;
        return result;
    }

    public static Text color(String message, ChatColor chatColor) {
        Text result = new Text();
        result.text = message;
        result.color = chatColor;
        return result;
    }

    public static Text build(String message, boolean bold) {
        Text result = new Text();
        result.text = message;
        result.bold = bold;
        return result;
    }

    public static Text concat(Text... messages) {
        Text result = new Text();
        result.text = "";
        result.extra = new ArrayList<>();
        Collections.addAll(result.extra, messages);
        return result;
    }

    public static Text build(String message, boolean bold, boolean italic) {
        Text result = new Text();
        result.text = message;
        result.bold = bold;
        result.italic = italic;
        return result;
    }

    public static Text build(String message, boolean bold, boolean italic, boolean underlined) {
        Text result = new Text();
        result.text = message;
        result.bold = bold;
        result.italic = italic;
        result.underlined = underlined;
        return result;
    }

    public static Text build(String message, boolean bold, boolean italic, boolean underlined, boolean strikethrough) {
        Text result = new Text();
        result.text = message;
        result.bold = bold;
        result.italic = italic;
        result.underlined = underlined;
        result.strikethrough = strikethrough;
        return result;
    }

    public static Text build(String message, boolean bold, boolean italic, boolean underlined, boolean strikethrough, boolean obfuscated) {
        Text result = new Text();
        result.text = message;
        result.bold = bold;
        result.italic = italic;
        result.underlined = underlined;
        result.strikethrough = strikethrough;
        result.obfuscated = obfuscated;
        return result;
    }

    public static Text build(String message, boolean bold, Text... extras) {
        Text result = new Text();
        result.text = message;
        result.bold = bold;
        List<Text> extrasList = new ArrayList<>();
        Collections.addAll(extrasList, extras);
        result.extra = extrasList;
        return result;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isUnderlined() {
        return underlined;
    }

    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }

    public boolean isStrikethrough() {
        return strikethrough;
    }

    public void setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
    }

    public boolean isObfuscated() {
        return obfuscated;
    }

    public void setObfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public List<Text> getExtra() {
        return extra;
    }

    public void setExtra(List<Text> extra) {
        this.extra = extra;
    }
}
