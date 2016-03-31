package data.entities;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.loader.custom.Return;
import org.omg.CORBA.PUBLIC_MEMBER;

@Entity
public class Training {

	@Id
	@GeneratedValue
	private int id;
	
	@ManyToMany
	@JoinColumn
	private List<User> players;
	
	@ManyToOne
	private Court court;
	
	@ManyToOne
	private User coach;
	
	private Calendar startTime;
	private Calendar endTime;
	
	public final int MAX_PLAYERS = 2;
	
	public Training(List<User> players,Court court, User coach,Calendar startTime, Calendar endTime){
		this.players = players;
		this.court = court;
		this.coach = coach;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<User> getPlayers() {
		return players;
	}

	public void setPlayers(List<User> players) {
		this.players = players;
	}

	public Court getCourt() {
		return court;
	}

	public void setCourt(Court court) {
		this.court = court;
	}

	public User getCoach() {
		return coach;
	}

	public void setCoach(User coach) {
		this.coach = coach;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
				return true;
		}
		if (obj == null) {
				return false;
		}
		if (getClass() != obj.getClass()) {
				return false;
		}
		return id == ((Training) obj).id;
	}
	
	@Override
	public String toString() {
		return "Training [id=" + id + ", players=" + players + ", coach=" + coach + ", court=" + court
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	
	public void removePlayer(User player){
		players.remove(player);
	}
	
	public boolean addPlayer(User player){
		if(players.size() < MAX_PLAYERS){
			players.add(player);
			return true;
		}
		else{
			return false;
		}
	}
}
