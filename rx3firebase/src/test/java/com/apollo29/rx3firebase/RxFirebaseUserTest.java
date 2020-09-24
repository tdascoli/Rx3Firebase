package com.apollo29.rx3firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.rxjava3.observers.TestObserver;

import static com.apollo29.rx3firebase.RxTestUtil.ANY_EMAIL;
import static com.apollo29.rx3firebase.RxTestUtil.ANY_PASSWORD;
import static com.apollo29.rx3firebase.RxTestUtil.EXCEPTION;
import static com.apollo29.rx3firebase.RxTestUtil.setupTask;
import static com.apollo29.rx3firebase.RxTestUtil.testOnCompleteListener;
import static com.apollo29.rx3firebase.RxTestUtil.testOnFailureListener;
import static com.apollo29.rx3firebase.RxTestUtil.testOnSuccessListener;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RxFirebaseUserTest {

    @Mock
    FirebaseUser firebaseUser;

    @Mock
    GetTokenResult getTokenResult;

    @Mock
    Task<Void> voidTask;

    @Mock
    Task<GetTokenResult> getTokenResultTask;

    @Mock
    UserProfileChangeRequest userProfileChangeRequest;

    @Mock
    AuthCredential authCredential;

    @Mock
    Task<AuthCredential> authCredentialTask;

    @Mock
    AuthResult authResult;

    @Mock
    Task<AuthResult> authResultTask;

    private boolean ANY_FORCE_REFRESH_VALUE = true;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        setupTask(getTokenResultTask);
        setupTask(voidTask);
        setupTask(authResultTask);
        setupTask(authCredentialTask);

        when(firebaseUser.getIdToken(ANY_FORCE_REFRESH_VALUE)).thenReturn(getTokenResultTask);
        when(firebaseUser.updateEmail(ANY_EMAIL)).thenReturn(voidTask);
        when(firebaseUser.updatePassword(ANY_PASSWORD)).thenReturn(voidTask);
        when(firebaseUser.updateProfile(userProfileChangeRequest)).thenReturn(voidTask);
        when(firebaseUser.delete()).thenReturn(voidTask);
        when(firebaseUser.reauthenticate(authCredential)).thenReturn(voidTask);
        when(firebaseUser.reauthenticateAndRetrieveData(authCredential)).thenReturn(authResultTask);
        when(firebaseUser.linkWithCredential(authCredential)).thenReturn(authResultTask);
    }

    @Test
    public void getToken() {
        TestObserver<GetTokenResult> userTestObserver = RxFirebaseUser.getIdToken(firebaseUser, ANY_FORCE_REFRESH_VALUE).test();

        testOnSuccessListener.getValue().onSuccess(getTokenResult);
        testOnCompleteListener.getValue().onComplete(getTokenResultTask);

        verify(firebaseUser).getIdToken(ANY_FORCE_REFRESH_VALUE);

        userTestObserver.assertComplete()
            .assertNoErrors()
            .assertValueCount(1)
            .dispose();
    }

    @Test
    public void getTokenError() {
        TestObserver<GetTokenResult> userTestObserver = RxFirebaseUser.getIdToken(firebaseUser, ANY_FORCE_REFRESH_VALUE).test();
        testOnFailureListener.getValue().onFailure(EXCEPTION);
        verify(firebaseUser).getIdToken(ANY_FORCE_REFRESH_VALUE);

        userTestObserver.assertError(EXCEPTION)
            .dispose();
    }

    @Test
    public void updateEmail() {
        TestObserver<Void> userTestObserver = RxFirebaseUser.updateEmail(firebaseUser, ANY_EMAIL).test();

        testOnCompleteListener.getValue().onComplete(voidTask);
        testOnSuccessListener.getValue().onSuccess(voidTask);

        verify(firebaseUser).updateEmail(ANY_EMAIL);

        userTestObserver.assertComplete()
            .dispose();

    }

    @Test
    public void updateEmailError() {
        TestObserver<Void> userTestObserver = RxFirebaseUser.updateEmail(firebaseUser, ANY_EMAIL).test();

        testOnFailureListener.getValue().onFailure(EXCEPTION);

        verify(firebaseUser).updateEmail(ANY_EMAIL);

        userTestObserver.assertError(EXCEPTION)
            .dispose();

    }

    @Test
    public void updatePassword() {
        TestObserver<Void> userTestObserver = RxFirebaseUser.updatePassword(firebaseUser, ANY_PASSWORD).test();
        testOnCompleteListener.getValue().onComplete(voidTask);
        testOnSuccessListener.getValue().onSuccess(voidTask);

        verify(firebaseUser).updatePassword(ANY_PASSWORD);

        userTestObserver.assertComplete()
            .dispose();
    }

    @Test
    public void updatePasswordError() {
        TestObserver<Void> userTestObserver = RxFirebaseUser.updatePassword(firebaseUser, ANY_PASSWORD).test();

        testOnFailureListener.getValue().onFailure(EXCEPTION);

        verify(firebaseUser).updatePassword(ANY_PASSWORD);

        userTestObserver.assertError(EXCEPTION)
            .dispose();

    }

    @Test
    public void updateProfile() {
        TestObserver<Void> userTestObserver = RxFirebaseUser.updateProfile(firebaseUser, userProfileChangeRequest).test();

        testOnCompleteListener.getValue().onComplete(voidTask);
        testOnSuccessListener.getValue().onSuccess(voidTask);

        verify(firebaseUser).updateProfile(userProfileChangeRequest);

        userTestObserver.assertComplete()
            .dispose();

    }

    @Test
    public void updateProfileError() {
        TestObserver<Void> userTestObserver = RxFirebaseUser.updateProfile(firebaseUser, userProfileChangeRequest).test();

        testOnFailureListener.getValue().onFailure(EXCEPTION);

        verify(firebaseUser).updateProfile(userProfileChangeRequest);

        userTestObserver.assertError(EXCEPTION)
            .dispose();

    }

    @Test
    public void delete() {
        TestObserver<Void> userTestObserver = RxFirebaseUser.delete(firebaseUser).test();

        testOnCompleteListener.getValue().onComplete(voidTask);
        testOnSuccessListener.getValue().onSuccess(voidTask);

        verify(firebaseUser).delete();

        userTestObserver.assertComplete()
            .dispose();
    }

    @Test
    public void deleteError() {
        TestObserver<Void> userTestObserver = RxFirebaseUser.delete(firebaseUser).test();

        testOnFailureListener.getValue().onFailure(EXCEPTION);
        verify(firebaseUser).delete();

        userTestObserver.assertError(EXCEPTION)
            .dispose();
    }

    @Test
    public void reAuthenticate() {
        TestObserver<Void> userTestObserver = RxFirebaseUser.reAuthenticate(firebaseUser, authCredential).test();

        testOnCompleteListener.getValue().onComplete(voidTask);
        testOnSuccessListener.getValue().onSuccess(voidTask);

        verify(firebaseUser).reauthenticate(authCredential);

        userTestObserver.assertComplete()
            .dispose();

    }

    @Test
    public void reauthenticateAndRetrieveData() {
        TestObserver<AuthResult> userTestObserver = RxFirebaseUser.reauthenticateAndRetrieveData(firebaseUser, authCredential).test();

        testOnSuccessListener.getValue().onSuccess(authResult);
        testOnCompleteListener.getValue().onComplete(authResultTask);

        verify(firebaseUser).reauthenticateAndRetrieveData(authCredential);

        userTestObserver.assertNoErrors()
            .assertValueCount(1)
            .assertComplete()
            //.assertValueSet(Collections.singletonList(authResult))
            .dispose();

    }

    @Test
    public void reAuthenticateError() {
        TestObserver<Void> userTestObserver = RxFirebaseUser.reAuthenticate(firebaseUser, authCredential).test();

        testOnFailureListener.getValue().onFailure(EXCEPTION);

        verify(firebaseUser).reauthenticate(authCredential);

        userTestObserver.assertError(EXCEPTION)
            .dispose();
    }

    @Test
    public void linkWithCredentials() {
        TestObserver<AuthResult> userTestObserver = RxFirebaseUser.linkWithCredential(firebaseUser, authCredential).test();

        testOnSuccessListener.getValue().onSuccess(authResult);
        testOnCompleteListener.getValue().onComplete(authResultTask);

        verify(firebaseUser).linkWithCredential(authCredential);


        userTestObserver.assertNoErrors()
            .assertValueCount(1)
            .assertComplete()
            //.assertValueSet(Collections.singletonList(authResult))
            .dispose();
    }

    @Test
    public void linkWithCredentialsError() {
        TestObserver<AuthResult> userTestObserver = RxFirebaseUser.linkWithCredential(firebaseUser, authCredential).test();

        testOnFailureListener.getValue().onFailure(EXCEPTION);

        verify(firebaseUser).linkWithCredential(authCredential);

        userTestObserver.assertError(EXCEPTION)
            .dispose();


    }
}