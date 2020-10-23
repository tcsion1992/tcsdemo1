package in.ion.tcsiondemo.modules.actions;



import org.apache.fulcrum.security.model.turbine.TurbineAccessControlList;
import org.apache.turbine.modules.actions.VelocitySecureAction;
import org.apache.turbine.om.security.User;
import org.apache.turbine.pipeline.PipelineData;
import org.apache.velocity.context.Context;

/**
 * Velocity Secure action.
 *
 * Always performs a Security Check that you've defined before executing the
 * doPerform().
 */
public class SecureAction extends VelocitySecureAction 
{
	/**
	 * This currently only checks to make sure that user is allowed to view the
	 * storage area. If you create an action that requires more security then
	 * override this method.
	 *
	 * @param data
	 *            Turbine information.
	 * @return True if the user is authorized to access the screen.
	 * @exception Exception,
	 *                a generic exception.
	 */
	@Override
	protected boolean isAuthorized(PipelineData data) throws Exception 
	{
		boolean isAuthorized = false;

		// Who is our current user?
		User user = getRunData(data).getUser();

		// Get the Turbine ACL implementation
		TurbineAccessControlList acl = getRunData(data).getACL();

		if (acl == null || ! ( acl.hasRole("turbineuser") || acl.hasRole("turbineadmin") ) ) 
		{
			getRunData(data).setMessage("You do not have permission to access this action");
			isAuthorized = false;
		} 
		else if ( acl.hasRole("turbineuser") || acl.hasRole("turbineadmin") )
		{
			isAuthorized = true;
		}

		return isAuthorized;
	}

	/**
	 * Implement this to add information to the context.
	 *
	 * @param data
	 *            Turbine information.
	 * @param context
	 *            Context for web pages.
	 * @exception Exception,
	 *                a generic exception.
	 */
	public void doPerform(PipelineData data, Context context) throws Exception 
	{

	}
}
