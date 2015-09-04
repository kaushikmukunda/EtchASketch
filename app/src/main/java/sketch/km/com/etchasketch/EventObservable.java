package sketch.km.com.etchasketch;

import java.util.ArrayList;

public class EventObservable implements Observable{
    // TODO: Explore moving tests to same package in order to use package scope
    private ArrayList<EventObserver> mListeners;

    public EventObservable() {
        this.mListeners = new ArrayList<EventObserver>();
    }

    public void registerListener(EventObserver listener) {
        mListeners.add(listener);
    }

    public void deregisterListener(EventObserver listener) {
       for (EventObserver observer : mListeners) {
           if (observer == listener) {
               mListeners.remove(observer);
               break;
           }
       }
    }

    @Override
    public void eventTrigger() {
        for (EventObserver observer: mListeners){
            observer.onChange();
        }
    }

    public int getNumListeners() {
        return mListeners.size();
    }
}
