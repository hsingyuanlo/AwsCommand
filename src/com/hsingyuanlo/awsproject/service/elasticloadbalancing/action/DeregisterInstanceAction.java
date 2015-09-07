package com.hsingyuanlo.awsproject.service.elasticloadbalancing.action;

import java.util.List;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancing;
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancingClient;
import com.amazonaws.services.elasticloadbalancing.model.DeregisterInstancesFromLoadBalancerRequest;
import com.amazonaws.services.elasticloadbalancing.model.DeregisterInstancesFromLoadBalancerResult;
import com.amazonaws.services.elasticloadbalancing.model.Instance;
import com.hsingyuanlo.awsproject.auth.AuthManager;
import com.hsingyuanlo.awsproject.service.AwsAction;
import com.hsingyuanlo.awsproject.utils.MetaUtil;

public class DeregisterInstanceAction extends AwsAction {
    
    static public String INSTANCE_ID = "instance-id";
    static public String ELB_NAME    = "elb-name";
    static public String EC2_SELF    = "self";
    
    public DeregisterInstanceAction() {
        super();
        mOptions.addOption("i", INSTANCE_ID, true,  "(required) AWS ec2 id");
        mOptions.addOption("l", ELB_NAME,    true,  "(required) AWS elasticloadbalancing name");
    }
    
    @Override
    protected boolean onRunAction(Map<String, String> map) throws Exception {
        
        String regionName = map.get(REGION);
        String instanceId = map.get(INSTANCE_ID);
        String elbName = map.get(ELB_NAME);
        
        // Auto-probe self instance id
        if (EC2_SELF.equals(instanceId)) {
            instanceId = MetaUtil.getInstanceId();
        }
        
        if (regionName == null || instanceId == null || elbName == null) {
            return false;
        }
        
        // Get AWSCredentials
        AWSCredentials credentials = AuthManager.getCredentials();
        if (credentials == null) {
            return false;
        }
        
        // Create AmazonElasticLoadBalancing
        AmazonElasticLoadBalancing elb = new AmazonElasticLoadBalancingClient(credentials);
        Region region = Region.getRegion(Regions.fromName(regionName));
        elb.setRegion(region);
        
        // Register instance with load balancer
        Instance instance = new Instance().withInstanceId(instanceId);
        DeregisterInstancesFromLoadBalancerRequest request = new DeregisterInstancesFromLoadBalancerRequest();
        request.withInstances(instance);
        request.withLoadBalancerName(elbName);
        
        // Test result
        DeregisterInstancesFromLoadBalancerResult result = elb.deregisterInstancesFromLoadBalancer(request);
        List<Instance> list = result.getInstances();
        for (Instance inst : list) {
            if (inst.getInstanceId().equals(instanceId)) {
                System.out.println("Deregister instance("+instanceId+") from loadbalancer("+elbName+"): failed");
                return false;
            }
        }
        System.out.println("Deregister instance("+instanceId+") from loadbalancer("+elbName+"): ok");
        return true;
    }
    
    @Override
    protected void onShowUsage() {
        System.out.println(" deregister-instance [options]");
    }
}
