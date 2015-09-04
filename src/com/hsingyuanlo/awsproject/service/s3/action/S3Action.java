package com.hsingyuanlo.awsproject.service.s3.action;

import com.hsingyuanlo.awsproject.service.AwsAction;

abstract public class S3Action extends AwsAction {
    
    final static public String S3_USAGE = "s3 ";
    
    @Override
    protected void onShowUsage() {
        System.out.println();
        System.out.println("Usage: [Service] [Action] [Options]");
        System.out.println("Service: " + S3_USAGE);
    }
}
