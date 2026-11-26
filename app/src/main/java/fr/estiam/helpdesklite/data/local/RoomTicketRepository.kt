package fr.estiam.helpdesklite.data.local

import fr.estiam.helpdesklite.data.TicketRepository
import fr.estiam.helpdesklite.model.Status
import fr.estiam.helpdesklite.model.SupportTicket
import fr.estiam.helpdesklite.model.TicketRules
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomTicketRepository(
    private val dao: TicketDao,
    private val canTransition: (Status, Status) -> Boolean = TicketRules::canTransition,
) : TicketRepository {
    override suspend fun add(ticket: SupportTicket) = dao.insert(ticket.toRow())

    override suspend fun findById(id: String): SupportTicket? = dao.findById(id)?.toTicket()

    override suspend fun updateStatus(id: String, status: Status): Boolean {
        val current = dao.findById(id) ?: return false
        if (!canTransition(current.status, status)) return false
        return dao.updateStatus(id, current.status, status) > 0
    }

    override fun observeAll(): Flow<List<SupportTicket>> = dao.observeAll().toTickets()

    override fun observeByStatus(status: Status): Flow<List<SupportTicket>> =
        dao.observeByStatus(status).toTickets()

    private fun Flow<List<TicketRow>>.toTickets(): Flow<List<SupportTicket>> =
        map { rows -> rows.map(TicketRow::toTicket) }
}
