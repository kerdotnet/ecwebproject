package com.kerdotnet.command;

import com.kerdotnet.controllers.SessionRequestContent;

/**
 * ActionCommand define basic contract for commands implementation
 * Yevhen Ivanov; 2018-04-09
 */

public interface IActionCommand {
    String execute(SessionRequestContent sessionRequestContent);
}
