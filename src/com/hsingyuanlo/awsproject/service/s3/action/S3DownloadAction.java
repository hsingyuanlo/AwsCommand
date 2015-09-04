package com.hsingyuanlo.awsproject.service.s3.action;

import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.hsingyuanlo.awsproject.auth.AuthManager;

public class S3DownloadAction extends S3Action {
    
    final static public String USAGE    = "download <options>";
    final static public String REGION   = "region";
    final static public String BUCKET   = "bucket";
    final static public String KEY      = "key";
    final static public String FILEPATH = "filepath";
    
    private Options mOptions = new Options();
    
    public S3DownloadAction() {
        mOptions.addOption("r", REGION,   true, "(required) AWS region");
        mOptions.addOption("b", BUCKET,   true, "(required) AWS S3 bucket");
        mOptions.addOption("k", KEY,      true, "(required) AWS S3 key");
        mOptions.addOption("f", FILEPATH, true, "(required) Local downloaded file path");
    }
    
    @Override
    protected Options getOptions() {
        return mOptions;
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
                fos.write(buf, 0, len);
                count += len;
                System.out.print(""+(count)+"/"+(length)+"\r");
            }
            System.out.println(""+bucket+":"+key+" Download complete" );
        } finally {
            ois.close();
            fos.close();
        }
        
        return true;
    }
    
    protected void onShowUsage() {
        super.onShowUsage();
        Collection<Option> opts = getOptions().getOptions();
        System.out.println("Action : download");
        System.out.println("Options: ");
        for (Option opt : opts) {
            System.out.println(" -"+opt.getOpt()+", --"+opt.getLongOpt()+"\t"+opt.getDescription());
        }
        System.out.println();

    }
}
