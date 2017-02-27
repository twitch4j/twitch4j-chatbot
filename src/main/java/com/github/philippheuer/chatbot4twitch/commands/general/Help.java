package com.github.philippheuer.chatbot4twitch.commands.general;

import me.philippheuer.twitch4j.chat.commands.Command;
import me.philippheuer.twitch4j.chat.commands.CommandPermission;
import me.philippheuer.twitch4j.events.event.ChannelMessageEvent;

import java.util.Optional;

public class Help extends Command {
    /**
     * Initialize Command
     */
    public Help() {
        super();

        // Command Configuration
        setCommand("help");
        setCommandAliases(new String[]{});
        setCategory("general");
        setDescription("Displays information about a command.");
        getRequiredPermissions().add(CommandPermission.EVERYONE);
        setUsageExample("");
    }

    /**
     * executeCommand Logic
     */
    @Override
    public void executeCommand(ChannelMessageEvent messageEvent) {
        super.executeCommand(messageEvent);

        // Parameters
        String cmdName = getParsedContent();

        // Get Command Info
        Optional<Command> cmd = getTwitchClient().getCommandHandler().getCommand(cmdName);

        // Command exists?
        if (cmd.isPresent()) {
            if (cmd.get().hasPermissions(messageEvent)) {
                // User has Permissions for Command
                String response = String.format("Command: %s | Description: %s", cmd.get().getCommand(), cmd.get().getDescription());
                sendMessageToChannel(messageEvent.getChannel().getName(), response);
            } else {
                // User has no permissions for this command
                return;
            }
        } else {
            String response = String.format("%s is not a valid command.", cmdName);
            sendMessageToChannel(messageEvent.getChannel().getName(), response);
        }
    }
}
