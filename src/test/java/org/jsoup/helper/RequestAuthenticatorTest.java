package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for RequestAuthenticator.
 * This class contains unit tests to verify the functionality of the RequestAuthenticator interface and its Context class.
 */
public class RequestAuthenticatorTest {

    @Test public void testAuthenticate() throws Exception {
        RequestAuthenticator auth = new RequestAuthenticator() {
            @Override
            public PasswordAuthentication authenticate(Context auth) {
                if (auth.isServer() && "Realm".equals(auth.realm())) {
                    return auth.credentials("user", "pass");
                }
                return null;
            }
        };

        RequestAuthenticator.Context context = new RequestAuthenticator.Context(
            new URL("http://test.com"), Authenticator.RequestorType.SERVER, "Realm");

        PasswordAuthentication credentials = auth.authenticate(context);
        assertNotNull(credentials);
        assertEquals("user", credentials.getUserName());
        assertEquals("pass", new String(credentials.getPassword()));

        RequestAuthenticator.Context wrongContext = new RequestAuthenticator.Context(
            new URL("http://test.com"), Authenticator.RequestorType.PROXY, "WrongRealm");

        PasswordAuthentication wrongCredentials = auth.authenticate(wrongContext);
        assertNull(wrongCredentials);
    }
}