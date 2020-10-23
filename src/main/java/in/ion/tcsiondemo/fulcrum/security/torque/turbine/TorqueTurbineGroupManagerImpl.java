package in.ion.tcsiondemo.fulcrum.security.torque.turbine;

import java.sql.Connection;
import java.util.List;

import org.apache.fulcrum.security.entity.Group;
import org.apache.fulcrum.security.torque.TorqueAbstractGroupManager;
import org.apache.fulcrum.security.torque.peer.Peer;
import org.apache.fulcrum.security.torque.peer.PeerManagable;
import org.apache.fulcrum.security.torque.peer.PeerManager;
import org.apache.fulcrum.security.torque.peer.TorqueTurbinePeer;
import org.apache.fulcrum.security.util.DataBackendException;
import org.apache.torque.NoRowsException;
import org.apache.torque.TooManyRowsException;
import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;

import in.ion.tcsiondemo.om.TurbineGroupPeer;
/**
 * This implementation persists to a database via Torque.
 *
 * @author <a href="mailto:tv@apache.org">Thomas Vandahl</a>
 * @version $Id:$
 */
public class TorqueTurbineGroupManagerImpl extends TorqueAbstractGroupManager implements PeerManagable
{
    
    PeerManager peerManager;
    /**
     * @see org.apache.fulcrum.security.torque.TorqueAbstractGroupManager#doSelectAllGroups(java.sql.Connection)
     */
    @Override
	@SuppressWarnings("unchecked")
	protected <T extends Group> List<T> doSelectAllGroups(Connection con) throws TorqueException
    {
        Criteria criteria = new Criteria(TurbineGroupPeer.DATABASE_NAME);
        
        if ( (getCustomPeer())) {
            try
            {
                return ((TorqueTurbinePeer<T>)getPeerInstance()).doSelect( criteria, con );
            }
            catch ( DataBackendException e )
            {
                throw new TorqueException( e );
            }
        } else {
            return (List<T>) TurbineGroupPeer.doSelect(criteria, con);
        }


    }

    /**
     * @see org.apache.fulcrum.security.torque.TorqueAbstractGroupManager#doSelectById(java.lang.Integer, java.sql.Connection)
     */
    @Override
	@SuppressWarnings("unchecked")
	protected <T extends Group> T doSelectById(Integer id, Connection con) throws NoRowsException, TooManyRowsException, TorqueException
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
            return  (T)  TurbineGroupPeer.retrieveByPK(id, con);
        }

    }

    /**
     * @see org.apache.fulcrum.security.torque.TorqueAbstractGroupManager#doSelectByName(java.lang.String, java.sql.Connection)
     */
    @Override
	@SuppressWarnings("unchecked")
	protected <T extends Group> T doSelectByName(String name, Connection con) throws NoRowsException, TooManyRowsException, TorqueException
    {
        Criteria criteria = new Criteria(TurbineGroupPeer.DATABASE_NAME);
        criteria.where(TurbineGroupPeer.GROUP_NAME, name);
        criteria.setIgnoreCase(true);
        criteria.setSingleRecord(true);
        List<T> groups = null;
        
        if ( (getCustomPeer())) {
            try
            {
                
                groups = ((TorqueTurbinePeer<T>) getPeerInstance()).doSelect( criteria, con );
            }
            catch ( DataBackendException e )
            {
                throw new TorqueException( e );
            }
        } else {
            groups = (List<T>) TurbineGroupPeer.doSelect(criteria, con);
        }

        if (groups.isEmpty())
        {
            throw new NoRowsException(name);
        }

        return groups.get(0);
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
