# README #

### What is this repository for? ###

* The repository is for clean (template) DWP / SuiteCRM soctar file

### What do we know about Soctar file? ###
* Every quarter Essent.be will receive a file from the Belgium civil services with a list of active customers who have the right to pay a lower social tariff instead of the contractual essent.be prices.
*  1st line is header
*  2nd line is columns
*  Soctar file has fixed length records
*  GLNID -> Always 5499764826400150550169
*  CUSTID-> Empty
*  EANID -> EAN of the customer created in step 1
*  NAME -> EMPTY
*  SSTARTDATENDDATE -> Same format as in example
   *  Example:
       When start date of contract created in step 1 is 01/01/2019 we need to note 12019010120191231
       When start date of contract created in step 1 is 01/02/2019, we need to note 12019020120191231
       FYI: Last date is always the end of the year mentioned in the start date.
       
*  A filename (irrelevant but must be unique)
*  File needs to be transferred by sftp to the environment: /home/ESSENT/sa_sftpcrm_smx/data/soctar/
