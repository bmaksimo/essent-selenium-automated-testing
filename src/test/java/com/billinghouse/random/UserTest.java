package com.billinghouse.random;

import com.essent.testing.util.resource.ResourceUtil;
import com.google.gson.Gson;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.FileReader;
import java.io.Reader;

import static com.billinghouse.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

;
public class UserTest {

    @Test
    public void  testRandomUser() throws Exception {
        String path = ResourceUtil.toPath("/com/billinghouse/random/User.json");
        Reader reader = new FileReader(path);
        Gson gson = new Gson();
        RandomUser user = gson.fromJson(reader, RandomUser.class);
        assertThat("Random User wasn't created", user, notNullValue());
        testRandomDateOfBirth(user);
    }

    private void testRandomDateOfBirth(RandomUser user) {
        DateTime dt = new DateTime(user.getDob().getDate());
        assertThat("Birth date is not null", dt, notNullValue());
    }
}
