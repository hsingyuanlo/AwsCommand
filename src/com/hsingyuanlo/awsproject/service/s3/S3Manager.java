package com.hsingyuanlo.awsproject.service.s3;

import com.hsingyuanlo.awsproject.service.AwsAction;
import com.hsingyuanlo.awsproject.service.AwsManager;
import com.hsingyuanlo.awsproject.service.s3.action.S3DownloadAction;

public class S3Manager extends AwsManager {
    
    final static public String ACTION_DOWNLOAD = "download";
    public AwsAction mAction = null;
    
    @Override
    protected void onSetAction(String action) {
        if (ACTION_DOWNLOAD.equals(action)) {
            mAction = new S3DownloadAction();
        } else {
            onShowUsage();
        }
    }

    @Override
    protected void onRunAction(String[] args) {
        mAction.exec(args);
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
