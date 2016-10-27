package com.preggiapp.testproject.data;

import com.preggiapp.testproject.network.command.BaseCommand;

public class AppData {

    private static AppData instance;
    private BaseCommand baseCommand;


    public static AppData getInstance() {
        if(instance == null) {
            instance = new AppData();
        }
        return instance;
    }

    private AppData() {
        baseCommand = new BaseCommand();
    }

    public BaseCommand getBaseCommand() {
        return baseCommand;
    }
}