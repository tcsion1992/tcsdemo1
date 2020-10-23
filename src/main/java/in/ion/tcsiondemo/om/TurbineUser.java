package in.ion.tcsiondemo.om;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.fulcrum.security.model.turbine.entity.TurbineUserGroupRole;
import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;
import org.apache.torque.om.SimpleKey;


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
 * Added Interface
 * @author gk
 *
 */
public  class TurbineUser
    extends in.ion.tcsiondemo.om.BaseTurbineUser implements org.apache.fulcrum.security.model.turbine.entity.TurbineUser
{
    /** Serial version */
    private static final long serialVersionUID = 1477403437136L;
    
   /**
     * Get the value of id.
     *
     * @return Object
     */
    @Override
	public Object getId() 
    {
        
        return getEntityId();
    }

    /**
     * Set the value of id.
     *
     * @param v new value
     */
    @Override
	public void setId(Object v)
    {
        setEntityId( (Integer) v);

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
    
    /**
     * @see org.apache.fulcrum.security.torque.TorqueAbstractSecurityEntity#getDatabaseName()
     */
    @Override
    public String getDatabaseName()
    {
      return "default";
    }

    /**
     * @see org.apache.fulcrum.security.torque.TorqueAbstractSecurityEntity#retrieveAttachedObjects(java.sql.Connection)
     */
    @Override
	public void retrieveAttachedObjects(Connection con) throws TorqueException
    {
        Set<TurbineUserGroupRole> userGroupRoleSet = new HashSet<TurbineUserGroupRole>();

        List<in.ion.tcsiondemo.om.TurbineUserGroupRole> ugrs = getTurbineUserGroupRolesJoinTurbineUser(new Criteria(), con);

        for (in.ion.tcsiondemo.om.TurbineUserGroupRole ttugr : ugrs)
        {
            TurbineUserGroupRole ugr = new TurbineUserGroupRole();
            ugr.setUser(this);
            ugr.setRole(ttugr.getTurbineRole());
            ugr.setGroup(ttugr.getTurbineGroup(con));
            userGroupRoleSet.add(ugr);
        }

        setUserGroupRoleSet(userGroupRoleSet);
    }

    /**
     * @see org.apache.fulcrum.security.torque.TorqueAbstractSecurityEntity#update(java.sql.Connection)
     * 
     * @TODO
     */
    @Override
	public void update(Connection con) throws TorqueException
    {
    	Set<TurbineUserGroupRole> userGroupRoleSet = getUserGroupRoleSet();
        if (userGroupRoleSet != null)
        {


        }

        try
        {
            save(con);
        }
        catch (Exception e)
        {
            throw new TorqueException(e);
        }
    }

    /**
     * @see org.apache.fulcrum.security.torque.TorqueAbstractSecurityEntity#delete()
     */
    @Override
	public void delete() throws TorqueException
    {
        TurbineUserPeer.doDelete(SimpleKey.keyFor(getEntityId()));
    }



}
