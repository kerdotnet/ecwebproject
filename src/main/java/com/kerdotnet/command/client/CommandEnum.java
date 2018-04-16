package com.kerdotnet.command.client;

import com.kerdotnet.command.*;

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
    REGISTRATION{
        {
            this.command = new RegistrationCommand();
        }
    },
    ADDUSER{
        {
            this.command = new AddUserCommand();
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
