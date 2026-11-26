package fr.estiam.helpdesklite.model

object TicketValidation {
    fun isTitleValid(title: String): Boolean = title.isNotBlank()

    fun isDescriptionValid(description: String): Boolean = description.trim().length >= 10

    fun create(
        id: String,
        title: String,
        description: String,
        priority: Priority,
        createdAt: Long,
    ): SupportTicket? {
        if (!isTitleValid(title) || !isDescriptionValid(description)) return null
        return SupportTicket(
            id = id,
            title = title.trim(),
            description = description.trim(),
            priority = priority,
            status = Status.OPEN,
            createdAt = createdAt,
        )
    }
}
