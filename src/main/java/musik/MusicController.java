package musik;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import main.Main;
import net.dv8tion.jda.api.entities.Guild;

public class MusicController {
	
	private final Guild guild;
	private final AudioPlayer player;
	private final Queue queue;
	
	public MusicController(Guild guild) {
		this.guild = guild;
		this.player = Main.INSTANCE.getAudioPlayerManager().createPlayer();
		this.queue = new Queue(this);
		
		this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
		this.player.setVolume(10);
	}

	public Guild getGuild() {
		return guild;
	}

	public AudioPlayer getPlayer() {
		return player;
	}
	
	public Queue getQueue() {
		return queue;
	}

}
