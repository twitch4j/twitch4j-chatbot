package com.github.twitch4j.chatbot.features;

import me.philippheuer.twitch4j.events.EventSubscriber;
import me.philippheuer.twitch4j.events.event.channel.DonationEvent;

public class ChannelNotificationOnDonation {

    /**
     * Subscribe to the Donation Event
     */
    @EventSubscriber
    public void onDonation(DonationEvent event) {
        String message = String.format("%s just donated %s using %s!", event.getUser().getDisplayName(), event.getAmount(), event.getSource());

        event.getClient().getMessageInterface().sendMessage(event.getChannel().getName(), message);
    }

}
