package musik;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import main.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.Queue;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackManager extends AudioEventAdapter {

    private final AudioPlayer PLAYER;
    private final Queue<AudioInfo> queue;

    public TrackManager(AudioPlayer player) {
        this.PLAYER = player;
        this.queue = new LinkedBlockingQueue<AudioInfo>();
    }

    public void queue(AudioTrack track, Member author) {
        AudioInfo info = new AudioInfo(track, author);
        queue.add(info);

        if (PLAYER.getPlayingTrack() == null) {
            PLAYER.playTrack(track);
        }
    }

    public LinkedHashSet<AudioInfo> getQueue() {
        return new LinkedHashSet<>(queue);
    }

    public AudioInfo getInfo(AudioTrack track) {
        return queue.stream()
                .filter(info -> info.getTrack().equals(track))
                .findFirst().orElse(null);
    }

    public void purgeQueue() {
        queue.clear();
    }

    public void shuffleQueue() {
        List<AudioInfo> cQueue = new ArrayList<>(getQueue());
        AudioInfo current = cQueue.get(0);
        cQueue.remove(0);
        Collections.shuffle(cQueue);
        cQueue.add(0, current);
        purgeQueue();
        queue.addAll(cQueue);
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        AudioInfo info = queue.element();
        VoiceChannel vChan = Objects.requireNonNull(info.getAuthor().getVoiceState()).getChannel();

        if (vChan == null)
            player.stopTrack();
        else
            info.getAuthor().getGuild().getAudioManager().openAudioConnection(vChan);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        long guildId = Main.INSTANCE.getPlayerManager().getGuildByPlayerHash(player.hashCode());
        MusicController controller = Main.INSTANCE.getPlayerManager().getController(guildId);
        controller.getQueue().controller.getQueue().next();
    }

}
