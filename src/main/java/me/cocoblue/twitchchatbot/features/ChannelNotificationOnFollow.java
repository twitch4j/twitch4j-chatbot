package me.cocoblue.twitchchatbot.features;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.FollowEvent;

public class ChannelNotificationOnFollow {

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public ChannelNotificationOnFollow(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(FollowEvent.class, this::onFollow);
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
