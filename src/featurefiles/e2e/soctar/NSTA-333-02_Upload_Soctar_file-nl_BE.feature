@DWP
@E2E
@SOCTAR
Feature: Upload Soctar file to Nova sftp

    @SOCTAR-02
    @NSTA-333-STEP-2
    Scenario: Upload Soctar file to Nova sftp
        #Given Soctar EAN is "parameter:EAN-code"

        Given Soctar EAN is "541443378265311407"
        And   Soctar customer Id is "parameter:Klantnummer & Naam"
        And   Soctar start date is "now"
        Then   Soctar file is uploaded to "/home/ESSENT/sa_sftpcrm_smx/data/soctar" remote directory
