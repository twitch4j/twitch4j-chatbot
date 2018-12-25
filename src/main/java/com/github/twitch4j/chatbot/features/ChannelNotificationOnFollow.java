package com.github.twitch4j.chatbot.features;

import com.github.philippheuer.events4j.annotation.EventSubscriber;
import com.github.twitch4j.chat.events.channel.FollowEvent;

public class ChannelNotificationOnFollow {

    /**
     * Subscribe to the Follow Event
     */
    @EventSubscriber
    public void onFollow(FollowEvent event) {
        String message = String.format(
                "%s is now following %s!",
                event.getUser().getName(),
                event.getChannel().getName()
        );

        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }

}
