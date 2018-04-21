package com.essent.testing.dwp.menu.model;

public enum UpperMenuItems {
    FAILED_JOBS("Failed jobs", "failed-jobs-link"),
    JOB_LOG("Job Log", "job-log-link"),
    DGO_DASHBOARD("DGO/Supplier/BRP", "dgo-dashboard-link"),
    FAQS("FAQs", "faqs-link"),
    ACTIVITIPROCESSINSTANCES("ActivitiProcessInstances", "activitiprocessinstances-link"),
    GRAYLOG_ERRORS("graylog_errors", "graylog-errors-link"),
    DEALER_AGENT_OID("dealer_agent_oid", "dealer-agent-oid-link"),
    DOCUMENTS("Documents", "documents-link"),
    USERS("Users", "users-link"),
    ACCOUNTS_LIST("Accounts", "accounts-list-link"),
    CONTRACT_LIST("Contracts", "contract-list-link"),
    CASES("Cases", "cases-link"),
    MOVE_SEND_ILC("MOVE: Send ILC", "move-send-ilc-link"),
    MARKETTRANSACTIONS_DASHBOARD("Market Transactions", "market-transactions-dashboard-link"),
    MS_SEND_ILC("MS: Send ILC", "ms-send-ilc-link"),
    MARKETTRANSACTIONTASKS_DASHBOARD("Tasks market transaction", "market-transactiontasks-dashboard-link"),
    QUOTES_LIST("Quotes", "quotes-list-link"),
    CHEAPEST_PRODUCTS("Cheapest products", "cheapest-products-link"),
    SALES_MARKETING_QUOTATION_TASKS("S&M Tasks", "sales-marketing-quotation-tasks-link"),
    LEAD_LIST("Leads", "lead-list-link"),
    MY_ACCOUNTS_LIST("My Accounts", "my-accounts-list-link"),
    PRICING_TOOL_DASHBOARD("Pricing tool", "pricing-tool-dashboard-link"),
    CAMPAIGN_LIST("Campaigns", "campaign-list-link"),
    EUROCCOR_QUOTES("Euroccor Quotes", "euroccor-quotes-link"),
    SERVICE_TASKS("Service_Tasks", "service-tasks-link"),
    WRITTEN("Written", "written-link"),
    OUTBOUND("Outbound", "outbound-link"),
    MISSING_CHEAPEST_PRODUCTS("Missing cheapest products", "missing-cheapest-products-link"),
    SEGMENTATION("Segmentation", "segmentation-link"),
    REJECTIONS_TASKS_INTERNAL("Rejections - internal",
        "rejections-tasks-internal-link"),
    DB_DEBIT_TASKS_FULL_VIEW("debit request tasks full view",
        "db-debit-tasks-full-view-link"),
    DB_GUARANTEE_TASKS_FULL_VIEW(
        "Guarantee Tasks full view",
        "db-guarantee-tasks-full-view-link"),
    DB_DUNNING_CALLS_TASK_LIST("Dunning call tasks",
        "db-dunning-calls-task-list-link"),
    DB_PAYMENTPLAN_TASKS("Payment Plan Tasks",
        "db-paymentplan-tasks-link"),
    TASKS_CONTRACTING_MOVE("Move",
        "tasks-contracting-move-link"),
    SALES_COMPLAINTS("Sales complaints",
        "sales-complaints-link"),
    TASKS_CUSTOM_DUTIES("Customs & duties",
        "tasks-custom-duties-link"),
    INTERNAL_COMPLAINTS(
        "Internal complaints",
        "internal-complaints-link"),
    TASKS_DEDUPLICATION(
        "Deduplication",
        "tasks-deduplication-link"),
    GRIDFEE_TASKS(
        "Gridfee tasks",
        "gridfee-tasks-link"),
    RECTIFICATION_TASKS(
        "Rectification tasks",
        "rectification-tasks-link"),
    ADVANCE_CASES(
        "Advance cases/tasks",
        "advance-cases-link"),
    SOCTAR_TASKS("Soctar",
        "soctar-tasks-link"),
    OFFICIAL_EMAILS(
        "Official E-mail",
        "official-emails-link"),
    EXCEPTIONAL_INVOICE_CASES(
        "Exceptional invoice cases/tasks",
        "exceptional-invoice-cases-link"),
    SETTLEMENT_CASES(
        "Settlement cases/tasks",
        "settlement-cases-link"),
    BUDGET_METER_TASKS(
        "Budget Meter  Tasks",
        "budget-meter-tasks-link"),
    OFFICIAL_LETTERS(
        "Official Letters",
        "official-letters-link"),
    TASKS_EPLUS(
        "Tasks E+",
        "tasks-eplus-link"),
    EXTRA_SMILE(
        "Extra Smile",
        "extra-smile-link"),
    NON_INVOICED_CONNECTIONS("NonInvoicedConnections", "non-invoiced-connections-link"),
    SELLINGPRODUCT_DETAILS("SellingProduct_details", "sellingproduct-details-link"),
    CONTRACTLINES_EPLUS("Contractlines_eplus", "contractlines-eplus-link"),
    CONFIGLES_LIESBET("Configles_Liesbet", "configles-liesbet-link"),
    KEY_CONFIGLES_EBOECKX("configles_eboeckx",  "key-configles-eboeckx-link"),
    CONDITIONAL_MESSAGES_TEST("Conditional messages test", "conditional-messages-test-link"),
    CONFIGLES_KATYCONFIG("Configles Katy Config", "configles-katyconfig-link");


    private String label;
    private String link;

    private UpperMenuItems(String label, String dashboardLink) {
        this.label = label;
        this.link = dashboardLink;
    }

    public String getLabel() {
        return label;
    }

    public String getLink() {
        return link;
    }
}
