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

import javax.security.auth.login.LoginException;

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
        System.out.println("Bot fährt hoch!");

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);

        MySQL mySQL = new MySQL();
        this.setMySQL(mySQL);
        mySQL.init();
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
