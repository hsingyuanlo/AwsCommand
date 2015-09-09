package com.hsingyuanlo.awsproject.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * AwsAction is the base class for AWS operations
 * @author hylo
 *
 */

abstract public class AwsAction {
    
    final static public String REGION   = "region";
    
    protected Options mOptions = new Options();
    
    public AwsAction() {
        mOptions.addOption("r", REGION,   true, "(required) AWS region");
    }
    
    /**
     * Execute template method
     * @param args
     */
    public void exec(String[] args) {
        try {
            Map<String, String> map = doParse(args, mOptions);
            boolean result = doRunAction(map);
            
            if (!result) {
                doShowUsage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            doShowUsage();
        }
    }
    
    /**
     * Parse parameters
     * @param args
     * @param options
     * @return
     * @throws Exception
     */
    protected Map<String, String> doParse(String[] args, Options options) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        
        CommandLineParser parser = new DefaultParser();
        CommandLine line = parser.parse( options, args, true );
        
        Iterator<Option> it = line.iterator();
        while (it.hasNext()) {
            Option opt = it.next();
            if (opt.hasArg()) {
                map.put(opt.getLongOpt(), opt.getValue());
                continue;
            }
        }
        return map;
    }
    
    /**
     * Run action from child class
     * @param map
     * @return
     * @throws Exception
     */
    protected boolean doRunAction(Map<String, String> map) throws Exception {
        return onRunAction(map);
    }
    
    /**
     * Show usage
     */
    protected void doShowUsage() {
        System.out.println();
        // onShowUsage
        onShowUsage();
        // Options
        Collection<Option> opts = mOptions.getOptions();
        System.out.println(" Options: ");
        for (Option opt : opts) {
            System.out.println("  -"+opt.getOpt()+", --"+opt.getLongOpt()+"\t"+opt.getDescription());
        }
        System.out.println();
    }
    
    /**
     * Call run-action in child class
     * @param map
     * @return true if success; otherwise false
     * @throws Exception
     */
    abstract protected boolean onRunAction(Map<String, String> map) throws Exception;
    
    /**
     * Call show-usage in child class
     */
    abstract protected void onShowUsage();
}
