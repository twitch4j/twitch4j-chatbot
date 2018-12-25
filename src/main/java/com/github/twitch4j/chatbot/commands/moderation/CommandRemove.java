package com.github.twitch4j.chatbot.commands.moderation;

import me.philippheuer.twitch4j.events.event.irc.ChannelMessageEvent;
import me.philippheuer.twitch4j.message.commands.Command;
import me.philippheuer.twitch4j.message.commands.CommandPermission;

import java.util.Optional;

public class CommandRemove extends Command {
    /**
     * Initialize Command
     */
    public CommandRemove() {
        super();

        // Command Configuration
        setCommand("removecommand");
        setCommandAliases(new String[]{});
        setCategory("moderation");
        setDescription("Removes a dynamic command!");
        getRequiredPermissions().add(CommandPermission.MODERATOR);
        setUsageExample("removecommand youtube command2 command3");
    }

    /**
     * executeCommand Logic
     */
    @Override
    public void executeCommand(ChannelMessageEvent messageEvent) {
        super.executeCommand(messageEvent);

        // Allows multiple command names to be provided in the arguments
        for (String commandName : getParsedArguments()) {
            // Get the Command
            Optional<Command> command = getTwitchClient().getCommandHandler().getCommand(commandName);

            // Remove Logic
            if (command.isPresent()) {
                // Command found, removing
                getTwitchClient().getCommandHandler().unregisterCommand(command.get());

                // Send Response
                String response = String.format("Command %s has been removed!", command.get().getCommand());
                getTwitchClient().getMessageInterface().sendPrivateMessage(messageEvent.getUser().getName(), response);
            } else {
                // Command does not exist!
            }
        }
    }
}
