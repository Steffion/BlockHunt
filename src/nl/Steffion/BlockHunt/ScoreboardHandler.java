package nl.Steffion.BlockHunt;

import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardHandler {
	public static void doScoreboard(Arena arena) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective object = board.registerNewObjective(arena.arenaName, "dummy");
		object.setDisplaySlot(DisplaySlot.SIDEBAR);
		object.setDisplayName(MessageM.replaceAll(MessageM.CType.TAG()));
		Score timeleft = object.getScore(Bukkit.getOfflinePlayer(MessageM
				.replaceAll("%ATime left:")));
		timeleft.setScore(arena.timer);
		Score seekers = object.getScore(Bukkit.getOfflinePlayer(MessageM
				.replaceAll("%NSeekers:")));
		seekers.setScore(arena.seekers.size());
		Score hiders = object.getScore(Bukkit.getOfflinePlayer(MessageM
				.replaceAll("%NHiders:")));
		hiders.setScore(arena.playersInArena.size() - arena.seekers.size());

		for (Player pl : arena.playersInArena) {
			pl.setScoreboard(board);
		}
	}

	public static void removeScoreboard(Player player) {
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
}
