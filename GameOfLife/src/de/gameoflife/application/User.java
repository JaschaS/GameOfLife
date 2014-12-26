package de.gameoflife.application;

/**
 *
 * @author JScholz
 */
public final class User {
    
    private static User instance;
    private final String username;
    private final String password;
    
    private User( String username, String password ) {
    
        this.username = username;
        this.password = password;
    
    }
    
    public static void create( String username, String password ) {
    
        instance = new User( username, password );
        
    }
    
    public static User getInstance() {
        return instance;     
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
}
