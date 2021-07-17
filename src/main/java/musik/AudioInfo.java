package musik;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Member;

public class AudioInfo {

    private final AudioTrack TRACK;
    private final Member AUTHOR;

    public AudioInfo(AudioTrack track, Member author) {
        this.TRACK = track;
        this.AUTHOR = author;
    }

    public AudioTrack getTrack() {
        return TRACK;
    }

    public Member getAuthor() {
        return AUTHOR;
    }

}
