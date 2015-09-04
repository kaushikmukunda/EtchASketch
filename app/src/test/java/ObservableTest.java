import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import sketch.km.com.etchasketch.BuildConfig;
import sketch.km.com.etchasketch.EventObservable;
import sketch.km.com.etchasketch.EventObserver;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants= BuildConfig.class, emulateSdk=21)
public class ObservableTest {
    private EventObservable mEventObservable;
    private TestObserver mObserver1;
    private TestObserver mObserver2;

   @Before
    public void setUp() {
       mEventObservable = new EventObservable();
       mObserver1 = new TestObserver();
       mObserver2 = new TestObserver();
   }

    @Test
    public void test_ArrayShouldBeInitialized() {
        Assert.assertEquals(0, mEventObservable.getNumListeners());
    }

    @Test
    public void test_onRegister_shouldAddToList() {
        mEventObservable.registerListener(mObserver1);
        Assert.assertEquals(1, mEventObservable.getNumListeners());
    }

    @Test
    public void test_onDeregister_removeFromList() {
        mEventObservable.registerListener(mObserver1);
        mEventObservable.registerListener(mObserver2);
        mEventObservable.deregisterListener(mObserver1);
        Assert.assertEquals(1, mEventObservable.getNumListeners());
    }

    @Test
    public void test_onEventTrigger_invokesListeners() {
        mEventObservable.registerListener(mObserver1);
        mEventObservable.registerListener(mObserver2);
        mEventObservable.eventTrigger();
        Assert.assertEquals(true, mObserver1.changeCalled);
        Assert.assertEquals(true, mObserver2.changeCalled);
    }


    @Test
    public void test_onEventTrigger_registerNewListener() {
        TrickyObserver mObserver3 = new TrickyObserver();
        mEventObservable.registerListener(mObserver1);
        mEventObservable.registerListener(mObserver2);
        mEventObservable.registerListener(mObserver3);

        mEventObservable.eventTrigger();
    }


    public class TrickyObserver implements EventObserver {
        @Override
        public void onChange() {
           mEventObservable.deregisterListener(this);
        }
    }


    public class TestObserver implements EventObserver {
        public boolean changeCalled;

        @Override
        public void onChange() {
            changeCalled = true;
        }

    }

}
