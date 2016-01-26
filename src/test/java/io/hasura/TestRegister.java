package io.hasura.core;

import io.hasura.core.AuthService;
import io.hasura.auth.*;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestRegister {

    @Test
    public void run() throws IOException, HasuraAuthException {

        AuthService authService = new AuthService("http://104.155.219.208");
        RegisterRequest rq = new RegisterRequest();
        rq.setUsername("aladdin");
        rq.setPassword("abracadabra");
        rq.setEmail("aladdin@genie.io");
        rq.setMobile("1010011000");
        RegisterResponse rr = authService.register(rq).execute();
        System.out.println(rr.getHasuraId());

    }
}
