package nl.Steffion.BlockHunt.Events;

import nl.Steffion.BlockHunt.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JoinArenaEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player = null;
    private Arena arena = null;

    public JoinArenaEvent(Player player, Arena arena) {
        this.player = player;
        this.arena = arena;
    }

    public Player getPlayer() {
        return player;
    }

    public Arena getArena() {
        return arena;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
