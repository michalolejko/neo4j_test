package pack;

import org.neo4j.ogm.session.Session;

class TicketService extends GenericService<Ticket> {

    public TicketService(Session session) {
		super(session);
	}
    
	@Override
	Class<Ticket> getEntityType() {
		return Ticket.class;
	}
    
}