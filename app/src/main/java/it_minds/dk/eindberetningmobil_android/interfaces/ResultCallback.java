package it_minds.dk.eindberetningmobil_android.interfaces;

/**
 * Created by kasper on 28-06-2015.
 */
public interface ResultCallback<T> {
    public void onSuccess(T result);


    public void onError(Exception error);

}
