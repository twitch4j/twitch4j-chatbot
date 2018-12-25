package com.github.twitch4j.chatbot.features;

import me.philippheuer.twitch4j.events.EventSubscriber;
import me.philippheuer.twitch4j.events.event.irc.ChannelMessageEvent;

public class WriteChannelChatToConsole {

    /**
     * Subscribe to the ChannelMessage Event and write the output to the console
     */
    @EventSubscriber
    public void onChannelMessage(ChannelMessageEvent event) {
        System.out.println("Channel [" +event.getChannel().getDisplayName() + "] - User[" + event.getUser().getDisplayName() + "] - Message [" + event.getMessage() + "]");
    }
    
}
