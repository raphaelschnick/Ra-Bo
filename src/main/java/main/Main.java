package main;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args)  {
        JDABuilder builder = JDABuilder.createDefault(SECRETS.TOKEN);

        builder.setActivity(Activity.playing("-help"));
        builder.setStatus(OnlineStatus.ONLINE);

        builder.setAutoReconnect(true);

        try {
            builder.build();
            System.out.println("Bot fährt hoch!");
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
