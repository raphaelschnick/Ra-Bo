package musik;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import commands.manager.ServerCommand;
import main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public  class Music implements ServerCommand {
	
    private String getTimestamp(long milis) {
        long seconds = milis / 1000;
        long hours = Math.floorDiv(seconds, 3600);
        seconds = seconds - (hours * 3600);
        long mins = Math.floorDiv(seconds, 60);
        seconds = seconds - (mins * 60);
        return (hours == 0 ? "" : hours + ":") + String.format("%02d", mins) + ":" + String.format("%02d", seconds);
    }

    public void sendErrorMsg(MessageReceivedEvent event, String content) {
        event.getTextChannel().sendMessage(
                new EmbedBuilder()
                        .setColor(Color.red)
                        .setDescription(content)
                        .build()
        ).queue();
    }

    public String help() {
        return  ":headphones: **Music help**\r\n" + 
                "\r\n" + 
                ":microphone:`!m play <url>` --> Let the bot play music.\r\n" + 
                "\r\n" + 
                ":mag: `!m now` -->  Show you the current song\r\n" + 
                "\r\n" + 
                ":twisted_rightwards_arrows: `!m shuffle` --> Activate shuffle in your playlist\r\n" + 
                "\r\n" + 
                ":track_next: `!m skip` --> Skip the current song \r\n" +
                "\r\n" + 
                ":no_entry:`!m stop` --> Stop the current song\r\n";

    }

	@Override
	public List<Message> performCommand(Member m, TextChannel channel, Message message, MessageReceivedEvent e) {
		String[] args = message.getContentDisplay().split(" ");

		if(args.length < 1) {
			sendErrorMsg(e,(help()));
			return null;
		}

		if (args.length < 2) {
			sendErrorMsg(e, help());
			return null;
		}

		switch (args[1].toLowerCase()) {
			case "help":
				e.getTextChannel().sendMessage(help()).complete();
				break;
			case "play":
			case "p":
				GuildVoiceState state;
				if((state = m.getVoiceState()) != null) {
					VoiceChannel vc;
					if((vc = state.getChannel()) != null) {
						MusicController controller = Main.INSTANCE.getPlayerManager().getController(vc.getGuild().getIdLong());
						AudioPlayerManager apm = Main.INSTANCE.getAudioPlayerManager();
						AudioManager manager = vc.getGuild().getAudioManager();

						manager.openAudioConnection(m.getVoiceState().getChannel());

						StringBuilder strBuilder = new StringBuilder();
						for(int i = 2; i < args.length; i++) strBuilder.append(args[i]).append(" ");
						String url = strBuilder.toString().trim();

						if(!url.startsWith("http")) {
							url = "ytsearch: " + url;
						}
						apm.loadItem(url, new AudioLoadResult(controller, url));
						if(controller.getPlayer().getPlayingTrack() == null) {
							apm.loadItem(url, new AudioLoadResult(controller, url));

							e.getTextChannel().sendMessage(
									new EmbedBuilder().setColor(Color.GREEN).setDescription(
									"**Now Playing:** \n" + url + "\r\n\n" +
									"\r\n" +
									":mag: = Info about the song \r\n" +
									":pause_button: = Pause song \r\n" +
									":arrow_forward: = Continue playing song \r\n" +
									":twisted_rightwards_arrows:  = Shuffle\r\n" +
									":track_next:  = Skip\r\n" +
									":no_entry:  = Stop").build()
							).queue(msg -> {
								msg.addReaction("🔍").queue();
								msg.addReaction("⏸️").queue();
								msg.addReaction("▶️").queue();
								msg.addReaction("⏭️").queue();
								msg.addReaction("🔀").queue();
								msg.addReaction("🎚️").queue();
								msg.addReaction("⛔").queue();
							});
						} else {
							controller.getPlayer().stopTrack();
							apm.loadItem(url, new AudioLoadResult(controller, url));

							e.getTextChannel().sendMessage(
									new EmbedBuilder().setColor(Color.GREEN).setDescription(
											"**Now Playing:** " + url + "\r\n\n" +
											"\r\n" +
											":mag: = Info about the song \r\n" +
											":pause_button: = Pause song \r\n" +
											":arrow_forward: = Continue playing song \r\n" +
											":twisted_rightwards_arrows:  = Shuffle\r\n" +
											":track_next:  = Skip\r\n" +
											":no_entry:  = Stop").build()
							).queue(msg -> {
								msg.addReaction("🔍").queue();
								msg.addReaction("⏸️").queue();
								msg.addReaction("▶️").queue();
								msg.addReaction("⏭️").queue();
								msg.addReaction("🔀").queue();
								msg.addReaction("🎚️").queue();
								msg.addReaction("⛔").queue();
							});

						}
					}
				}
				break;

			case "skip":
			case "s":

				MusicController controller3 = Main.INSTANCE.getPlayerManager().getController(e.getGuild().getIdLong());
				controller3.getQueue().next();
				e.getTextChannel().sendMessage(
						new EmbedBuilder().setColor(Color.ORANGE).setDescription(":track_next: Skipped").build()
				).complete().delete().queueAfter(5, TimeUnit.SECONDS);

				break;

			case "stop":
				MusicController controller2 = Main.INSTANCE.getPlayerManager().getController(e.getGuild().getIdLong());
				AudioManager manager2 = e.getGuild().getAudioManager();
				AudioPlayer player2 = controller2.getPlayer();

				player2.stopTrack();
				manager2.closeAudioConnection();
				break;

			case "shuffle":
				//MusicController controller3 = Main.INSTANCE.playerManager.getController(e.getGuild().getIdLong());
				//controller3.getQueue().shuffle();

				e.getTextChannel().sendMessage(
						new EmbedBuilder().setColor(Color.ORANGE).setDescription(":twisted_rightwards_arrows:  Shuffle modus activated").build()
				).complete().delete().queueAfter(5, TimeUnit.SECONDS);

				break;

			case "now":
			case "info":
				MusicController controller = Main.INSTANCE.getPlayerManager().getController(e.getGuild().getIdLong());
				AudioTrack track = controller.getPlayer().getPlayingTrack();
				AudioTrackInfo info = track.getInfo();
				e.getTextChannel().sendMessage(
						new EmbedBuilder()
							.setDescription("**CURRENT TRACK INFO:**")
							.addField("Title", "`" + info.title + "`", false)
							.addField("Duration", "`[ " + getTimestamp(track.getPosition()) + "/ " + getTimestamp(track.getDuration()) + " ]`", false)
							.addField("Author", "`" + info.author + "`", false)
							.build()
			   ).complete().delete().queueAfter(10, TimeUnit.SECONDS);
				break;
		}
		return null;
    }
	public List<Message> get(MessageChannel channel, int amount) {
		List<Message> messages = new ArrayList<Message>();
		int i = amount + 1;

		for(Message message : channel.getIterableHistory().cache(false)) {
			if(!message.isPinned()) {
				messages.add(message);
			}
			if(--i <= 0) break;
		}
		return messages;
	}
}

