/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.connection.rmi;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.data.GameUI;
import rmi.data.rules.Evaluable;
import rmi.interfaces.IAnalysis;
import rmi.interfaces.IGameEngineServer;
import rmi.interfaces.IRemoteRuleEditor;


/**
 *
 * @author Daniel
 */
public class GameHandler implements IGameConfiguration, IConnectionRuleEditor, 
        IConnectionGameEngine, IConnectionAnalysis{
    /*
     * @param gameList Contains all games which the user has opened in his GUI. 
     * For performance it creates an index over the game ids.
     */
    private HashMap<Integer,GameUI> gameList = null;
    
    /*
     * @param ruleEditor Handles the communication between the RuleEditor and UI.
     */
    private IRemoteRuleEditor ruleEditor = null;
    
    /*
     * @param ruleEditor Handles the communication between the Engine and UI.
     */
    private IGameEngineServer gameEngine = null;
    
    /*
     * @param ruleEditor Handles the communication between the Analyse and UI.
     */
    private IAnalysis analysis = null;
    
    public GameHandler(){
        gameList = new HashMap<>();
    }

    @Override
    public boolean establishConnection() {
        try {
            ruleEditor = (IRemoteRuleEditor) Naming.lookup("rmi://143.93.91.72/RuleEditor");
            //TODO: verbindung zu analyse und engine aufbauen
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            //TODO
            Logger.getLogger(GameHandler.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
        return true;
    }
    
    @Override
    public boolean closeConnection() {
        //TODO: eigentlich sollte die verbindung sauber abgebaut werden
        ruleEditor = null;
        return true;
    }

    @Override
    public boolean generateNewGame(int userId, String name) {
        try {
            if (ruleEditor != null){
                GameUI game = ruleEditor.generateNewGame(userId, name);
                gameList.put(game.getGameId(), game);
            } else {
                //TODO: throw error that the connection must be established first
                
                return false;
            }
        } catch (RemoteException ex) {
            //TODO
            return false;
        }
        return true;
    }
    
    @Override
    public GameUI copyGame(final int userId, final int gameId){
        //TODO: muss noch implementiert werden... jascha soll nix mit dem objekt zu tun haben
        return null;
    }
    
    @Override
    public boolean saveGame(final int gameId) {
        try {
            ruleEditor.saveGame(gameList.get(gameId));
        } catch (RemoteException ex) {
            //TODO
            return false;
        }
        return true;
    }

    @Override
    public boolean loadGame(final int userId, final int gameId) {
        try {
            gameList.put(gameId, ruleEditor.getGameObject(userId,gameId));
        } catch (RemoteException ex) {
            //TODO
            return false;
        }
        return true;
    }

    @Override
    public List<GameUI> getGameList(final int userId) {
        //TODO: muss noch implementiert werden... jascha soll nix mit dem objekt zu tun haben
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override
    public void setGameName(final int gameId, String gameName) {
        gameList.get(gameId).setGameName(gameName);
    }

    @Override
    public void setStartGen(final int gameId, boolean[][] startGen) {
        gameList.get(gameId).setStartGen(startGen);
    }

    @Override
    public void addDeathRule(final int gameId, Evaluable rule) {
        gameList.get(gameId).addDeathRule(rule);
    }

    @Override
    public void addBirthRule(final int gameId, Evaluable rule) {
        gameList.get(gameId).addBirthRule(rule);
    }

    @Override
    public void setBorderOverflow(final int gameId, boolean borderOverflow) {
        gameList.get(gameId).setBorderOverflow(borderOverflow);
    }
    
     /*public static void main(String[] args) {

        
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
         
    }*/
}
