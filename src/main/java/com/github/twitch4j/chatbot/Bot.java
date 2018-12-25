package com.github.twitch4j.chatbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chatbot.features.ChannelNotificationOnDonation;
import com.github.twitch4j.chatbot.features.ChannelNotificationOnFollow;
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
    private TwitchClient twitchClient;

    /**
     * Constructor
     */
    public Bot() {
        // Load Configuration
        loadConfiguration();

        TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();

        //region Chat related configuration
        OAuth2Credential credential = new OAuth2Credential(
                "twitch",
                configuration.getCredentials().get("irc")
        );

        clientBuilder = clientBuilder
                .withChatAccount(credential)
                .withEnableChat(true);
        //endregion

        //region Api related configuration
        clientBuilder = clientBuilder
                .withClientId(configuration.getApi().get("twitch_client_id"))
                .withClientSecret(configuration.getApi().get("twitch_client_secret"))
                .withEnableHelix(true)
                /*
                 * GraphQL has a limited support
                 * Don't expect a bunch of features enabling it
                 */
                .withEnableGraphQL(true)
                /*
                 * Kraken is going to be deprecated
                 * see : https://dev.twitch.tv/docs/v5/#which-api-version-can-you-use
                 * It is only here so you can call methods that are not (yet)
                 * implemented in Helix
                 */
                .withEnableKraken(true);
        //endregion

        // Build the client out of the configured builder
        twitchClient = clientBuilder.build();

        // Register this class to receive events using the EventSubscriber Annotation
        twitchClient.getEventManager().registerListener(this);
    }

    /**
     * Method to register all features
     */
    public void registerFeatures() {
        twitchClient.getEventManager().registerListener(new WriteChannelChatToConsole());
        twitchClient.getEventManager().registerListener(new ChannelNotificationOnFollow());
        twitchClient.getEventManager().registerListener(new ChannelNotificationOnSubscription());
        twitchClient.getEventManager().registerListener(new ChannelNotificationOnDonation());
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

}
