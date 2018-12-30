package com.github.twitch4j.chatbot.features;

import com.github.philippheuer.events4j.annotation.EventSubscriber;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

public class WriteChannelChatToConsole {

    /**
     * Subscribe to the ChannelMessage Event and write the output to the console
     */
    @EventSubscriber
    public void onChannelMessage(ChannelMessageEvent event) {
        System.out.printf(
                "Channel [%s] - User[%s] - Message [%s]%n",
                event.getChannel().getName(),
                event.getUser().getName(),
                event.getMessage()
        );
    }

}
