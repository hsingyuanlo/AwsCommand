package com.hsingyuanlo.awsproject.service.s3;

import com.hsingyuanlo.awsproject.service.AwsAction;
import com.hsingyuanlo.awsproject.service.AwsManager;
import com.hsingyuanlo.awsproject.service.s3.action.S3DownloadAction;

public class S3Manager extends AwsManager {
    
    final static public String ACTION_DOWNLOAD = "download";
    
    @Override
    protected AwsAction onGetAction(String action) {
        if (ACTION_DOWNLOAD.equals(action)) {
            return new S3DownloadAction();
        }
        return null;
    }
    
    @Override
    protected void onShowUsage() {
        System.out.println();
        System.out.println(" s3 [action] [options]");
        System.out.println(" Actions:");
        System.out.println(" 1. download");
        System.out.println();
    }
}
