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
    },BOOKCATALOG{
        {
            this.command = new ListBookCatalogCommand();
        }
    },VIEWBOOKCATALOGENTITY{
        {
            this.command = new ViewBookCatalogEntityCommand();
        }
    }, ADDBOOKCATALOG{
        {
            this.command = new AddBookCatalogEntityCommand();
        }
    }, EDITBOOKCATALOG{
        {
            this.command = new EditBookCatalogEntityCommand();
        }
    }, DELETEBOOKCATALOG{
        {
            this.command = new DeleteBookCatalogEntityCommand();
        }
    };

    IActionCommand command;
    public IActionCommand getCurrentCommand(){
        return command;
    }
}
