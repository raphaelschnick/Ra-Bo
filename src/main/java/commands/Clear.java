package commands;

import commands.manager.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Clear implements ServerCommand {

	@Override
	public List<Message> performCommand(Member m, TextChannel channel, Message message, MessageReceivedEvent e) {
		String[] args = message.getContentDisplay().split(" ");

		message.delete().queue();
		if(args.length == 2) {
			try {
				int amount = Integer.parseInt(args[1]);
				channel.purgeMessages(get(channel, amount));
				channel.sendMessage("`" + amount + "` messages deleted!").complete().delete().queueAfter(3, TimeUnit.SECONDS);
			} catch (NumberFormatException e2) {
				e2.printStackTrace();
			}

		}
		return null;
	}

	public List<Message> get(MessageChannel channel, int amount){
		List<Message> messages = new ArrayList<Message>();
		int i = amount + 1;

		for(Message message : channel.getIterableHistory().cache(false)) {
			if(!message.isPinned()) {
				messages.add(message);
			}
			if(--i <= 0) break;
		}

		return messages;

	}
}
