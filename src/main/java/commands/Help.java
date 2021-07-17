package commands;

import commands.manager.ServerCommand;
import main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class Help implements ServerCommand {

    @Override
    public List<Message> performCommand(Member m, TextChannel channel, Message message, MessageReceivedEvent e) {
        channel.sendMessage(
                new EmbedBuilder().setColor(Color.GREEN).setDescription(
                ":pushpin: **PREFIX:**  " + Main.PREFIX + "\r\n" +
                "\r\n" +
                ":man_shrugging: `" + Main.PREFIX + "help` --> Get Help.\r\n" +
                "\r\n" +
                ":gear: `" + Main.PREFIX + "info <@member>` --> Get all information about a member.\r\n" +
                "\r\n" +
                ":pencil2: `" + Main.PREFIX + "clear <number>` --> Clear Messages.\r\n" +
                "\r\n" +
                ":headphones: `" + Main.PREFIX + "m help` --> Get Music Help.\r\n" +
                "\r\n" +
                ":bulb: `" + Main.PREFIX + "server` --> Get the latest server stats.\r\n").build()
        ).complete();
        return null;
    }
}
