package com.github.twitch4j.chatbot.features;

import com.github.philippheuer.events4j.annotation.EventSubscriber;
import com.github.twitch4j.chat.events.channel.DonationEvent;

public class ChannelNotificationOnDonation {

    /**
     * Subscribe to the Donation Event
     */
    @EventSubscriber
    public void onDonation(DonationEvent event) {
        String message = String.format(
                "%s just donated %s using %s!",
                event.getUser().getName(),
                event.getAmount(),
                event.getSource()
        );

        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }

}
