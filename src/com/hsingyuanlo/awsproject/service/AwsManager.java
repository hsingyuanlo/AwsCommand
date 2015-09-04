package com.hsingyuanlo.awsproject.service;

import java.util.Arrays;

abstract public class AwsManager {
    
    public void parseAndRun(String[] args) {
        if (args.length < 1) {
            showUsage();
            return;
        }
        
        // Set action
        doSetAction(args[0]);
        
        // Run service action
        String[] modified_args = Arrays.copyOfRange(args, 1, args.length);
        doRunAction(modified_args);
    }
    
    abstract protected void doSetAction(String action);
    abstract protected void doRunAction(String[] args);
    abstract protected void showUsage();
}
