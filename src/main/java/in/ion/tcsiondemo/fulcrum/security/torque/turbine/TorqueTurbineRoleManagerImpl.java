package in.ion.tcsiondemo.fulcrum.security.torque.turbine;

import java.sql.Connection;
import java.util.List;

import org.apache.fulcrum.security.entity.Role;
import org.apache.fulcrum.security.torque.TorqueAbstractRoleManager;
import org.apache.fulcrum.security.torque.peer.Peer;
import org.apache.fulcrum.security.torque.peer.PeerManagable;
import org.apache.fulcrum.security.torque.peer.PeerManager;
import org.apache.fulcrum.security.torque.peer.TorqueTurbinePeer;
import org.apache.fulcrum.security.util.DataBackendException;
import org.apache.torque.NoRowsException;
import org.apache.torque.TooManyRowsException;
import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;

import in.ion.tcsiondemo.om.TurbineRolePeer;
/**
 * This implementation persists to a database via Torque.
 *
 * @author <a href="mailto:tv@apache.org">Thomas Vandahl</a>
 * @version $Id:$
 */
public class TorqueTurbineRoleManagerImpl extends TorqueAbstractRoleManager implements PeerManagable
{
    PeerManager peerManager;
    
    /**
     * @see org.apache.fulcrum.security.torque.TorqueAbstractRoleManager#doSelectAllRoles(java.sql.Connection)
     */
    @Override
	@SuppressWarnings("unchecked")
	protected <T extends Role> List<T> doSelectAllRoles(Connection con) throws TorqueException
    {
        Criteria criteria = new Criteria(TurbineRolePeer.DATABASE_NAME);

        if ( (getCustomPeer())) {
            try
            {
                return ((TorqueTurbinePeer<T>) getPeerInstance()).doSelect( criteria, con );
            }
            catch ( DataBackendException e )
            {
                throw new TorqueException( e );
            }
        } else {
            return (List<T>) TurbineRolePeer.doSelect(criteria, con);
        }
    }

    /**
     * @see org.apache.fulcrum.security.torque.TorqueAbstractRoleManager#doSelectById(java.lang.Integer, java.sql.Connection)
     */
    @Override
	@SuppressWarnings("unchecked")
	protected <T extends Role> T doSelectById(Integer id, Connection con) throws NoRowsException, TooManyRowsException, TorqueException
    {
        if ( (getCustomPeer())) {
            try
            {
                return ((TorqueTurbinePeer<T>) getPeerInstance()).retrieveByPK( id, con );
            }
            catch ( DataBackendException e )
            {
                throw new TorqueException( e );
            }
        } else {
            return  (T)  TurbineRolePeer.retrieveByPK(id, con);
        }
    }

    /**
     * @see org.apache.fulcrum.security.torque.TorqueAbstractRoleManager#doSelectByName(java.lang.String, java.sql.Connection)
     */
    @Override
	@SuppressWarnings("unchecked")
	protected <T extends Role> T doSelectByName(String name, Connection con) throws NoRowsException, TooManyRowsException, TorqueException
    {
        Criteria criteria = new Criteria(TurbineRolePeer.DATABASE_NAME);
        criteria.where(TurbineRolePeer.ROLE_NAME, name);
        criteria.setIgnoreCase(true);
        criteria.setSingleRecord(true);
        
        List<T> roles = null;
        if ( (getCustomPeer())) {
            try
            {
                roles = ((TorqueTurbinePeer<T>)getPeerInstance()).doSelect( criteria, con );
            }
            catch ( DataBackendException e )
            {
                throw new TorqueException( e );
            }
        } else {
            roles =  (List<T>) TurbineRolePeer.doSelect(criteria, con);
        }

        if (roles.isEmpty())
        {
            throw new NoRowsException(name);
        }

        return roles.get(0);
    }
    
    public Peer getPeerInstance() throws DataBackendException {
        return getPeerManager().getPeerInstance(getPeerClassName(), TorqueTurbinePeer.class, getClassName());
    }
    
    /**
     * @return Returns the persistenceHelper.
     */
    @Override
	public PeerManager getPeerManager()
    {
        if (peerManager == null)
        {
            peerManager = (PeerManager) resolve(PeerManager.ROLE);
        }
        return peerManager;
    }
}
