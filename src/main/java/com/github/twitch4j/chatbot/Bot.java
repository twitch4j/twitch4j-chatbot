package com.github.twitch4j.chatbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.twitch4j.chatbot.commands.general.*;
import com.github.twitch4j.chatbot.commands.moderation.CommandAdd;
import com.github.twitch4j.chatbot.commands.moderation.CommandRemove;
import com.github.twitch4j.chatbot.features.ChannelNotificationOnDonation;
import com.github.twitch4j.chatbot.features.ChannelNotificationOnFollow;
import com.github.twitch4j.chatbot.features.ChannelNotificationOnSubscription;
import com.github.twitch4j.chatbot.features.WriteChannelChatToConsole;
import com.github.twitch4j.chatbot.commands.general.*;
import me.philippheuer.twitch4j.TwitchClient;
import me.philippheuer.twitch4j.TwitchClientBuilder;

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
        twitchClient = TwitchClientBuilder.init()
                .withClientId(getConfiguration().getApi().get("twitch_client_id"))
                .withClientSecret(getConfiguration().getApi().get("twitch_client_secret"))
                .withCredential(getConfiguration().getCredentials().get("irc"))
                .withAutoSaveConfiguration(true)
                .withConfigurationDirectory(new File("config"))
                .connect();

        // Register this class to receive events using the EventSubscriber Annotation
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
            twitchClient.getMessageInterface().joinChannel(channel);
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
