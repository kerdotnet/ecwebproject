package com.kerdotnet.tags;

import com.kerdotnet.entity.User;
import com.kerdotnet.resource.MessageManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@SuppressWarnings("serial")
public class UserInfoTag extends TagSupport{
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int doStartTag() throws JspException {
        StringBuilder sb = new StringBuilder();
        if (user != null) {
            sb.append(user.getFirstName())
                    .append(" ").append(user.getLastName())
                    .append(user.getMobile()).append(", ")
                    .append(MessageManager.getProperty("message.mobile")).append(": ")
                    .append(user.getMobile()).append(", ")
                    .append(MessageManager.getProperty("message.email")).append(": ")
                    .append(user.getEmail());
        } else {
            sb.append("No user data");
        }

        try {
            pageContext.getOut().write(sb.toString());
        } catch (IOException e) {
            new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
