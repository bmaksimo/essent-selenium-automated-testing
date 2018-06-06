package com.essent.roles;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;

public enum UserRoles {
    ESSENT_ADMIN(ConfigProvider.getProperty(ConfigKey.DWP_USER_ESSENTADMIN), ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD)),
    SERVICE_DESK_B2B(ConfigProvider.getProperty(ConfigKey.DWP_USER_SERVICEDESK_B2B), ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_SERVICEDESK_B2B)),
    SERVICE_DESK_B2C(ConfigProvider.getProperty(ConfigKey.DWP_USER_SERVICEDESK_B2C), ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_SERVICEDESK_B2C)),
    PARTNER_B2C_EXT(ConfigProvider.getProperty(ConfigKey.DWP_USER_PARTNER_B2C_EXT), ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_PARTNER_B2C_EXT));
    private String username;
    private String password;

    UserRoles(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
