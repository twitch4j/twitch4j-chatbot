package com.github.philippheuer.chatbot4twitch.commands.general;

import me.philippheuer.twitch4j.events.event.irc.ChannelMessageEvent;
import me.philippheuer.twitch4j.message.commands.Command;
import me.philippheuer.twitch4j.message.commands.CommandPermission;

public class About extends Command {
    /**
     * Initialize Command
     */
    public About() {
        super();

        // Command Configuration
        setCommand("about");
        setCommandAliases(new String[]{"development", "framework"});
        setCategory("general");
        setDescription("Displays information about the bot.");
        getRequiredPermissions().add(CommandPermission.EVERYONE);
        setUsageExample("");
    }

    /**
     * executeCommand Logic
     */
    @Override
    public void executeCommand(ChannelMessageEvent messageEvent) {
        super.executeCommand(messageEvent);

        // Prepare Response
        String response = String.format("This bot was created using the Twitch4J API.");

        // Send Response
        sendMessageToChannel(messageEvent.getChannel().getName(), response);
    }
}
