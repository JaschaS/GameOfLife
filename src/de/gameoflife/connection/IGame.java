package de.gameoflife.connection;

import java.util.Date;
import java.util.List;
import rmi.data.rules.Evaluable;

/**
 *
 * @author JScholz
 */
public class IGame {

    public  int userId;
    private int gameId;
    private String gameName;

    private boolean generation[][];
    private List<Evaluable> birthRules;
    private List<Evaluable> deathRules;
    private boolean borderOverflow;

    private Date creationDate;
    private Date modifiedDate;

    private boolean historyAvailable;
    private boolean analysisAvailable;
    
    
}
