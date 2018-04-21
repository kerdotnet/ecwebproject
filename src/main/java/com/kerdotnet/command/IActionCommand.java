package com.kerdotnet.command;

import javax.servlet.http.HttpServletRequest;

/**
 * ActionCommand define basic contract for commands implementation
 * Yevhen Ivanov; 2018-04-09
 */

public interface IActionCommand {
    String execute(HttpServletRequest request);
}
