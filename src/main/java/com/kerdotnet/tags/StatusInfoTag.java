package com.kerdotnet.tags;

import com.kerdotnet.entity.User;
import com.kerdotnet.resource.MessageManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@SuppressWarnings("serial")
public class StatusInfoTag extends TagSupport{
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int doStartTag() throws JspException {

        if (status == null) {
            status = "NONE";
        }

        String statusInfo = MessageManager.getProperty("message." + status);

        try {
            pageContext.getOut().write(statusInfo);
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
