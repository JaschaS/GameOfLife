import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import queue.data.Generation;

import rmi.data.GameUI;
import rmi.data.rules.NumericRule;
import rmi.data.rules.RulePattern;
import rmi.interfaces.IGameEngineServer;
import rmi.interfaces.IRemoteRuleEditor;
import rmi.interfaces.IRemoteUI_Server;


public class UI {

	public static void main(String[] args) {
		
		try {
			// 0. Lookup Service
			IRemoteRuleEditor ruleEditor = (IRemoteRuleEditor) Naming.lookup(IRemoteRuleEditor.FULLSERVICEIDENTIFIER);
			//IRemoteRuleEditor ruleEditor = (IRemoteRuleEditor) Naming.lookup("rmi://localhost/" + IRemoteRuleEditor.SERVICENAME);
			
			// 1. Generate new GameUI Object for user
			final int userId = 1703;
			
			/*final GameUI game = ruleEditor.generateNewGame(userId, "StartGameTest2");
			System.out.println(game);
			
			// 2. Save modified GameUI Object back
			final RulePattern oneBirthrule = new RulePattern(new boolean[]{true,true,true,false,false,true,true,true});
			final NumericRule oneDeathrule = new NumericRule();
			oneDeathrule.setTriggerAtNumberOfNeighbours(5, true); //Death at 5 alive neigbours
			oneDeathrule.setTriggerAtNumberOfNeighbours(4, true); //Death at 4 alive neigbours
			game.setBorderOverflow(true);
			game.addBirthRule(oneBirthrule);
			game.addDeathRule(oneDeathrule);
			final boolean[][] field = new boolean[][] {
					new boolean[]{false,false,false,false,false,false,false,false,false},
					new boolean[]{false,true ,true ,true ,false,true ,false,true ,false},
					new boolean[]{false,true ,false,false,false,true ,false,true ,false},
					new boolean[]{false,true ,true ,true ,false,true ,true ,true ,false},
					new boolean[]{false,true ,false,false,false,true ,false,true ,false},
					new boolean[]{false,true ,false,false,false,true ,false,true ,false},
					new boolean[]{false,false,false,false,false,false,false,false,false},
				};
			game.setStartGen(field);
			System.out.println(game);
			ruleEditor.saveGame(game);
			
			final int gameId = game.getGameId();
			*/
			// 3. Load GameUI for given gameId
			final GameUI loadedGame = ruleEditor.getGameObject(userId, 3);
			
			System.out.println(loadedGame);
			
			/*
			// Currently with timeouts / exceptions if analysis or engine offlline
			// 4. Load all games of given user
			final List<GameUI> allGames = ruleEditor.getUserGames(userId);
			for (final GameUI gameUI : allGames) {
				System.out.println(gameUI);
			}
			*/
                        
                        IGameEngineServer gameEngine = (IGameEngineServer) Naming.lookup(IGameEngineServer.FULLSERVICEIDENTIFIER);
                        IRemoteUI_Server uiServer = (IRemoteUI_Server) Naming.lookup("rmi://143.93.91.71/" + "RemoteUIBackend");
                        
                        gameEngine.sendIDsToEngine(loadedGame.getUserId(), loadedGame.getGameId());
                        
                        Generation gen = uiServer.getNextGeneration(loadedGame.getUserId(), loadedGame.getGameId());
                        
                        if( gen == null ) System.out.println( "null" );
                        else System.out.println("not null");
                        
                        gameEngine.stop(loadedGame.getUserId(), loadedGame.getGameId());
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, e);
		}
		
	}
	
}
