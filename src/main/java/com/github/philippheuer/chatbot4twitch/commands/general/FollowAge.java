package com.github.philippheuer.chatbot4twitch.commands.general;

import me.philippheuer.twitch4j.chat.commands.Command;
import me.philippheuer.twitch4j.enums.CommandPermission;
import me.philippheuer.twitch4j.events.event.MessageEvent;
import me.philippheuer.twitch4j.model.Follow;
import me.philippheuer.twitch4j.model.User;

import java.util.Optional;

public class FollowAge extends Command {
    /**
     * Initialize Command
     */
    public FollowAge() {
        super();

        // Command Configuration
        setCommand("followage");
        setCommandAliases(new String[]{"followsince", "following"});
        setCategory("general");
        setDescription("Display's the first follow date!");
        getRequiredPermissions().add(CommandPermission.EVERYONE);
        setUsageExample("");
    }

    /**
     * executeCommand Logic
     */
    @Override
    public void executeCommand(MessageEvent messageEvent) {
        super.executeCommand(messageEvent);

        // Get Target (or self)
        User commandTarget = getCommandArgumentTargetUserOrSelf();

        // Get Follow Age
		Optional<Follow> follow = getTwitchClient().getUserEndpoint().checkUserFollowByChannel(commandTarget.getId(), messageEvent.getChannel().getId());

		// Response
		if(follow.isPresent()) {
			// Following
			String response = String.format("%s is following since %s!", commandTarget.getName(), follow.get().getCreatedAt().toString());
			sendMessageToChannel(messageEvent.getChannel().getName(), response);
		} else {
			// Not Following
			String response = String.format("%s is not following!", commandTarget.getName());
			sendMessageToChannel(messageEvent.getChannel().getName(), response);
		}
    }
}
