
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
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
            IGameEngineServer gameEngine = (IGameEngineServer) Naming.lookup(IGameEngineServer.FULLSERVICEIDENTIFIER);
            IRemoteUI_Server uiServer = (IRemoteUI_Server) Naming.lookup("rmi://143.93.91.71:1098/RemoteUIBackendIntern");
			//IRemoteRuleEditor ruleEditor = (IRemoteRuleEditor) Naming.lookup("rmi://localhost/" + IRemoteRuleEditor.SERVICENAME);
            
            boolean b = gameEngine.stop(21, 0);
            
            System.out.println("stop " + b);
            
            // 1. Generate new GameUI Object for user
            final int userId = 21;
            /*
             final GameUI game = ruleEditor.generateNewGame(userId, "RMI Test for UI");
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
            final GameUI loadedGame = ruleEditor.getGameObject(userId, 0);

            System.out.println(loadedGame);

            boolean s = gameEngine.sendIDsToEngine(userId, loadedGame.getGameId());

            if (s) {

                Generation g = uiServer.getNextGeneration(userId, loadedGame.getGameId());

                if (g != null) {

                    int[][] config = g.getConfig();

                    for (int i = 0; i < config.length; ++i) {
                        for (int j = 0; j < config[i].length; ++j) {
                            System.out.print(config[i][j] + " ");
                        }
                        System.out.println("");
                    }

                } else {
                    System.out.println("generation = null");
                }

                g = gameEngine.getGeneration(userId, 0, 1);
                
                if (g != null) {

                    int[][] config = g.getConfig();

                    for (int i = 0; i < config.length; ++i) {
                        for (int j = 0; j < config[i].length; ++j) {
                            System.out.print(config[i][j] + " ");
                        }
                        System.out.println("");
                    }

                } else {
                    System.out.println("generation = null");
                }
                
                s = gameEngine.stop(userId, loadedGame.getGameId());

                if (s) {
                    System.out.println("engine stop");
                } else {
                    System.out.println("nicht gestoppt");
                }

            } else {
                System.out.println("konnte nicht gestaret werden");
            }

        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
