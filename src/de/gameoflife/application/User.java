package de.gameoflife.application;

/**
 *
 * @author JScholz
 */
final class User {
    
    private static User instance;
    private final String username;
    private final int id;
    
    private User( String username, int id ) {
    
        this.username = username;
        this.id = id;
    
    }
    
    static void create( String username, int id ) {
    
        instance = new User( username, id );
        
    }
    
    static void removeInstance() {
    
        instance = null;
        
    }
    
    static User getInstance() {
        return instance;     
    }

    String getUsername() {
        return username;
    }
    
    int getId() {
        return id;
    }
    
}
