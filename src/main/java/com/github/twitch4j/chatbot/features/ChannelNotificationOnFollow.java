package com.github.twitch4j.chatbot.features;

import com.github.philippheuer.events4j.EventManager;
import com.github.twitch4j.chat.events.channel.FollowEvent;

public class ChannelNotificationOnFollow {

    /**
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
     */
    public ChannelNotificationOnFollow(EventManager eventManager) {
        eventManager.onEvent(FollowEvent.class).subscribe(event -> onFollow(event));
    }

    /**
     * Subscribe to the Follow Event
     */
    public void onFollow(FollowEvent event) {
        String message = String.format(
                "%s is now following %s!",
                event.getUser().getName(),
                event.getChannel().getName()
        );

        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }

}
