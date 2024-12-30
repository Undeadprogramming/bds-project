package bds;

import bds.config.DataSourceConfig;

public class Main {
    public static void main(String[] args) {

        DataSourceConfig.initializeDataSource(args);
        App.main(args);
    }
}