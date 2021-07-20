package listener;

import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;


public class TalkListener extends ListenerAdapter {

	private final String tempSuffix = "[Join]";
	private final String acSuffix = "[Talk]";
	
	public void createAutoChannel(Member member, VoiceChannel joinedChannel) {
		
		Category category = joinedChannel.getParent();
		
		int position = joinedChannel.getPosition() + 1;
		
		String name = member.getUser().getName() + " " + acSuffix;
		
		VoiceChannel channel = joinedChannel.getGuild().createVoiceChannel(name)
				.setBitrate(joinedChannel.getBitrate())
				.setUserlimit(joinedChannel.getUserLimit())
				.setParent(category)
				.complete();
		joinedChannel.getManager().sync(channel).queue();
		joinedChannel.getGuild().modifyVoiceChannelPositions().selectPosition(channel).moveTo(position).queue();
		joinedChannel.getGuild().moveVoiceMember(member, channel).queue();
		
	}
	@Override
	public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent e) {
		if(e.getChannelJoined().getName().endsWith(tempSuffix)) {
			createAutoChannel(e.getMember(), e.getChannelJoined());
		}
	}
	@Override
	public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent e) {
		if(e.getChannelJoined().getName().endsWith(tempSuffix)) {
			createAutoChannel(e.getMember(), e.getChannelJoined());
			
		}
		if(e.getChannelLeft().getName().endsWith(acSuffix) && e.getChannelLeft().getMembers().isEmpty()) {
			e.getChannelLeft().delete().queue();
		}
	}
	@Override
	public void onGuildVoiceLeave (@Nonnull GuildVoiceLeaveEvent e) {
		if(e.getChannelLeft().getName().endsWith(acSuffix) && e.getChannelLeft().getMembers().isEmpty()) {
			e.getChannelLeft().delete().queue();
		}	
	}
}
