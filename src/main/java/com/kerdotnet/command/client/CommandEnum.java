package com.kerdotnet.command.client;

import com.kerdotnet.command.authorization.AddUserCommand;
import com.kerdotnet.command.authorization.LoginCommand;
import com.kerdotnet.command.authorization.LogoutCommand;
import com.kerdotnet.command.authorization.RegistrationCommand;
import com.kerdotnet.command.bookcatalog.*;
import com.kerdotnet.command.bookitem.*;
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
    }, SAVEBOOKCATALOG{
        {
            this.command = new SaveBookCatalogEntityCommand();
        }
    }, EDITAUTHORS{
        {
            this.command = new EditBookCatalogAuthorsCommand();
        }
    }, ADDNEWAUTHOR {
        {
            this.command = new AddOneAuthorCommand();
        }
    }, DELETEBOOKCATALOGAUTHOR {
        {
            this.command = new DeleteBookCatalogAuthorCommand();
        }
    }, LISTBOOKITEMS {
        {
            this.command = new ListBookItemsCommand();
        }
    }, DELETEBOOKITEM {
        {
            this.command = new DeleteBookItemEntityCommand();
        }
    }, ADDBOOKITEM {
        {
            this.command = new AddBookItemEntityCommand();
        }
    }, SAVEBOOKITEM {
        {
            this.command = new SaveBookItemEntityCommand();
        }
    }, TAKEBOOKITEM {
        {
            this.command = new TakeBookItemEntityCommand();
        }
    }, CONFIRMTAKEBOOKITEM {
        {
            this.command = new ConfirmTakeBookItemEntityCommand();
        }
    }, CONFIRMRETURNBOOKITEM {
        {
            this.command = new ConfirmReturnBookItemEntityCommand();
        }
    }, LISTTAKENBOOKITEMS {
        {
            this.command = new ListTakenBookItemsCommand();
        }
    }, RETURNBOOKITEM {
        {
            this.command = new ReturnBookItemEntityCommand();
        }
    }, LISTMYBOOKITEMS {
        {
            this.command = new ListMyBookItemsCommand();
        }
    },LISTOVERDUEBOOKITEMS {
        {
            this.command = new ListOverdueBookItemsCommand();
        }
    }, SEARCH {
        {
            this.command = new SearchBookCatalogCommand();
        }
    }, CHANGELOCALE {
        {
            this.command = new ChangeLocaleCommand();
        }
    }, NOTIFYUSER {
        {
            this.command = new NotifyUserCommand();
        }
    }
    ;

    IActionCommand command;
    public IActionCommand getCurrentCommand(){
        return command;
    }
}
