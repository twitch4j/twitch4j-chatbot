package com.github.philippheuer.chatbot4twitch.features;

import me.philippheuer.twitch4j.events.EventSubscriber;
import me.philippheuer.twitch4j.events.event.channel.SubscriptionEvent;

public class ChannelNotificationOnSubscription {

    /**
     * Subscribe to the Subscription Event
     */
    @EventSubscriber
    public void onSubscription(SubscriptionEvent event) {
        String message = "";

        // New Subscription
        if(event.getMonths() <= 1) {
            message = String.format("%s has subscribed to %s!", event.getUser().getDisplayName(), event.getChannel().getDisplayName());
        }
        // Resubscription
        if(event.getMonths() > 1) {
            message = String.format("%s has subscribed to %s in his %s month!", event.getUser().getDisplayName(), event.getChannel().getDisplayName(), event.getMonths());
        }

        // Send Message
        event.getClient().getMessageInterface().sendMessage(event.getChannel().getName(), message);
    }

}
