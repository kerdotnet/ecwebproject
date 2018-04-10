package com.kerdotnet.command;

/**
 * Command Enum which contains all commands in the application
 * Yevhen Ivanov; 2018-04-09
 */

public enum CommandEnum {
    LOGIN{
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT{
        {
            this.command = new LogoutCommand();
        }
    };

    ActionCommand command;
    public ActionCommand getCurrentCommand(){
        return command;
    }
}
