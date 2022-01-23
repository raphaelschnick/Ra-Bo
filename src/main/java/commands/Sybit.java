package commands;

import commands.manager.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Sybit implements ServerCommand {
    @Override
    public List<Message> performCommand(Member m, TextChannel channel, Message message, MessageReceivedEvent e) {
        channel.sendMessage("```" +
                " #######################################\n" +
                        "########################################\n" +
                        "######################################% \n" +
                        "##################################      \n" +
                        "##############################          \n" +
                        "###########################          ###\n" +
                        "######*          #######/          %####\n" +
                        "#######(          ####/          #######\n" +
                        "#########          *%          %########\n" +
                        "##########        (*          ##########\n" +
                        "##########%      %           ###########\n" +
                        "###########%    /,          ############\n" +
                        "#############   #          #############\n" +
                        "############## .           #############\n" +
                        "########################################\n" +
                        "########################################" +
                "```").queue();
        return null;
    }
}
