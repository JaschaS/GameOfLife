package de.gameoflife.connection.tasks;

import javafx.concurrent.Task;

/**
 *
 * @author JScholz
 */
public abstract class DefaultTask<T> extends Task<T> {

    //TODO load url from database
    private final String url;

    public DefaultTask ( final String url ) {
        this.url = url;
    }

    public String getUrl () {
        return url;
    }

    @Override
    protected abstract T call () throws Exception;

}
