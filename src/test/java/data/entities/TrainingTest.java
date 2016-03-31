package data.entities;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrainingTest {

	@Test
	public void testRemovePlayer(){
		Court court = new Court();
		Calendar starTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		endTime.add(Calendar.HOUR_OF_DAY, 1);
		User coach = new User("c", "c@gmail.com", "c", Calendar.getInstance());
		User player = new User("p1", "p1@gmail.com", "p1", Calendar.getInstance());
		
		List<User> players = null;
		players.add(player);
		
		Training training = new Training(players, court, coach, starTime, endTime);
		
		assertFalse(training.getPlayers().isEmpty());
		training.removePlayer(player);
		assertTrue(training.getPlayers().isEmpty());
	}
}
