import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import queue.data.Generation;
import rmi.data.GameUI;
import rmi.interfaces.IAnalysis;
import rmi.interfaces.IGameEngineServer;
import rmi.interfaces.IRemoteRuleEditor;
import rmi.interfaces.IRemoteUI_Server;


public class UI {

	public static void main(String[] args) throws InterruptedException {
		
		try {
			// 0. Lookup Service
			IRemoteRuleEditor ruleEditor = (IRemoteRuleEditor) Naming.lookup(IRemoteRuleEditor.FULLSERVICEIDENTIFIER);
                        IAnalysis analyse = (IAnalysis) Naming.lookup(IAnalysis.RMI_ADDR);
                        IGameEngineServer gameEngine = (IGameEngineServer) Naming.lookup(IGameEngineServer.FULLSERVICEIDENTIFIER);
                        IRemoteUI_Server uiServer = (IRemoteUI_Server) Naming.lookup("rmi://143.93.91.71:1098/RemoteUIBackendIntern");
                        /*
                        List<GameUI> list = ruleEditor.getUserGames(4);
                        
                        for(GameUI g : list) {
                    
                            System.out.println(g.getGameId() + " " + g.getGameName());
                            
                        }
                        */
                        
                        GameUI g = ruleEditor.getGameObject(4, 5);
                        
                        
                        boolean f = gameEngine.sendIDsToEngine(g.getUserId(), g.getGameId());
                        System.out.println(f);
                        if(f) {
                            
                            for(int i=0;i<100;i++) {
                                Generation gen = uiServer.getNextGeneration(g.getUserId(), g.getGameId());
                            
                                System.out.println(gen);
                            }
                           
                            gameEngine.stop(g.getUserId(), g.getGameId());
                            
                        }
                        
                        
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
