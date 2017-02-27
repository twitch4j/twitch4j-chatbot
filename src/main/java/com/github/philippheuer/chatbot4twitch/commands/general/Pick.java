package com.github.philippheuer.chatbot4twitch.commands.general;

import me.philippheuer.twitch4j.chat.commands.Command;
import me.philippheuer.twitch4j.chat.commands.CommandPermission;
import me.philippheuer.twitch4j.events.event.ChannelMessageEvent;

import java.util.concurrent.ThreadLocalRandom;

public class Pick extends Command {
    /**
     * Initialize Command
     */
    public Pick() {
        super();

        // Command Configuration
        setCommand("pick");
        setCommandAliases(new String[]{});
        setCategory("general");
        setDescription("Lets the bot pick one out of several options.");
        getRequiredPermissions().add(CommandPermission.EVERYONE);
        setUsageExample("");
    }

    /**
     * executeCommand Logic
     */
    @Override
    public void executeCommand(ChannelMessageEvent messageEvent) {
        super.executeCommand(messageEvent);

        // Get Choices from CommandContent
        String[] choices = getCommandArgumentSeperatedList("or");

        // Handle Command
        if (choices.length >= 2) {
            Integer randomIndex = ThreadLocalRandom.current().nextInt(0, choices.length);

            // Prepare Response
            String response = String.format("I choose %s.", choices[randomIndex]);

            // Send Response
            sendMessageToChannel(messageEvent.getChannel().getName(), response);
        }
    }

}
