package nl.Steffion.BlockHunt.Events;

import nl.Steffion.BlockHunt.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class EndArenaEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private List<Player> winners = null;
    private List<Player> losers = null;
    private Arena arena = null;

    public EndArenaEvent(List<Player> winners, List<Player> losers, Arena arena) {
        this.winners = winners;
        this.losers = losers;
        this.arena = arena;
    }

    public List<Player> getWinners() {
        return winners;
    }

    public List<Player> getLosers() {
        return losers;
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
