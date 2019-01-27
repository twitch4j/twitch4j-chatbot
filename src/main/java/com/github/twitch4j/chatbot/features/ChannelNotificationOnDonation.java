package com.github.twitch4j.chatbot.features;

import com.github.philippheuer.events4j.EventManager;
import com.github.twitch4j.chat.events.channel.DonationEvent;

public class ChannelNotificationOnDonation {

    /**
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
     */
    public ChannelNotificationOnDonation(EventManager eventManager) {
        eventManager.onEvent(DonationEvent.class).subscribe(event -> onDonation(event));
    }

    /**
     * Subscribe to the Donation Event
     */
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
