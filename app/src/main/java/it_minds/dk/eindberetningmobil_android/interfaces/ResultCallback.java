package it_minds.dk.eindberetningmobil_android.interfaces;

/**
 * Created by kasper on 28-06-2015.
 * describes an operations result, either it went good or bad (server for example).
 */
public interface ResultCallback<T> {
    void onSuccess(T result);
    void onError(Exception error);
}
