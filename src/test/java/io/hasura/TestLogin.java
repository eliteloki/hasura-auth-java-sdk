package io.hasura.core;

import io.hasura.core.AuthService;
import io.hasura.auth.*;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestLogin {

    @Test
    public void run() throws IOException, HasuraException {

        AuthService authService = new AuthService("http://104.155.219.208");
        LoginResponse r = authService.login("aladdin", "abracadabra", null).execute();
        System.out.println(r.getHasuraId());
        System.out.println(r.getHasuraRole());

    }
}
