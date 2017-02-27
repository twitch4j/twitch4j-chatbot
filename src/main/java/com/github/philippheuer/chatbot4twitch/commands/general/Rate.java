package com.github.philippheuer.chatbot4twitch.commands.general;

import me.philippheuer.twitch4j.chat.commands.Command;
import me.philippheuer.twitch4j.chat.commands.CommandPermission;
import me.philippheuer.twitch4j.events.event.ChannelMessageEvent;

import java.util.concurrent.ThreadLocalRandom;

public class Rate extends Command {
    /**
     * Initialize Command
     */
    public Rate() {
        super();

        // Command Configuration
        setCommand("rate");
        setCommandAliases(new String[]{});
        setCategory("general");
        setDescription("Rates a given user. If no user is given it rates you.");
        getRequiredPermissions().add(CommandPermission.EVERYONE);
        setUsageExample("");
    }

    /**
     * executeCommand Logic
     */
    @Override
    public void executeCommand(ChannelMessageEvent messageEvent) {
        super.executeCommand(messageEvent);

        // Roll the Dice
        Integer diceResult = ThreadLocalRandom.current().nextInt(1, 10);

        // Prepare Response
        String response = String.format("I rate %s a %d out of 10.", messageEvent.getUser().getName(), diceResult);

        // Send Response
        sendMessageToChannel(messageEvent.getChannel().getName(), response);
    }
}