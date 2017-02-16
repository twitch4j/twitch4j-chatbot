package com.github.philippheuer.chatbot4twitch.commands.general;

import me.philippheuer.twitch4j.chat.commands.Command;
import me.philippheuer.twitch4j.enums.CommandPermission;
import me.philippheuer.twitch4j.events.event.MessageEvent;

import java.util.concurrent.ThreadLocalRandom;

public class Eightball extends Command {
    /**
     * Eightball Responses
     */
    String[] responses = new String[]
            {
                    "It is certain",
                    "It is decidedly so",
                    "Without a doubt",
                    "Yes, definitely",
                    "You may rely on it",
                    "As I see it, yes",
                    "Most likely",
                    "Outlook good",
                    "Yes",
                    "Signs point to yes",

                    "Reply hazy, try again",
                    "Ask again later",
                    "Better not tell you now",
                    "Cannot predict now",
                    "Concentrate and ask again",

                    "Don't count on it",
                    "My reply is no",
                    "My sources say no",
                    "Outlook not so good",
                    "Very doubtful"
            };

    /**
     * Initialize Command
     */
    public Eightball() {
        super();

        // Command Configuration
        setCommand("8ball");
        setCommandAliases(new String[]{"magicball", "eightball"});
        setCategory("general");
        setDescription("Ask the magic eight ball a question.");
        getRequiredPermissions().add(CommandPermission.SUBSCRIBER);
        getRequiredPermissions().add(CommandPermission.MODERATOR);
        getRequiredPermissions().add(CommandPermission.BROADCASTER);
        setUsageExample("Is this thing rigged?");
    }

    /**
     * executeCommand Logic
     */
    @Override
    public void executeCommand(MessageEvent messageEvent) {
        super.executeCommand(messageEvent);

        // Parse Parameters
        String question = this.getParsedContent();
        if (question.length() == 0) {
            return;
        }

        // Get Response Index
        Integer randomIndex = ThreadLocalRandom.current().nextInt(0, responses.length);

        // Prepare Response
        String response = String.format("Question: %s | Response: %s.", getParsedContent(), responses[randomIndex]);

        // Send Response
        sendMessageToChannel(messageEvent.getChannel().getName(), response);
    }
}
