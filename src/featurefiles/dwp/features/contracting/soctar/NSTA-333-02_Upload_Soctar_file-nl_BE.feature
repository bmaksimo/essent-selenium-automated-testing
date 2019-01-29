@DWP
@SOCTAR
Feature: Upload Soctar file to Nova sftp

    @SOCTAR-02
    @NSTA-333-STEP-2
    Scenario: Upload Soctar file to Nova sftp
        #Output parameter "ean_id"
        Given Soctar EAN is "541443378265311406"
        #Output parameter "cust_id"
        And   Soctar customer Id is "100005524"
        #Output parameter "start_end_date"
        And   Soctar start date is "now"
        # Input parameter: "parameter:ean_id"
        # Input parameter:  "parameter:cust_id"
        # Input parameter:  "parameter:start-end-date"
        # Output parameter: "soctar-file-name"
        Then  Soctar file is uploaded to "/home/ESSENT/sa_sftpcrm_smx/data/soctar" remote directory
