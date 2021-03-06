package in.ion.tcsiondemo.om;

import org.apache.torque.TorqueException;


/**
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Tue Oct 25 15:50:37 CEST 2016]
 *
 * You should add additional methods to this class to meet the
 * application requirements.  This class will only be generated as
 * long as it does not already exist in the output directory.
 */
/**
 * Added Interface and default implementations 
 * @author gk
 *
 */
public  class TurbinePermission
    extends in.ion.tcsiondemo.om.BaseTurbinePermission implements org.apache.fulcrum.security.entity.Permission, org.apache.fulcrum.security.model.turbine.entity.TurbinePermission
{
    /** Serial version */
    private static final long serialVersionUID = 1477403437105L;

	@Override
	public Integer getEntityId() {
		return getPermissionId();
	}

	@Override
	public void setEntityId(Integer id) throws TorqueException {
		setPermissionId(id);
	}

	 /**
     * Get the value of name.
     *
     * @return String
     */
    @Override
	public String getName() 
    {
        
        return getEntityName();
    }

    /**
     * Set the value of name.
     *
     * @param v new value
     */
    @Override
	public void setName(String v)
    {
        setEntityName(v);

    }

}
