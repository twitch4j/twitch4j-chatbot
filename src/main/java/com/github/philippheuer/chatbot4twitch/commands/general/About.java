package com.github.philippheuer.chatbot4twitch.commands.general;

import me.philippheuer.twitch4j.chat.commands.Command;
import me.philippheuer.twitch4j.enums.CommandPermission;
import me.philippheuer.twitch4j.events.event.MessageEvent;

public class About extends Command {
	/**
	 * Initalize Command
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
	public void executeCommand(MessageEvent messageEvent) {
		super.executeCommand(messageEvent);

		// Prepare Response
		String response = String.format("This bot was created using the Twitch4J API.");

		// Send Response
		getTwitchClient().getIrcClient().sendMessage(messageEvent.getChannel().getName(), response);
	}
}
