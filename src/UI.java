import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import queue.data.Generation;
import rmi.data.GameUI;
import rmi.interfaces.IGameEngineServer;
import rmi.interfaces.IRemoteRuleEditor;
import rmi.interfaces.IRemoteUI_Server;


public class UI {

	public static void main(String[] args) {
		
		try {
			// 0. Lookup Service
			IRemoteRuleEditor ruleEditor = (IRemoteRuleEditor) Naming.lookup(IRemoteRuleEditor.FULLSERVICEIDENTIFIER);
                        IGameEngineServer gameEngine = (IGameEngineServer) Naming.lookup(IGameEngineServer.FULLSERVICEIDENTIFIER);
                        IRemoteUI_Server uiServer = (IRemoteUI_Server) Naming.lookup("rmi://143.93.91.71:1098/RemoteUIBackendIntern");
			//IRemoteRuleEditor ruleEditor = (IRemoteRuleEditor) Naming.lookup("rmi://localhost/" + IRemoteRuleEditor.SERVICENAME);
			gameEngine.stop(21, 7);
                        //final GameUI game = ruleEditor.generateNewGame(21, "RMI Test for UI");
                        
                        //System.out.println("Test game");
                        //System.out.println(game);
			// 1. Generate new GameUI Object for user
			/*final int userId = 1703;
			
			final GameUI game = ruleEditor.generateNewGame(userId, "RMI Test for UI");
			System.out.println(game);
			
			// 2. Save modified GameUI Object back
			final RulePattern oneBirthrule = new RulePattern(new int[]{1,1,1,0,0,1,1,1});
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
                        /*List<GameUI> l = ruleEditor.getUserGames(21);
                        
                        Iterator<GameUI> it = l.iterator();
                        
                        while(it.hasNext()) {
                            System.out.println(it.next());
                        }
                        
                        //classic game
			final GameUI loadedGame = ruleEditor.getGameObject(21, 7);
			
			System.out.println(loadedGame);
			
			/*
			// Currently with timeouts / exceptions if analysis or engine offlline
			// 4. Load all games of given user
			final List<GameUI> allGames = ruleEditor.getUserGames(userId);
			for (final GameUI gameUI : allGames) {
				System.out.println(gameUI);
			}
			*/
                        /*
                        boolean s =gameEngine.sendIDsToEngine(loadedGame.getUserId(), loadedGame.getGameId());
                        System.out.println("Start: " + s);
                        if( s ) {
                         
                            for(int k = 0; k < 100; ++k) {

                                Generation gen = uiServer.getNextGeneration(loadedGame.getUserId(), loadedGame.getGameId());

                                if( gen != null) {

                                    int[][] g = gen.getConfig();
                                    System.out.println("Generation: " + gen.getGameID());
                                    for(int i=0;i<g.length;++i) {
                                        for(int j=0; j <g[i].length; ++j) System.out.print(g[i][j] + " ");
                                        System.out.println("");
                                    }

                                }
                                else System.out.println("gen == null");
                            }
                            
                            s = gameEngine.stop(loadedGame.getUserId(), loadedGame.getGameId());
                            System.out.println("Stop: " + s);
                        }
			*/
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
