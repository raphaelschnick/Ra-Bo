package musik;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class AudioLoadResult implements AudioLoadResultHandler {

	private final MusicController controller;
	private final String uri;
	
	public AudioLoadResult(MusicController controller, String uri) {
		this.controller = controller;
		this.uri = uri;
	}
	
	@Override
	public void trackLoaded(AudioTrack track) {
		controller.getPlayer().playTrack(track);
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		Queue queue = controller.getQueue();
		if(uri.startsWith("ytsearch: ")) {
			queue.addTrackToQueue(playlist.getTracks().get(0));
			return;
		}
		
		int added = 0;
		
		for(AudioTrack track : playlist.getTracks()) {
			queue.addTrackToQueue(track);
			added++;
		}
		
		controller.getGuild().getTextChannelsByName("musik", true).get(0).sendMessage(
                new EmbedBuilder().setColor(Color.GREEN).setDescription(":white_check_mark: " + added + " songs added").build()
    	).complete().delete().queueAfter(5, TimeUnit.SECONDS);
	}

	@Override
	public void noMatches() {
		
	}

	@Override
	public void loadFailed(FriendlyException exception) {
		
	}
}
