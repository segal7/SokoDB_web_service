package db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

//****************************//
 //plsk:player-level solved key//
//****************************//
@SuppressWarnings("serial")
@Embeddable
public class PLSK implements Serializable {
	@Column(name = "player")
	private String _player_name;
	@Column(name = "lvl")
	private String _level_name;

	PLSK ()
	{
		_player_name=null;
		_level_name=null;
	}
	PLSK(String lvl_name,String player_name)
	{
		_level_name=lvl_name;
		_player_name=player_name;
	}


	public String get_player_name() {
		return _player_name;
	}
	public void set_player_name(String _player_name) {
		this._player_name = _player_name;
	}
	public String get_level_name() {
		return _level_name;
	}
	public void set_level_name(String _level_name) {
		this._level_name = _level_name;
	}


	@Override
	public int hashCode()
	{
		return _player_name.hashCode()*31 + _level_name.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;
		if(obj instanceof PLSK){
			PLSK a = (PLSK)obj;
			return this._player_name.equals(a._player_name) && this._level_name.equals(a._level_name);
		}
		return false;
	}


}
