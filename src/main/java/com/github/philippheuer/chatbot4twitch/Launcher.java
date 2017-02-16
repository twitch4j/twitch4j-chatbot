package com.github.philippheuer.chatbot4twitch;

public class Launcher {

    public static void main(String[] args) {
        Bot bot = new Bot();
        bot.registerCommands();
        bot.start();
    }

}
