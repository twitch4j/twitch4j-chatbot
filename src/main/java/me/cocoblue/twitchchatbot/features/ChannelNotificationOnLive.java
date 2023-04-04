package me.cocoblue.twitchchatbot.features;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import me.cocoblue.twitchchatbot.dto.BotDTO;

public class ChannelNotificationOnLive {

    private final BotDTO botDTO;

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param botDTO          the botDTO instance
     * @param eventHandler SimpleEventHandler
     */
    public ChannelNotificationOnLive(BotDTO botDTO, SimpleEventHandler eventHandler) {
        this.botDTO = botDTO;
        eventHandler.onEvent(ChannelGoLiveEvent.class, this::onGoLive);
    }

    /**
     * Subscribe to the Follow Event
     */
    public void onGoLive(ChannelGoLiveEvent event) {
        String message = String.format(
            "%s is now live!",
            event.getChannel().getName()
        );

        botDTO.getTwitchClient().getChat().sendMessage(event.getChannel().getName(), message);
    }

}
