package fr.estiam.helpdesklite.model

object TicketRules {
    fun canTransition(from: Status, to: Status): Boolean = when (from) {
        Status.OPEN -> to == Status.IN_PROGRESS
        Status.IN_PROGRESS -> to == Status.RESOLVED
        Status.RESOLVED -> false
    }

    fun sortByPriority(tickets: List<SupportTicket>): List<SupportTicket> =
        tickets.sortedBy { priorityRank(it.priority) }

    private fun priorityRank(priority: Priority): Int = when (priority) {
        Priority.HIGH -> 0
        Priority.MEDIUM -> 1
        Priority.LOW -> 2
    }
}
