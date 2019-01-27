package com.github.twitch4j.chatbot.features;

import com.github.philippheuer.events4j.EventManager;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

public class WriteChannelChatToConsole {

    /**
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
     */
    public WriteChannelChatToConsole(EventManager eventManager) {
        eventManager.onEvent(ChannelMessageEvent.class).subscribe(event -> onChannelMessage(event));
    }

    /**
     * Subscribe to the ChannelMessage Event and write the output to the console
     */
    public void onChannelMessage(ChannelMessageEvent event) {
        System.out.printf(
                "Channel [%s] - User[%s] - Message [%s]%n",
                event.getChannel().getName(),
                event.getUser().getName(),
                event.getMessage()
        );
    }

}
