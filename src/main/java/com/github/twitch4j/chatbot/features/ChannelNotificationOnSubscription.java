package com.github.twitch4j.chatbot.features;

import com.github.philippheuer.events4j.annotation.EventSubscriber;
import com.github.twitch4j.chat.events.channel.SubscriptionEvent;

public class ChannelNotificationOnSubscription {

    /**
     * Subscribe to the Subscription Event
     */
    @EventSubscriber
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
