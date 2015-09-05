package com.hsingyuanlo.awsproject.service;

import java.util.Arrays;

abstract public class AwsManager {
    
    protected AwsAction mAction = null;
    
    /**
     * Parse parameters and run
     * @param args
     */
    public void parseAndRun(String[] args) {
        // check number of parameters
        if (args.length < 1) {
            doShowUsage();
            return;
        }
        
        // Set action
        mAction = doGetAction(args[0]);
        if (mAction == null) {
            doShowUsage();
            return;
        }
        
        // Run service action
        String[] modified_args = Arrays.copyOfRange(args, 1, args.length);
        doRunAction(modified_args);
    }
    
    /**
     * Get action
     * @param action
     */
    protected AwsAction doGetAction(String action) {
        return onGetAction(action);
    }
    
    /**
     * Run action
     * @param args
     */
    protected void doRunAction(String[] args) {
        mAction.exec(args);
    }
    
    /**
     * Show service usage
     */
    protected void doShowUsage() {
        onShowUsage();
    }
    
    abstract protected AwsAction onGetAction(String action);
    abstract protected void onShowUsage();
}
