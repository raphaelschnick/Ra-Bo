package commands;

import commands.manager.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class Info implements ServerCommand {

	@Override
	public List<Message> performCommand(Member m, TextChannel channel, Message message, MessageReceivedEvent e) {
		String[] args = message.getContentDisplay().split(" ");
		
        if(args.length <1) {
            e.getTextChannel().sendMessage(
                    new EmbedBuilder().setColor(Color.GREEN).setDescription("Info �ber: " + e.getMessage().getAuthor().getName() + " #" + e.getMessage().getAuthor().getDiscriminator() + "\n "
                            + "Nichname: " + Objects.requireNonNull(e.getMember()).getNickname() + "\n"
                            + "ID: " + e.getMessage().getAuthor().getId() + "\n"
                            + "Avatar: " + e.getMessage().getAuthor().getEffectiveAvatarUrl() + "\n"
                            + "Enter server on:" + "\n"
                            + "Bot?: " + e.getMessage().getAuthor().isBot() + "\n"
                            + "Status: " + e.getMessage().getAuthor().getJDA().getStatus() + "\n"
                            + "Tests: " + "\n"
                    ).build()
            ).queue();
        } else {
            e.getTextChannel().sendMessage(
                    new EmbedBuilder().setColor(Color.GREEN).setDescription("Info über: " + e.getMessage().getMentionedMembers().get(0).getUser().getName() + " #" + e.getMessage().getAuthor().getDiscriminator() + "\n "
                            + "Nichname: " + e.getMessage().getMentionedMembers().get(0).getNickname() + "\n"
                            + "ID: " + e.getMessage().getMentionedMembers().get(0).getUser().getId() + "\n"
                            + "Avatar: " + e.getMessage().getMentionedMembers().get(0).getUser().getEffectiveAvatarUrl() + "\n"
                            + "Server betreten am:" + e.getMessage().getMentionedMembers().get(0).getTimeJoined() + "\n"
                            + "Bot?: " + e.getMessage().getMentionedMembers().get(0).getUser().isBot() + "\n"
                            + "Status: " + e.getMessage().getMentionedMembers().get(0).getUser().getJDA().getStatus()+ "\n"
                            + "Roles: " +  e.getMessage().getMentionedMembers().get(0).getRoles() + "\n"
                    ).build()
            ).queue();

        }
        return null;
    }

}
