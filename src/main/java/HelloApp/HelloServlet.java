package HelloApp;

import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.context.RegistryType;
import org.wso2.carbon.registry.api.Registry;
import org.wso2.carbon.registry.api.RegistryException;
import org.wso2.carbon.registry.api.Resource;
import org.wso2.carbon.utils.multitenancy.MultitenantConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String resourcePath = request.getParameter("path");
			String resourceValue = request.getParameter("resource");
			String action = request.getParameter("action");
			String tenantDomain = request.getParameter("domain");

			out.println("Hello servlet, Tenant Resource test !");
			PrivilegedCarbonContext cCtx = PrivilegedCarbonContext.getThreadLocalCarbonContext();
			if (MultitenantConstants.SUPER_TENANT_DOMAIN_NAME.equals(tenantDomain)) {
				cCtx.setTenantDomain(MultitenantConstants.SUPER_TENANT_DOMAIN_NAME, true);
			} else {
				cCtx.setTenantDomain(tenantDomain, true);
			}
			Registry registry = cCtx.getRegistry(RegistryType.SYSTEM_CONFIGURATION);

			if (resourcePath != null && action != null) {
				if (action.equalsIgnoreCase("get")) {
					Resource property = registry.get(resourcePath);
					out.print(" Resource Accessed from " + tenantDomain + ",property name::=" +
					          resourceValue + ", value ::= " + registry.get(resourcePath)
					  .getProperty(resourceValue));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RegistryException e) {
			e.printStackTrace();
		}
	}
}
