package com.github.philippheuer.chatbot4twitch.features;

import me.philippheuer.twitch4j.events.EventSubscriber;
import me.philippheuer.twitch4j.events.event.DonationEvent;
import me.philippheuer.twitch4j.events.event.FollowEvent;

public class ChannelNotificationOnDonation {

    /**
     * Subscribe to the Donation Event
     */
    @EventSubscriber
    public void onDonation(DonationEvent event) {
        String message = String.format("%s just donated %s using %s!", event.getUser().getDisplayName(), event.getAmount(), event.getSource());

        event.getClient().getIrcClient().sendMessage(event.getChannel().getName(), message);
    }

}
