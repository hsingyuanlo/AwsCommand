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

abstract public class AwsAction {
    
    public void exec(String[] args) {
        try {
            Options options = getOptions();
            Map<String, String> map = doParse(args, options);
            boolean result = doRunAction(map);
            
            if (!result) {
                doShowUsage();
            }
        } catch (Exception e) {
            doShowUsage();
        }
    }
    
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
    
    protected boolean doRunAction(Map<String, String> map) throws Exception {
        return onRunAction(map);
    }
    
    protected void doShowUsage() {
        System.out.println();
        // onShowUsage
        onShowUsage();
        // Options
        Collection<Option> opts = getOptions().getOptions();
        System.out.println("Options: ");
        for (Option opt : opts) {
            System.out.println(" -"+opt.getOpt()+", --"+opt.getLongOpt()+"\t"+opt.getDescription());
        }
        System.out.println();
    }
    
    abstract protected Options getOptions();
    abstract protected boolean onRunAction(Map<String, String> map) throws Exception;
    abstract protected void    onShowUsage();
}
