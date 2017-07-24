package db;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name="PlayerSolvedLevel")
public class Player_solved_level {
	@EmbeddedId
	PLSK _key;
	@Column (name ="steps")
	private int _steps;

	@Column (name ="seconds")
	private int _seconds;

	@Column (name ="solution")
	private String _solution;	

	public String get_solution() {
		return _solution;
	}

	public void set_solution(String _solution) {
		this._solution = _solution;
	}

	public int get_steps() {
		return _steps;
	}

	public void set_steps(int _steps) {
		this._steps = _steps;
	}

	public int get_seconds() {
		return _seconds;
	}

	public void set_seconds(int _seconds) {
		this._seconds = _seconds;
	}

	public String get_level_name() {
		return _key.get_level_name();
	}

	public void set_level_name(String _level_name) {
		_key.set_level_name(_level_name);
	}

	public String get_Player_name() {
		return _key.get_player_name();
	}

	public void set_Player_name(String _Player_name) {
		_key.set_player_name( _Player_name);
	}
	public Player_solved_level() {
		_steps=0;
		_seconds=0;
		_key= new PLSK();
		_solution=null;
	}
	public Player_solved_level ( int steps, int time, String lvl, String plyr,String solution)
	{
		_steps=steps;
		_seconds=time;
		_key = new PLSK(lvl,plyr);
		_solution = solution;

	}

	@Override
	public String toString() {
		return "[PLS | " + _key.get_level_name() + ", "+_key.get_player_name() + ","+_steps+","+_seconds + "," + _solution +"]";
	}


}
