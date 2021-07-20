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
		
        if(args.length <= 1) {
            assert e.getMember() != null;
            String nickname = e.getMember().getNickname() != null ? e.getMember().getNickname() : "";
            e.getTextChannel().sendMessage(
                    new EmbedBuilder().setColor(Color.GREEN).setDescription("Info über: " + e.getMessage().getAuthor().getName() + " #" + e.getMessage().getAuthor().getDiscriminator() + "\n "
                            + "Nickname: " + nickname + "\n"
                            + "ID: " + e.getMessage().getAuthor().getId() + "\n"
                            + "Avatar: " + e.getMessage().getAuthor().getEffectiveAvatarUrl() + "\n"
                            + "Bot?: " + e.getMessage().getAuthor().isBot() + "\n"
                            + "Status: " + e.getMessage().getAuthor().getJDA().getStatus() + "\n"
                    ).build()
            ).queue();
        } else {
            assert e.getMember() != null;
            String nickname = e.getMessage().getMentionedMembers().get(0).getNickname() != null ? e.getMessage().getMentionedMembers().get(0).getNickname() : "";
            e.getTextChannel().sendMessage(
                    new EmbedBuilder().setColor(Color.GREEN).setDescription("Info über: " + e.getMessage().getMentionedMembers().get(0).getUser().getName() + " #" + e.getMessage().getAuthor().getDiscriminator() + "\n "
                            + "Nickname: " + nickname + "\n"
                            + "ID: " + e.getMessage().getMentionedMembers().get(0).getUser().getId() + "\n"
                            + "Avatar: " + e.getMessage().getMentionedMembers().get(0).getUser().getEffectiveAvatarUrl() + "\n"
                            + "Server joined at:" + e.getMessage().getMentionedMembers().get(0).getTimeJoined() + "\n"
                            + "Bot?: " + e.getMessage().getMentionedMembers().get(0).getUser().isBot() + "\n"
                            + "Status: " + e.getMessage().getMentionedMembers().get(0).getUser().getJDA().getStatus()+ "\n"
                    ).build()
            ).queue();
        }
        return null;
    }

}
