package com.github.twitch4j.chatbot;

public class Launcher {

    public static void main(String[] args) {
        Bot bot = new Bot();
        bot.registerFeatures();
        bot.start();
    }

}
