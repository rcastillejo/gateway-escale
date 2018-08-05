package com.sacooliveros.escale.etl.listener;


/**
 * Created by rcastillejo on 02/06/2016.
 */
public interface ThreadCompleteListener {

    void notifyOfThreadComplete(final Runnable runnable);

}
