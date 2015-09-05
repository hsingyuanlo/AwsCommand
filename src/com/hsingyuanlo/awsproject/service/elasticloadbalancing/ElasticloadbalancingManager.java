package com.hsingyuanlo.awsproject.service.elasticloadbalancing;

import com.hsingyuanlo.awsproject.service.AwsAction;
import com.hsingyuanlo.awsproject.service.AwsManager;

public class ElasticloadbalancingManager extends AwsManager {
    
    final static public String REGISTER_INSTANCE   = "register-instance";
    final static public String DEREGISTER_INSTANCE = "deregister-instance";
    
    @Override
    protected AwsAction onGetAction(String action) {
        return null;
    }

    @Override
    protected void onShowUsage() {
        System.out.println();
        System.out.println(" elb [action] [options]");
        System.out.println(" Actions:");
        System.out.println(" 1. register-instance");
        System.out.println(" 2. deregister-instance");
        System.out.println();
    }
}
