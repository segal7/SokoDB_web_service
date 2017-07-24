package db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity( name = "Levels")
public class LevelDescription implements Serializable{
	@Id
	private String level_name;
	@Column
	private String level_solution;
	
	public LevelDescription(){
		this.level_name = null;
		this.level_solution = null;
	}
	
	public LevelDescription(String name){
		this.level_name = name;
		this.level_solution = null;
	}
	
	public LevelDescription(String name,String solution){
		this.level_name = name;
		this.level_solution = solution;
	}
	
	public void setLevel_name(String level_name){
		this.level_name = level_name;
		this.level_solution = null;
	}
	public String getLevel_name(){
		return this.level_name;
	}
	
	public void setLevel_solution(String level_solution){
		this.level_solution = level_solution;
	}
	
	public String getLevel_solution(){
		return this.level_solution;
	}
	
	@Override
	public String toString() {
		return "[Level| " + level_name + "," + level_solution + " ]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof LevelDescription))
			return false;
		return this.level_name.equals(((LevelDescription)obj).level_name);
	}
	@Override
	public int hashCode() {
		return this.level_name.hashCode();
	}
}
