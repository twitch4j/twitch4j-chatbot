package com.github.twitch4j.chatbot.commands.general;

import me.philippheuer.twitch4j.events.event.irc.ChannelMessageEvent;
import me.philippheuer.twitch4j.message.commands.Command;
import me.philippheuer.twitch4j.message.commands.CommandPermission;

import java.util.concurrent.ThreadLocalRandom;

public class Dice extends Command {
    /**
     * Initialize Command
     */
    public Dice() {
        super();

        // Command Configuration
        setCommand("dice");
        setCommandAliases(new String[]{"roll"});
        setCategory("general");
        setDescription("Roll a dice!");
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
        Integer diceResult = ThreadLocalRandom.current().nextInt(1, 6);

        // Prepare Response
        String response = String.format("%s rolled %d out of 6.", messageEvent.getUser().getName(), diceResult);

        // Send Response
        sendMessageToChannel(messageEvent.getChannel().getName(), response);
    }
}
