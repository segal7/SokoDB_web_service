package sokoban.SokoDBServer;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import db.LevelDescription;
import db.Player;
import db.Player_solved_level;
import db.SokobanDBManager;

@Path("/")
public class SokoDBService {
	
	public SokoDBService() {
	}
	
	@GET
	@Path("level_records/{level_name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Player_solved_level> getAllLevelRecords(
			@PathParam("level_name") String name,
			@DefaultValue("steps") @QueryParam("order") String order,
			@DefaultValue("") @QueryParam("search") String search_plyr
	){
		
		ArrayList<String> specificfields = new ArrayList<>();
		ArrayList<String> orderings = null;
		
		if(order.equals("alfabetic")){
			orderings = alfabetic_order();
		}
		else if(order.equals("steps"))
			orderings = stepes_order();
		else
			orderings = time_order();
		
		if(!name.isEmpty()){
			specificfields.add("_key._level_name");
			specificfields.add(name);
		}
		if(!search_plyr.isEmpty()){
			specificfields.add("_key._player_name");
			specificfields.add(search_plyr);
		}
		
		return SokobanDBManager.getInstance().getScores(specificfields, orderings);
		//return manager.getInstance().getAllPlayers();
	}
	
	@GET
	@Path("player_records/{player_name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Player_solved_level> getAllPlayerRecords(
			@PathParam("player_name") String name,
			@DefaultValue("steps") @QueryParam("order") String order,
			@DefaultValue("") @QueryParam("search") String search_lvl
	){
		ArrayList<String> specificfields = new ArrayList<>();
		ArrayList<String> orderings = null;
		
		if(order.equals("alfabetic")){
			orderings = alfabetic_order();
		}
		else if(order.equals("steps"))
			orderings = stepes_order();
		else
			orderings = time_order();
		
		if(!name.isEmpty()){
			specificfields.add("_key._player_name");
			specificfields.add(name);
		}
		if(!search_lvl.isEmpty()){
			specificfields.add("_key._level_name");
			specificfields.add(search_lvl);
		}
		
		return SokobanDBManager.getInstance().getScores(specificfields, orderings);
		//return manager.getInstance().getAllPlayers();
	}
	
	@GET
	@Path("players")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Player> getAllPlayers(){
		return SokobanDBManager.getInstance().getAllPlayers();
	}
	
	@GET
	@Path("levels")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LevelDescription> getAllLevels(){
		return SokobanDBManager.getInstance().getAllLevels();
	}

	@GET
	@Path("solution/{lvl_name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLevelSolution(
			@PathParam("lvl_name") String lvl_name
	){
		return SokobanDBManager.getInstance().getLevelSoluion(lvl_name);
	}
	
	@POST
	@Path("updateSolution")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateLvlSolution(LevelDescription lvl){
		SokobanDBManager.getInstance().add(lvl);
	}
	
	@POST
	@Path("registerHighscore")
	@Consumes(MediaType.APPLICATION_JSON)
	public void register_player_score(Player_solved_level psl){
		Player_solved_level local_psl = new Player_solved_level(psl.get_steps(), psl.get_seconds(), psl.get_level_name(), psl.get_Player_name(), psl.get_solution());
		SokobanDBManager.getInstance().registerHighscore(local_psl);
	}
	/*
	@GET
	@Path("addSolution/{level}/{player}/{solution}")
	public void updateLevelSolution(
			@PathParam("level") String lvl_name,
			@PathParam("player") String plyr_name,
			@PathParam("solution") String solution
	){
		Player_solved_level psl = new Player_solved_level(solution.length(), 10, lvl_name, plyr_name);
		//Level lvl = new Level(lvl_name,solution);
		//SokobanDBManager.getInstance().add(lvl);
		SokobanDBManager.getInstance().registerHighscore(psl, solution);
	}
	*/

	public ArrayList<String> alfabetic_order()
	{
		ArrayList<String> _orderings = new ArrayList<String>();
		_orderings.add("_key._level_name");
		_orderings.add("_key._player_name");
		_orderings.add("_seconds");
		_orderings.add("_steps");

		return _orderings;
	}
	public ArrayList<String> time_order()
	{
		ArrayList<String> _orderings = new ArrayList<String>();
		_orderings.add("_seconds");
		_orderings.add("_steps");

		return _orderings;
	}
	public ArrayList<String> stepes_order()
	{
		ArrayList<String> _orderings = new ArrayList<String>();
		_orderings.add("_steps");
		_orderings.add("_seconds");

		return _orderings;
	}

}
