package com.essent.testing.config;

public enum ConfigKey {

	// formatter:off
    ENVIRONMENT("environment"),
    SFTPHOST("sftp.host"),
    BILLING_BASE_URL("billing.baseurl"),
    CRM_BASE_URL("crm.baseurl"),
    CRM_USER("crm.user"),
    CRM_PASSWORD("crm.password"),
	CRM_REST_USER("crm.rest.user"),
	CRM_REST_PASSWORD("crm.rest.password"),
    ACTIVITI_REST_URL("activiti.resturl"),
    BPM_API_URL("bpm.api.url"),
    BPM_USER("bpm.user"),
    BPM_PASSWORD("bpm.password"),
    DWP_BASE_URL("dwp.baseurl"),

    DWP_USER_ESSENTADMIN("dwp.user"),
    DWP_USER_SERVICEDESK_B2B("dwp.user.b2b"),
    DWP_USER_SERVICEDESK_B2C("dwp.user.b2c"),
    DWP_USER_PARTNER_B2C_EXT("dwp.user.partner.b2c.external"),
    DWP_USER_SOAPUI_B2B("dwp.user.soapui_b2b"),
    DWP_PASSWORD("dwp.password"),

    DWP_PASSWORD_SERVICEDESK_B2B("dwp.password.b2b"),

    DWP_USER_SALESMARKETING_B2C("dwp.user.b2c.salesmarketing"),
    DWP_PASSWORD_SALESMARKETING_B2C("dwp.password.b2c.salesmarketing"),

    DWP_USER_CONTRACTING_B2C("dwp.user.b2c.contracting"),
    DWP_PASSWORD_CONTRACTING_B2C("dwp.password.b2c.contracting"),

    DWP_USER_BILLING("dwp.user.billing"),
    DWP_PASSWORD_BILLING("dwp.password.billing"),

    DWP_PASSWORD_SERVICEDESK_B2C("dwp.password.b2c"),
    DWP_PASSWORD_PARTNER_B2C_EXT("dwp.password.partner.b2c.external"),
    DWP_PASSWORD_SOAPUI_B2B("dwp.password.soapui_b2b"),
    SERVICEMIX_BASE_URL("servicemix.baseurl"),
    BILLING_URL("billing.url"),
    BILLING_DB_HOST("billing.db.host"),
    BILLING_DB_USER("billing.db.user"),
    BILLING_DB_PASSWORD("billing.db.password"),
    BILLING_DB("billing.db"),
    BILLING_CHANGELOG_MASTER("billing.changelog-master"),
    EDIEL_DB_HOST("ediel.db.host"),
    EDIEL_DB_USER("ediel.db.user"),
    EDIEL_DB_PASSWORD("ediel.db.password"),
    EDIEL_DB("ediel.db"),
    EDIEL_DB_CHANGELOG_MASTER("ediel.changelog-master"),
    PROFILE_DB_HOST("profile.db.host"),
    PROFILE_DB_USER("profile.db.user"),
    PROFILE_DB_PASSWORD("profile.db.password"),
    PROFILE_DB("profile.db"),
	PROFILE_DB_CHANGELOG_MASTER("profile.changelog-master"),
    ENERGY_BALANCE_DB_HOST("energy_balance.db.host"),
    ENERGY_BALANCE_DB_USER("energy_balance.db.user"),
    ENERGY_BALANCE_DB_PASSWORD("energy_balance.db.password"),
    ENERGY_BALANCE_DB_DB("energy_balance.db"),
	ENERGY_BALANCE_DB_CHANGELOG_MASTER("energy_balance.changelog-master"),
    SUITE_DB_HOST("suite.db.host"),
    SUITE_DB_USER("suite.db.user"),
    SUITE_DB_PASSWORD("suite.db.password"),
    SUITE_DB("suite.db"),
    SSH_SUITE_HOSTNAME("ssh.suite.hostname"),
    SSH_SUITE_LOCAL_PORT("ssh.suite.local.port"),
    SSH_SUITE_REMOTE_PORT("ssh.suite.remote.port"),
    SSH_PASSPHRASE("ssh.passphrase"),
    SSH_KEYPATH("ssh.keypath"),
    SSH_USER("ssh.user"),
    SSH_BILLING_HOSTNAME("ssh.billing.hostname"),
    SSH_BILLING_REMOTE_PORT("ssh.billing.remote.port"),

    // related to resetting odoo database
    SSH_ODOO_HOSTNAME("ssh.odoo.hostname"),
    SSH_ODOO_PORT("ssh.odoo.port"),
    SSH_ODOO_RESETDB_SCRIPT("ssh.odoo.resetdb.script"),
    SSH_ODOO_VERBOSE("ssh.odoo.verbose"),
    SSH_ODOO_TIMEOUT("ssh.odoo.timeout"),

    // Access to odoo database.
    ODOO_DB("odoo.db"),
    ODOO_DB_HOST("odoo.db.host"),
    ODOO_DB_PORT("odoo.db.port"),
    ODOO_DB_USER("odoo.db.user"),
    ODOO_DB_PASSWORD("odoo.db.password"),

    ODOO_HOST("odoo.host"),
    ODOO_BASE_URL("odoo.baseurl"),
    ODOO_PORT("odoo.port"),
    ODOO_DATABASE("odoo.database"),
    ODOO_USER("odoo.user"),
    ODOO_COMPANYNAME("odoo.companyName"),
    ODOO_PASSWORD("odoo.password"),
    ODOO_MASTERPASSWORD("odoo.masterPassword"),

    WEBDRIVER_FIREFOX_DRIVER("webdriver.firefox.driver"),
    WEBDRIVER_GECKO_DRIVER("webdriver.gecko.driver"),
    WEBDRIVER_CHROME_DRIVER("webdriver.chrome.driver"),
    WEBDRIVER_FIREFOX_PROFILE("firefox.profile.path"),
	WEBDRIVER_CHROME_USER_DATA_PATH("chrome.user.data.path"),
    WEBDRIVER_CHROME_HEADLESS("webdriver.chrome.headless"),
    WEBDRIVER_CHROME_HEADLESS_WINDOW_SIZE("webdriver.chrome.headless.window.size"),
    PLSQL_LOCATION("psql.location"),
	SSH_BPM_HOSTNAME("ssh.bpm.hostname"),
    TESTING_BASE_URL("testing.base.url"),
    DUNNING_DUMP_DIR("dunning.dump.dir"),
    TUNNEL_BILLING_DB("tunnel.billing.db"),
    TUNNEL_SCP("tunnel.scp"),
    TUNNEL_SUITE("tunnel.suite"),
    TUNNEL_BPM("tunnel.bpm"),
    ENERGYCOMM_URL("energycomm.url"),
    MARKET_MOCK_URL("marketmock.url");
	// formatter:on


    private String resourceKey;

    ConfigKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getResourceKey() {
        return resourceKey;
    }
}
