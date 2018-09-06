package com.essent.testing.context.beans;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SystemPropsIncludingPropertiesFactoryBeanTest {

    SystemPropsIncludingPropertiesFactoryBean factoryBean = new SystemPropsIncludingPropertiesFactoryBean();

    @Before
    public void beforeEachTest() {
        factoryBean.setLocation(new ClassPathResource("SystemPropsIncludingPropertiesFactoryBeanTest.properties"));
    }

    @Test
    public void testPickingUpFromFile() throws IOException {
        factoryBean.afterPropertiesSet();

        String runningLocationFromProps = factoryBean.getObject().getProperty("running.location");
        assertEquals("remote", runningLocationFromProps);

        String sshUserFromProps = factoryBean.getObject().getProperty("ssh.user");
        assertEquals("userFromFile", sshUserFromProps);
    }

    @Test
    public void testPickingUpSystemProps_StartingWithSSH() throws IOException {
        String sshUser = "someSSHUser";
        System.setProperty("ssh.user", sshUser);
        factoryBean.afterPropertiesSet();

        String sshUserFromProps = factoryBean.getObject().getProperty("ssh.user");
        assertEquals(sshUser, sshUserFromProps);
    }

    @Test
    public void testNotPickingUpSystemProps_NotStartingWithSSH() throws IOException {
        System.setProperty("someKey", "someValue");
        factoryBean.afterPropertiesSet();

        assertNull(factoryBean.getObject().getProperty("someKey"));
    }

}
