package com.github.twitch4j.chatbot.features;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.SubscriptionEvent;

public class ChannelNotificationOnSubscription {

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public ChannelNotificationOnSubscription(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(SubscriptionEvent.class, event -> onSubscription(event));
    }

    /**
     * Subscribe to the Subscription Event
     */
    public void onSubscription(SubscriptionEvent event) {
        String message = "";

        // New Subscription
        if (event.getMonths() <= 1) {
            message = String.format(
                    "%s has subscribed to %s!",
                    event.getUser().getName(),
                    event.getChannel().getName()
            );
        }
        // Resubscription
        if (event.getMonths() > 1) {
            message = String.format(
                    "%s has subscribed to %s in his %s month!",
                    event.getUser().getName(),
                    event.getChannel().getName(),
                    event.getMonths()
            );
        }

        // Send Message
        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }
}
