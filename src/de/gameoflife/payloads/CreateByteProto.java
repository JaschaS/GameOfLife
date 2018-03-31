package de.gameoflife.payloads;

import com.google.protobuf.ByteString;
import org.json.JSONObject;

/**
 *
 * @author JScholz
 */
public class CreateByteProto {
    public static void main(String[] args) {
    
        GoFGame.Game g = GoFGame.Game.newBuilder ()
                .setUserID ( 1 )
                .setGameID ( 1 )
                .setGameName ( "")
                .setBorderOverflow ( true )
                .setHistoryAvailable ( false )
                .setAnalysisAvailable ( false )
                .setStartGeneration ( 0, true)
                .build ();
                
    
        ByteString s = g.toByteString ();
        JSONObject o = new JSONObject ();
        o.put ( "error", false);
        o.put ( "errorMessage", "");
        o.put ( "message", s.toStringUtf8 ());
        
        System.out.println ( o );
    }
}
