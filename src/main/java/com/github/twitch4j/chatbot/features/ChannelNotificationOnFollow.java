package com.github.twitch4j.chatbot.features;

import me.philippheuer.twitch4j.events.EventSubscriber;
import me.philippheuer.twitch4j.events.event.channel.FollowEvent;

public class ChannelNotificationOnFollow {

    /**
     * Subscribe to the Follow Event
     */
    @EventSubscriber
    public void onFollow(FollowEvent event) {
        String message = String.format("%s is now following %s!", event.getUser().getDisplayName(), event.getChannel().getDisplayName());

        event.getClient().getMessageInterface().sendMessage(event.getChannel().getName(), message);
    }

}
