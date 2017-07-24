package db;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name= "Players")
public class Player {
	@Id
	private String player_name;


	public Player(String name){
		this.player_name = name;
	}

	public String get_player_name() {
		return player_name;
	}

	public void set_player_name(String _player_name) {
		this.player_name = _player_name;
	}
	public Player() {
		player_name=null;
	}

	@Override
	public String toString() {

		return "[player| " + player_name + " ]";
	}

}
