package main;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import commands.manager.CommandListener;
import commands.manager.CommandManager;
import listener.GuildListener;
import listener.TalkListener;
import musik.MusicListener;
import musik.PlayerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static final String PREFIX = "!";

    public static Main INSTANCE;

    private AudioPlayerManager audioPlayerManager;
    private PlayerManager playerManager;

    private JDA jda;

    private MySQL mySQL;

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
        JDABuilder builder = JDABuilder.createDefault(Secrets.TOKEN);

        builder.setActivity(Activity.playing(PREFIX + "help"));
        builder.setStatus(OnlineStatus.ONLINE);

        builder.setAutoReconnect(true);

        this.setAudioPlayerManager(new DefaultAudioPlayerManager());
        this.setPlayerManager(new PlayerManager());
        this.setCmdMan(new CommandManager());

        // Listener
        builder.addEventListeners(new CommandListener());
        builder.addEventListeners(new TalkListener());
        builder.addEventListeners(new MusicListener());
        builder.addEventListeners(new GuildListener());

        this.setJda(builder.build());
        System.out.println("Bot starts up!");

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);

        MySQL mySQL = new MySQL();
        this.setMySQL(mySQL);
        mySQL.init();

        shutdown();
    }

    public void shutdown() {

        new Thread(() -> {

            String line = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while((line = reader.readLine()) != null) {

                    if(line.equalsIgnoreCase("stop")) {
                        ShardManager shardMan = this.getJda().getShardManager();
                        if(shardMan != null) {
                            shardMan.setStatus(OnlineStatus.OFFLINE);
                            shardMan.shutdown();
                            this.getMySQL().disconnect();
                            System.out.println("Bot shuts down");
                        }
                        reader.close();
                    } else {
                        System.out.println("Use 'stop' to shutdown the Bot.");
                    }

                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }).start();
    }

    public CommandManager getCmdMan() {
        return cmdMan;
    }

    public void setCmdMan(CommandManager cmdMan) {
        this.cmdMan = cmdMan;
    }

    public AudioPlayerManager getAudioPlayerManager() {
        return audioPlayerManager;
    }

    public void setAudioPlayerManager(AudioPlayerManager audioPlayerManager) {
        this.audioPlayerManager = audioPlayerManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public JDA getJda() {
        return jda;
    }

    public void setJda(JDA jda) {
        this.jda = jda;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public void setMySQL(MySQL mySQL) {
        this.mySQL = mySQL;
    }
}
