package commands.manager;

import commands.Clear;
import commands.Help;
import commands.Info;
import commands.Server;
import musik.Music;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {

    public ConcurrentHashMap<String, ServerCommand> commands;

    public CommandManager() {
        this.commands = new ConcurrentHashMap<>();

        this.commands.put("help", new Help());
        this.commands.put("server", new Server());
        this.commands.put("info", new Info());
        this.commands.put("clear", new Clear());
        this.commands.put("m", new Music());
    }

    public boolean perform(String command, Member m, TextChannel channel, Message message, MessageReceivedEvent e) {
        ServerCommand cmd;
        if((cmd = this.commands.get(command.toLowerCase())) != null) {
            cmd.performCommand(m, channel, message, e);
            return true;
        }
        return false;
    }
}
