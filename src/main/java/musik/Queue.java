package musik;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Queue {
	
	private List<AudioTrack> queueList;
	protected MusicController controller;
	
	public Queue(MusicController controller) {
		this.setController(controller);
		this.setQueueList(new ArrayList<>());
	}

	public boolean next() {
		
		if(this.queueList.size() >= 1) {
			AudioTrack track = queueList.remove(0);
			this.controller.getPlayer().playTrack(track);
		}
		return false;
	}

	public void addTrackToQueue(AudioTrack track) {
		this.queueList.add(track);
		
		if(controller.getPlayer().getPlayingTrack() == null) {
			next();
		}
	}
	
	public MusicController getController() {
		return controller;
	}

	public void setController(MusicController controller) {
		this.controller = controller;
	}

	public List<AudioTrack> getQueueList() {
		return queueList;
	}

	public void setQueueList(List<AudioTrack> queueList) {
		this.queueList = queueList;
	}

	public void shuffle() {
		Collections.shuffle(queueList);
		
	}
}
