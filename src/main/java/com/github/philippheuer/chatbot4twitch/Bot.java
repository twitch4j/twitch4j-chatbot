package com.github.philippheuer.chatbot4twitch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.philippheuer.chatbot4twitch.commands.general.*;
import com.github.philippheuer.chatbot4twitch.commands.moderation.CommandAdd;
import com.github.philippheuer.chatbot4twitch.commands.moderation.CommandRemove;
import com.github.philippheuer.chatbot4twitch.features.ChannelNotificationOnDonation;
import com.github.philippheuer.chatbot4twitch.features.ChannelNotificationOnFollow;
import com.github.philippheuer.chatbot4twitch.features.ChannelNotificationOnSubscription;
import com.github.philippheuer.chatbot4twitch.features.WriteChannelChatToConsole;
import me.philippheuer.twitch4j.TwitchClient;
import me.philippheuer.twitch4j.auth.model.OAuthCredential;
import me.philippheuer.twitch4j.endpoints.ChannelEndpoint;
import me.philippheuer.twitch4j.events.EventSubscriber;
import me.philippheuer.twitch4j.events.event.ChannelMessageEvent;

import java.io.File;
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

        // Initialization
        twitchClient = TwitchClient.builder()
                .clientId(getConfiguration().getApi().get("twitch_client_id"))
                .clientSecret(getConfiguration().getApi().get("twitch_client_secret"))
                .ircCredential(new OAuthCredential(getConfiguration().getCredentials().get("irc")))
                .configurationAutoSave(true)
                .configurationDirectory(new File("config").getAbsolutePath())
                .build();

        // Register this class to recieve events using the EventSubscriber Annotation
        twitchClient.getDispatcher().registerListener(this);
    }

    /**
     * Method to register all commands
     */
    public void registerCommands() {
        // General
        twitchClient.getCommandHandler().registerCommand(About.class);
        twitchClient.getCommandHandler().registerCommand(Dice.class);
        twitchClient.getCommandHandler().registerCommand(Eightball.class);
        twitchClient.getCommandHandler().registerCommand(Pick.class);
        twitchClient.getCommandHandler().registerCommand(Rate.class);
        twitchClient.getCommandHandler().registerCommand(Help.class);

        // Moderation
        twitchClient.getCommandHandler().registerCommand(CommandAdd.class);
        twitchClient.getCommandHandler().registerCommand(CommandRemove.class);
    }

    /**
     * Method to register all features
     */
    public void registerFeatures() {
        twitchClient.getDispatcher().registerListener(new WriteChannelChatToConsole());
        twitchClient.getDispatcher().registerListener(new ChannelNotificationOnFollow());
        twitchClient.getDispatcher().registerListener(new ChannelNotificationOnSubscription());
        twitchClient.getDispatcher().registerListener(new ChannelNotificationOnDonation());
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
            Long channelId = twitchClient.getUserEndpoint().getUserIdByUserName(channel).get();
            ChannelEndpoint channelEndpoint = twitchClient.getChannelEndpoint(channelId);
            channelEndpoint.registerEventListener();
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
