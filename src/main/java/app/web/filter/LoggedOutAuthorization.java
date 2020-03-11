package app.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter({"/views/home.jsf", "/views/friends.jsf", "/views/profile.jsf"})
public class LoggedOutAuthorization implements Filter
{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        String id = (String) ((HttpServletRequest) request).getSession().getAttribute("userId");

        if (id == null)
        {
            ((HttpServletResponse) response).sendRedirect("/views/index.jsf");
            return;
        }

        chain.doFilter(request, response);
    }
}
