package com.hsingyuanlo.awsproject.service;

import java.util.Arrays;

abstract public class AwsManager {
    
    /**
     * Parse parameters and run
     * @param args
     */
    public void parseAndRun(String[] args) {
        if (args.length < 1) {
            doShowUsage();
            return;
        }
        
        // Set action
        doSetAction(args[0]);
        
        // Run service action
        String[] modified_args = Arrays.copyOfRange(args, 1, args.length);
        doRunAction(modified_args);
    }
    
    /**
     * Set action
     * @param action
     */
    protected void doSetAction(String action) {
        onSetAction(action);
    }
    
    /**
     * Run action
     * @param args
     */
    protected void doRunAction(String[] args) {
        onRunAction(args);
    }
    
    /**
     * Show action
     */
    protected void doShowUsage() {
        onShowUsage();
    }
    
    abstract protected void onSetAction(String action);
    abstract protected void onRunAction(String[] args);
    abstract protected void onShowUsage();
}
