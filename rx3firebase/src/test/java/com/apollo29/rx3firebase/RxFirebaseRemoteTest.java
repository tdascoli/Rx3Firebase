package com.apollo29.rx3firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.rxjava3.observers.TestObserver;

import static com.apollo29.rx3firebase.RxTestUtil.ANY_TIME;
import static com.apollo29.rx3firebase.RxTestUtil.EXCEPTION;
import static com.apollo29.rx3firebase.RxTestUtil.setupTask;
import static com.apollo29.rx3firebase.RxTestUtil.testOnCompleteListener;
import static com.apollo29.rx3firebase.RxTestUtil.testOnFailureListener;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RxFirebaseRemoteTest {

    @Mock
    private Task<Void> voidTask;

    @Mock
    private FirebaseRemoteConfig firebaseConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        setupTask(voidTask);

        when(firebaseConfig.fetch(ANY_TIME)).thenReturn(voidTask);
    }

    @Test
    public void fetchRemoteConfig() {
        TestObserver fetchTestObserver = RxFirebaseRemote
            .fetch(firebaseConfig, ANY_TIME)
            .test();

        testOnCompleteListener.getValue().onComplete(voidTask);

        verify(firebaseConfig).fetch(eq(ANY_TIME));

        fetchTestObserver.assertNoErrors()
            //.assertValueSet(Collections.singletonList(voidTask))
            .assertComplete();
            //.dispose();
    }

    @Test
    public void fetchRemoteFailure() {
        TestObserver fetchTestObserver = RxFirebaseRemote
            .fetch(firebaseConfig, ANY_TIME)
            .test();

        testOnFailureListener.getValue().onFailure(EXCEPTION);

        verify(firebaseConfig).fetch(eq(ANY_TIME));

        fetchTestObserver.assertError(EXCEPTION)
            .assertNotComplete();
            //.dispose();
    }
}
