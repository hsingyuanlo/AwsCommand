package com.hsingyuanlo.awsproject.service.s3.action;

import java.io.FileOutputStream;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.hsingyuanlo.awsproject.auth.AuthManager;
import com.hsingyuanlo.awsproject.service.AwsAction;

public class S3DownloadAction extends AwsAction {
    
    final static public String REGION   = "region";
    final static public String BUCKET   = "bucket";
    final static public String KEY      = "key";
    final static public String FILEPATH = "filepath";
    
    public S3DownloadAction() {
        mOptions.addOption("r", REGION,   true, "(required) AWS region");
        mOptions.addOption("b", BUCKET,   true, "(required) AWS S3 bucket");
        mOptions.addOption("k", KEY,      true, "(required) AWS S3 key");
        mOptions.addOption("f", FILEPATH, true, "(required) Local downloaded file path");
    }
    
    @Override
    protected boolean onRunAction(Map<String, String> map) throws Exception {
        
        String regionName = map.get(REGION);
        String bucket     = map.get(BUCKET);
        String key        = map.get(KEY);
        String filePath   = map.get(FILEPATH);
        
        if (regionName == null || bucket == null || key == null || filePath == null) {
            return false;
        }
        
        // Get AWSCredentials
        AWSCredentials credentials = AuthManager.getCredentials();
        if (credentials == null) {
            return false;
        }
        
        // Create AmazonS3Client
        AmazonS3 s3 = new AmazonS3Client(credentials);
        Region region = Region.getRegion(Regions.fromName(regionName));
        s3.setRegion(region);
        
        // Get Object by bucket and key
        GetObjectRequest request = new GetObjectRequest(bucket, key);
        S3Object object = s3.getObject(request);
        
        // Length
        long length = object.getObjectMetadata().getContentLength();
        
        // Read from object stream
        S3ObjectInputStream ois = object.getObjectContent();
        FileOutputStream fos = new FileOutputStream(filePath);
        try {
            long count = 0;
            int len = 0;
            byte buf[] = new byte[1000];
            while ((len = ois.read(buf)) != -1) {
                System.out.println("\r");
                fos.write(buf, 0, len);
                count += len;
                System.out.print(""+(count)+"/"+(length));
            }
            System.out.println(""+bucket+":"+key+" Download complete" );
        } finally {
            ois.close();
            fos.close();
        }
        
        return true;
    }
    
    protected void onShowUsage() {
        System.out.println(" download [options]");
    }
}
