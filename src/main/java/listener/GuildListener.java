package listener;

import main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GuildListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent e) {
        String id = e.getGuild().getId();
        String name = e.getGuild().getName();
        String query = String.format("INSERT INTO `config`(`id`, `name`) VALUES ('%s', '%s');", id, name);
        Main.INSTANCE.getMySQL().execute(query);
        if(e.getGuild().createTextChannel("ra-bo").complete().getType().equals(ChannelType.TEXT)) {
            TextChannel channel = e.getGuild().getTextChannelsByName("ra-bo", true).get(0);
            channel.sendMessage(
                    new EmbedBuilder().setColor(Color.GREEN).setDescription(
                            "**Welcome!** \n\n" +
                                    "Please select a Prefix for **Ra-Bo**\n" +
                                    "\r\n" +
                                    ":one: = ! \r\n" +
                                    ":two:  = - \r\n" +
                                    ":three: = .").build()
            ).queue(msg ->{
                msg.addReaction("1️⃣").queue();
                msg.addReaction("2️⃣").queue();
                msg.addReaction("3️⃣").queue();
            });
        }
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent e) {
        String id = e.getGuild().getId();
        Main.INSTANCE.getMySQL().execute("DELETE FROM config WHERE id='" + id + "';");
    }

    @Override
    public void onMessageReactionAdd (MessageReactionAddEvent e) {
        if (e.getChannel().getName().equals("ra-bo") && !Objects.requireNonNull(e.getMember()).getUser().isBot()) {
            String reaction = e.getReactionEmote().getEmoji();
            String id = e.getGuild().getId();
            switch (reaction) {
                case "1️⃣":
                    Main.INSTANCE.getMySQL().execute(String.format("UPDATE config SET prefix='!' WHERE id='%s'", id));
                    break;
                case "2️⃣":
                    Main.INSTANCE.getMySQL().execute(String.format("UPDATE config SET prefix='-' WHERE id='%s'", id));
                    break;
                case "3️⃣":
                    Main.INSTANCE.getMySQL().execute(String.format("UPDATE config SET prefix='.' WHERE id='%s'", id));
                    break;
            }
            String prefix = "!";
            String query = String.format("SELECT prefix FROM config WHERE id='%s'", id);
            try {
                prefix = Main.INSTANCE.getMySQL().executeQuery(query).getString("prefix");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            e.getChannel().deleteMessageById(e.getMessageId()).queue();
            e.getChannel().sendMessage(new EmbedBuilder().setColor(Color.GREEN).setDescription(
                    "Prefix successfully updated!\n\n New Prefix: " + prefix).build()
            ).complete().delete().queueAfter(5, TimeUnit.SECONDS);
            Objects.requireNonNull(e.getGuild().getTextChannelById(e.getChannel().getId())).delete().queueAfter(10, TimeUnit.SECONDS);
        }
    }
}
