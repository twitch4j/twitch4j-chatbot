package com.github.twitch4j.chatbot.features;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chatbot.Bot;
import com.github.twitch4j.events.ChannelGoLiveEvent;

public class ChannelNotificationOnLive {

    private final Bot bot;

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param bot          the bot instance
     * @param eventHandler SimpleEventHandler
     */
    public ChannelNotificationOnLive(Bot bot, SimpleEventHandler eventHandler) {
        this.bot = bot;
        eventHandler.onEvent(ChannelGoLiveEvent.class, this::onGoLive);
    }

    /**
     * Subscribe to the Follow Event
     */
    public void onGoLive(ChannelGoLiveEvent event) {
        String message = String.format(
            "%s is now live!",
            event.getChannel().getName()
        );

        bot.getTwitchClient().getChat().sendMessage(event.getChannel().getName(), message);
    }

}
