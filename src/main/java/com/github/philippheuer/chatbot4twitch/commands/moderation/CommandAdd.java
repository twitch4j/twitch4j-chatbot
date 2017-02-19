package com.github.philippheuer.chatbot4twitch.commands.moderation;

import me.philippheuer.twitch4j.chat.commands.Command;
import me.philippheuer.twitch4j.chat.commands.DynamicCommand;
import me.philippheuer.twitch4j.enums.CommandPermission;
import me.philippheuer.twitch4j.events.event.MessageEvent;
import me.philippheuer.util.conversion.TypeConvert;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

public class CommandAdd extends Command {

    /**
     * Parameter: Command Name
     */
    @Option(name = "-c", aliases = "--command", usage = "The command name to trigger this commmand", required = true)
    private String pCommandName = "";

    /**
     * Parameter: Command Type
     */
    @Option(name = "-t", aliases = "--type", usage = "The type of the command")
    private String pType = "echo";

    /**
     * Parameter: Response
     */
    @Option(name = "-r", aliases = "--response", usage = "The response to write by the command", handler = StringArrayOptionHandler.class, required = true)
    private String[] pResponse;

    /**
     * Initialize Command
     */
    public CommandAdd() {
        super();

        // Command Configuration
        setCommand("addcommand");
        setCommandAliases(new String[]{});
        setCategory("moderation");
        setDescription("Add's a new command!");
        getRequiredPermissions().add(CommandPermission.BROADCASTER);
        setUsageExample("addcommand -c kappa KappaHD only.");
    }

    /**
     * executeCommand Logic
     */
    @Override
    public void executeCommand(MessageEvent messageEvent) {
        super.executeCommand(messageEvent);

        // Parameter Validation
        if (!pCommandName.matches("[A-Za-z0-9]+")) {
            // Invalid Command Naming
            return;
        }

        // Prepare Command
        DynamicCommand dynamicCommand;

        // Command Type [Echo - Simply write a static text]
        if (pType.equals("echo")) {
            // Create Command
            dynamicCommand = new DynamicCommand(pCommandName, CommandPermission.EVERYONE, TypeConvert.combineStringArray(pResponse, " "));
        } else {
            // Unknown Type
            return;
        }

        // Register Command
        getTwitchClient().getCommandHandler().registerCommand(dynamicCommand);

        // Send Response
        String response = String.format("Command %s has been added!", dynamicCommand.getCommand());
        getTwitchClient().getIrcClient().sendPrivateMessage(messageEvent.getUser().getName(), response);
    }
}
