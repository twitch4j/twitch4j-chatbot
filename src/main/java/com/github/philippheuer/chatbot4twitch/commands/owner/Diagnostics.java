package com.github.philippheuer.chatbot4twitch.commands.owner;

import me.philippheuer.twitch4j.chat.commands.Command;
import me.philippheuer.twitch4j.enums.CommandPermission;
import me.philippheuer.twitch4j.events.event.MessageEvent;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Diagnostics extends Command {

	/**
	 * Initalize Command
	 */
	public Diagnostics() {
		super();

		// Command Configuration
		setCommand("diagnostics");
		setCommandAliases(new String[]{});
		setCategory("owner");
		setDescription("Displays information about the bot.");
		getRequiredPermissions().add(CommandPermission.BROADCASTER);
		setUsageExample("");
	}

	/**
	 * executeCommand Logic
	 */
	@Override
	public void executeCommand(MessageEvent messageEvent) {
		super.executeCommand(messageEvent);

		// Prepare Response
		Runtime r = Runtime.getRuntime();
		Double memoryUsage = r.totalMemory() / (1024. * 1024.);
		String response = String.format("Memory usage: %s, CPU usage: %s, Threads: %s",
				memoryUsage.intValue() + "MB",
				getCpuLoad() + "%",
				Thread.activeCount()
		);

		// Send Response (in Private - sensitive information)
		sendMessageToChannel(messageEvent.getChannel().getName(), response);
	}

	/**
	 * Get CPU Load of Host System
	 *
	 * @author http://stackoverflow.com/questions/18489273/how-to-get-percentage-of-cpu-usage-of-os-from-java
	 */
	private double getCpuLoad() {
		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
			AttributeList list = mbs.getAttributes(name, new String[]{"ProcessCpuLoad"});

			if (list.isEmpty())
				return Double.NaN;

			Attribute att = (Attribute) list.get(0);
			Double value = (Double) att.getValue();

			// usually takes a couple of seconds before we get real values
			if (value == -1.0)
				return Double.NaN;
			// returns a percentage value with 1 decimal point precision
			return ((int) (value * 1000) / 10.0);
		} catch (Exception e) {
			return Double.NaN;
		}
	}
}
