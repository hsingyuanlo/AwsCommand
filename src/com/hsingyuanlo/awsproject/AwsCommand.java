package com.hsingyuanlo.awsproject;

import java.util.Arrays;

import com.hsingyuanlo.awsproject.auth.AuthManager;
import com.hsingyuanlo.awsproject.service.s3.S3Manager;

/**
 * Command:
 *  AwsCommand [service] [action] [options]
 * 
 * @author hylo
 *
 */

public class AwsCommand {

    static public void main(String[] args) throws Exception {
        new AwsCommand().parseAndRun(args);
    }
    
    public void parseAndRun(String[] args) {
        // Test CredentialsProvider
        try {
            AuthManager.getCredentials();
        } catch (Exception e) {
            System.out.println();
            System.out.println(" Credentials are not found in below list: ");
            System.out.println(" 1. Environment Variables - AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY");
            System.out.println(" 2. Java System Properties - aws.accessKeyId and aws.secretKey");
            System.out.println(" 3. Credential profiles file at the default location (~/.aws/credentials)");
            System.out.println(" 4. Instance profile credentials delivered through the Amazon EC2 metadata service");
            System.out.println();
            return;
        }
        // Check number of parameters
        if (args.length < 1) {
            showUsage();
            return;
        }
        // Shift parameters by 1
        String[] modified_args = Arrays.copyOfRange(args, 1, args.length);
        if ("s3".equals(args[0])) {
            new S3Manager().parseAndRun(modified_args);
        } else {
            showUsage();
        }
    }
    
    private void showUsage() {
        System.out.println();
        System.out.println(" AwsCommand [service] [action] [options]");
        System.out.println(" Services:");
        System.out.println(" 1. s3");
        System.out.println();
    }
}
