package main;

import commands.manager.CommandListener;
import commands.manager.CommandManager;
import listener.TalkListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Main {

    public static final String PREFIX = "!";

    public static Main INSTANCE;

    private CommandManager cmdMan;

    public static void main(String[] args)  {

        try {
            new Main();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public Main() throws LoginException {
        INSTANCE = this;
        JDABuilder builder = JDABuilder.createDefault(SECRETS.TOKEN);

        builder.setActivity(Activity.playing(PREFIX + "help"));
        builder.setStatus(OnlineStatus.ONLINE);

        builder.setAutoReconnect(true);

        this.setCmdMan(new CommandManager());

        // Listener
        builder.addEventListeners(new CommandListener());
        builder.addEventListeners(new TalkListener());

        builder.build();
        System.out.println("Bot fährt hoch!");
    }

    public CommandManager getCmdMan() {
        return cmdMan;
    }

    public void setCmdMan(CommandManager cmdMan) {
        this.cmdMan = cmdMan;
    }
}
