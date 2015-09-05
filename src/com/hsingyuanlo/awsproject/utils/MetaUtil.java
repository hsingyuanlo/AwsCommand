package com.hsingyuanlo.awsproject.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class MetaUtil {

    static public String getInstanceId() throws IOException {
        URL url = new URL("http://169.254.169.254/latest/meta-data/instance-id");
        URLConnection conn = url.openConnection();
        Scanner scanner = new Scanner(conn.getInputStream());
        String id = null;
        if (scanner.hasNext()) {
            id = scanner.next();
        }
        scanner.close();
        return id;
    }
}
