package com.essent.roles;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum UserRoles {
    ESSENT_ADMIN(ConfigProvider.getProperty(ConfigKey.DWP_USER_ESSENTADMIN), ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD)),
    SERVICE_DESK_B2B(ConfigProvider.getProperty(ConfigKey.DWP_USER_SERVICEDESK_B2B), ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_SERVICEDESK_B2B)),
    B2B(ConfigProvider.getProperty(ConfigKey.DWP_USER_B), ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_B)),
    B2B_BUSINESS(ConfigProvider.getProperty(ConfigKey.DWP_BUSINESS_USER), ConfigProvider.getProperty(ConfigKey.DWP_BUSINESS_PASSWORD)),
    SALESMARKETING_B2C(ConfigProvider.getProperty(ConfigKey.DWP_USER_SALESMARKETING_B2C), ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_SALESMARKETING_B2C)),
    CONTRACTING_B2C(ConfigProvider.getProperty(ConfigKey.DWP_USER_CONTRACTING_B2C), ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_CONTRACTING_B2C)),
    BILLING(ConfigProvider.getProperty(ConfigKey.DWP_USER_BILLING), ConfigProvider.getProperty(ConfigKey.DWP_PASSWORD_BILLING)),
    ODOO(ConfigProvider.getProperty(ConfigKey.ODOO_USER), ConfigProvider.getProperty(ConfigKey.ODOO_PASSWORD));

    private String username;
    private String password;

    UserRoles(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private static final Map<String, UserRoles> lookup = new HashMap<>();
    static {
        for(UserRoles role: EnumSet.allOf(UserRoles.class)) {
            lookup.put(role.username, role);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static UserRoles get(final String userName) {
        if(!lookup.containsKey(userName)) {
            throw new IllegalArgumentException(String.format("DWP RandomUser name  '%s' undefined", userName));
        }
        return lookup.get(userName);
    }
}
