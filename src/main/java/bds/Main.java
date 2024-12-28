package bds;

import bds.config.DataSourceConfig;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello, World!");
        DataSourceConfig.initializeDataSource(args);
        App.main(args);
    }
}