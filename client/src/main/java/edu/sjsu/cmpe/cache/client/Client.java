package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        
        char[] val = {
            '0', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'
        };
        List < DistributedCacheService > Cluster = new ArrayList < DistributedCacheService > ();
        // Adding three servers
        Cluster.add(new DistributedCacheService("http://localhost:3000"));
        Cluster.add(new DistributedCacheService("http://localhost:3001"));
        Cluster.add(new DistributedCacheService("http://localhost:3002"));
        System.out.println("---- Inserting ----");
        for (int keys = 1; keys <= 10; keys++) {
            int key = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(keys)), Cluster.size());
            Cluster.get(key).put(keys, Character.toString(val[keys]));
            System.out.println("The key value pair " + keys + "-" + val[keys] + " is assigned to server " + key);
        }
        System.out.println("---- Retriving ----");
        for (int getkey = 1; getkey <= 10; getkey++) {
            int key2 = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(getkey)), Cluster.size());
            System.out.println("The key value pair " + getkey + "-" + Cluster.get(key2).get(getkey) + " is received to server " + key2);

        }
        System.out.println("Existing Cache Client...");
    }

}