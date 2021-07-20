package commands.manager;

import main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.SQLException;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        String id = e.getGuild().getId();
        String prefix = "!";
        String query = String.format("SELECT prefix FROM config WHERE id='%s'", id);
        try {
            prefix = Main.INSTANCE.getMySQL().executeQuery(query).getString("prefix");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        String message = e.getMessage().getContentDisplay();

        if(e.isFromType(ChannelType.TEXT)) {
            TextChannel channel = e.getTextChannel();

            if(message.startsWith(prefix)) {
                String[] args = message.substring(1).split(" ");
                if(args.length > 0) {
                    if(!Main.INSTANCE.getCmdMan().perform(args[0], e.getMember(), channel, e.getMessage(), e)) {
                        channel.sendMessage(
                                new EmbedBuilder().setColor(Color.RED).setDescription("Use `" + prefix + "help`").build()).complete();
                    }
                }
            }
        }
    }
}

