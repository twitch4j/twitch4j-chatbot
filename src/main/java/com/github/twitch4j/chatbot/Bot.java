package com.github.twitch4j.chatbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.ITwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;

import com.github.twitch4j.chatbot.features.ChannelNotificationOnFollow;
import com.github.twitch4j.chatbot.features.ChannelNotificationOnLive;
import com.github.twitch4j.chatbot.features.ChannelNotificationOnSubscription;
import com.github.twitch4j.chatbot.features.WriteChannelChatToConsole;

import java.io.InputStream;

public class Bot {

    /**
     * Holds the Bot Configuration
     */
    private Configuration configuration;

    /**
     * Twitch4J API
     */
    private ITwitchClient twitchClient;

    /**
     * Constructor
     */
    public Bot() {
        // Load Configuration
        loadConfiguration();

        TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();

        //region Auth
        OAuth2Credential credential = new OAuth2Credential(
            "twitch",
            configuration.getCredentials().get("irc")
        );
        //endregion

        //region TwitchClient
        twitchClient = clientBuilder
            .withClientId(configuration.getApi().get("twitch_client_id"))
            .withClientSecret(configuration.getApi().get("twitch_client_secret"))
            .withEnableHelix(true)
            /*
             * Chat Module
             * Joins irc and triggers all chat based events (viewer join/leave/sub/bits/gifted subs/...)
             */
            .withChatAccount(credential)
            .withEnableChat(true)
            /*
             * Build the TwitchClient Instance
             */
            .build();
        //endregion
    }

    /**
     * Method to register all features
     */
    public void registerFeatures() {
        SimpleEventHandler eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);

        // Register Event-based features
        ChannelNotificationOnLive channelNotificationOnLive = new ChannelNotificationOnLive(this, eventHandler);
        ChannelNotificationOnFollow channelNotificationOnFollow = new ChannelNotificationOnFollow(eventHandler);
        ChannelNotificationOnSubscription channelNotificationOnSubscription = new ChannelNotificationOnSubscription(eventHandler);
        WriteChannelChatToConsole writeChannelChatToConsole = new WriteChannelChatToConsole(eventHandler);
    }

    /**
     * Load the Configuration
     */
    private void loadConfiguration() {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("config.yaml");

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            configuration = mapper.readValue(is, Configuration.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Unable to load Configuration ... Exiting.");
            System.exit(1);
        }
    }

    public void start() {
        // Connect to all channels
        for (String channel : configuration.getChannels()) {
            twitchClient.getChat().joinChannel(channel);
        }
    }

    public ITwitchClient getTwitchClient() {
        return twitchClient;
    }
}
