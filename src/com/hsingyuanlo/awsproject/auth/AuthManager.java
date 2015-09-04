package com.hsingyuanlo.awsproject.auth;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

/**
 * Provide valid credentials for each AWS API call
 * @author hylo
 */

public class AuthManager {
    
    static private AWSCredentialsProvider PROVIDER = new DefaultAWSCredentialsProviderChain();
    
    static public AWSCredentials getCredentials() {
        AWSCredentials credentials = null;
        try {
            credentials = PROVIDER.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(e);
        }
        return credentials;
    }
}
