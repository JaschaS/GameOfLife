package de.gameoflife.application;

/**
 *
 * @author JScholz
 */
public final class User {
    
    private static User instance;
    private final String username;
    private final int id;
    
    private User( String username, int id ) {
    
        this.username = username;
        this.id = id;
    
    }
    
    public static void create( String username, int id ) {
    
        instance = new User( username, id );
        
    }
    
    public static void removeInstance() {
    
        instance = null;
        
    }
    
    public static User getInstance() {
        return instance;     
    }

    public String getUsername() {
        return username;
    }
    
    public int getId() {
        return id;
    }
    
}
