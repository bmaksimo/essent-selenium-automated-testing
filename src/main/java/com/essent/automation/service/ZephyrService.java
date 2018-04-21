package com.essent.automation.service;

import com.essent.automation.bean.zephyr.Execution;
import com.essent.automation.bean.zephyr.StepResult;
import com.essent.automation.bean.zephyr.StepResults;
import com.essent.automation.bean.zephyr.Test;
import com.essent.automation.config.ConfigProperties;
import com.essent.automation.config.ConfigProvider;
import com.essent.automation.connector.HttpConnector;
import cucumber.runtime.CucumberException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class ZephyrService {

    private static final ZephyrService instance = new ZephyrService();
    private ServiceLoader<ZephyrServiceFacade> loader;

    private final static String ZEPHYR_CLOUD_HOST      = ConfigProvider.get().getProperty(ConfigProperties.ZAPI_HOST);
    private final static String EMADEV_ATLASSIAN_HOST  =  ConfigProvider.get().getProperty(ConfigProperties.JIRA_HOST);


    private ZephyrService() {
        HttpConnector.getInstance().init(ConfigProvider.get().getProperty(ConfigProperties.PROTOCOL),
                ConfigProvider.get().getProperty(ConfigProperties.ZAPI_HOST),
            null, new HashMap<>());
        HttpConnector.getInstance().withJWTAuthentication();
        loader = ServiceLoader.load(ZephyrServiceFacade.class);
    }
    public static final ZephyrService get() {
        return instance;
    }

    public Test.Step createTestStep(String stepName, String stepData, String stepResult)  {
        HttpConnector.getInstance().withHost(ZEPHYR_CLOUD_HOST).withJWTAuthentication();
        Iterator<ZephyrServiceFacade> iterator = loader.iterator();
        ZephyrServiceFacade serviceFacade = getZephyrServiceFacade(iterator);
        String PROJECT_ID =
            ConfigProvider.get().getProperty(ConfigProperties.NOVA_PROJECT_ID);
        String ISSUE_ID = ConfigProvider.get().getProperty(ConfigProperties.NOVA_ISSUE_ID);
        try {
            return serviceFacade.createTestStep(PROJECT_ID, ISSUE_ID, stepName, stepData, stepResult);
        } catch (IOException e) {
           throw new CucumberException("CreateTestStep request to Zephyr Cloud API failed.", e);
        }
    }

    public Execution createTestExecution(long status) {
        HttpConnector.getInstance().withHost(ZEPHYR_CLOUD_HOST).withJWTAuthentication();
        Iterator<ZephyrServiceFacade> iterator = loader.iterator();
        ZephyrServiceFacade serviceFacade = getZephyrServiceFacade(iterator);
        Long projectId =
            Long.parseLong(ConfigProvider.get().getProperty(ConfigProperties.NOVA_PROJECT_ID));
        Long issueId = Long.parseLong(ConfigProvider.get().getProperty(ConfigProperties.NOVA_ISSUE_ID));
        String cycleId =  ConfigProvider.get().getProperty(ConfigProperties.NOVA_CYCLE_ID);
        try {
            return serviceFacade.createTestExecution(projectId, issueId, cycleId, -1l, status);
        } catch (IOException e) {
            throw new CucumberException("CreateTestExecution request to Zephyr Cloud API failed.", e);
        }
    }

    public boolean createStepResults(Execution execution) {
        HttpConnector.getInstance().withHost(EMADEV_ATLASSIAN_HOST).withBasicAuthentication();
        Iterator<ZephyrServiceFacade> iterator = loader.iterator();
        ZephyrServiceFacade serviceFacade = getZephyrServiceFacade(iterator);
        String techUserid = ConfigProvider.get().getProperty(ConfigProperties.TECHNICAL_USERID);
        String techPass = ConfigProvider.get().getProperty(ConfigProperties.TECHNICAL_PASSWD);
        serviceFacade.login(techUserid, techPass);
        return serviceFacade.createStepResults(execution.getProjectId(), execution.getId(), execution.getIssueId());
    }

    public List<StepResult> getStepResults(Execution execution) {
        HttpConnector.getInstance().withHost(ZEPHYR_CLOUD_HOST).withJWTAuthentication();
        Iterator<ZephyrServiceFacade> iterator = loader.iterator();
        ZephyrServiceFacade serviceFacade = getZephyrServiceFacade(iterator);
        StepResults stepResults = serviceFacade.getStepResults(execution.getId(), execution.getIssueId());
        return stepResults.getStepResults();
    }

    public void updateStepResult(StepResult stepResult) {
        HttpConnector.getInstance().withHost(ZEPHYR_CLOUD_HOST).withJWTAuthentication();
        Iterator<ZephyrServiceFacade> iterator = loader.iterator();
        ZephyrServiceFacade serviceFacade = getZephyrServiceFacade(iterator);
        serviceFacade.updateStepResult(stepResult.getId(), stepResult.getExecutionId(), stepResult.getStepId(), stepResult.getIssueId(),
            stepResult.getStatus().getId(), stepResult.getComment());
    }

    public void updateExecution(Execution execution) {
        HttpConnector.getInstance().withHost(ZEPHYR_CLOUD_HOST).withJWTAuthentication();
        Iterator<ZephyrServiceFacade> iterator = loader.iterator();
        ZephyrServiceFacade serviceFacade = getZephyrServiceFacade(iterator);
        serviceFacade.updateExecution(execution.getId(), execution.getProjectId(), execution.getCycleId(), execution.getIssueId(),
            execution.getStatus().getId(), execution.getComment());
    }


    private ZephyrServiceFacade getZephyrServiceFacade(Iterator<ZephyrServiceFacade> iterator) {
        ZephyrServiceFacade serviceFacade = null;
        while(iterator.hasNext()) {
            serviceFacade = iterator.next();
        }
        return serviceFacade;
    }

}
