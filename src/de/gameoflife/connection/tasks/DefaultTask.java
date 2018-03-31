package de.gameoflife.connection.tasks;

import javafx.concurrent.Task;

/**
 *
 * @author JScholz
 */
public abstract class DefaultTask<T> extends Task<T> {

    protected boolean error;
    protected String errorMassage;
    //TODO load url from database
    private final String url;

    public DefaultTask ( final String url ) {
        this.url = url;
        this.error = false;
        this.errorMassage = "";
    }

    public String getUrl () {
        return url;
    }
    
    public boolean hasError() {
        return error;
    }
    
    public String getErrorMessage() {
        return errorMassage;
    }

    @Override
    protected abstract T call () throws Exception;

}
