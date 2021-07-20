package commands;

import commands.manager.ServerCommand;
import main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class Help implements ServerCommand {

    @Override
    public List<Message> performCommand(Member m, TextChannel channel, Message message, MessageReceivedEvent e) {
        String id = e.getGuild().getId();
        String prefix = "!";
        String query = String.format("SELECT prefix FROM config WHERE id='%s'", id);
        try {
            prefix = Main.INSTANCE.getMySQL().executeQuery(query).getString("prefix");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        channel.sendMessage(
                new EmbedBuilder().setColor(Color.GREEN).setDescription(
                ":pushpin: **PREFIX:**  " + prefix + "\r\n" +
                "\r\n" +
                ":man_shrugging: `" + prefix + "help` --> Get Help.\r\n" +
                "\r\n" +
                ":gear: `" + prefix + "info <@member>` --> Get all information about a member.\r\n" +
                "\r\n" +
                ":pencil2: `" + prefix + "clear <number>` --> Clear Messages.\r\n" +
                "\r\n" +
                ":headphones: `" + prefix + "m help` --> Get Music Help.\r\n" +
                "\r\n" +
                ":bulb: `" + prefix + "server` --> Get the latest server stats.").build()
        ).complete();
        channel.sendMessage(
                new EmbedBuilder().setColor(Color.ORANGE).setDescription(
                "**Get Ra-Bo on your server::** \n https://discord.com/api/oauth2/authorize?client_id=865523259701198879&permissions=8&scope=bot\r\n\n" +
                ":desktop: **Developer:** Rapha#8081 \r\n\n" +
                ":placard: **Code:** https://github.com/raphaelschnick/Ra-Bo").build()
        ).complete();
        return null;
    }
}
