package musik;

import main.Main;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
	
	public ConcurrentHashMap<Long, MusicController> controller;
	
	public PlayerManager() {
		this.controller = new ConcurrentHashMap<>();
	}
	public MusicController getController(long guildId) {
		MusicController mc = null;
		
		if(this.controller.containsKey(guildId)) {
			mc = this.controller.get(guildId);
		} else {
			mc = new MusicController(Main.INSTANCE.getJda().getGuildById(guildId));
			this.controller.put(guildId, mc);
		}
		return mc;
	}
	
	public long getGuildByPlayerHash(int hash) {
		for(MusicController controller : this.controller.values()) {
			if(controller.getPlayer().hashCode() == hash) {
				return controller.getGuild().getIdLong();
			}
		}
		return -1;
	}

}
