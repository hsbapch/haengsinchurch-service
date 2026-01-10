package com.haengsin.church.common

import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*

@MappedSuperclass
abstract class PrimaryKeyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {

    var isDeleted: Boolean = false

    fun delete() {
        this.isDeleted = true
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is HibernateProxy && this::class != other::class) return false
        if (other is HibernateProxy) return other.hibernateLazyInitializer.identifier == id

        return this.id == (other as PrimaryKeyEntity).id
    }

    override fun hashCode(): Int = Objects.hashCode("${this.javaClass.name}:$id")
}

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class BaseTimeEntity(
    id: Long = 0L,
) : PrimaryKeyEntity(id) {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set
}

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class BaseEntity(
    id: Long = 0L
) : BaseTimeEntity(id) {
    @CreatedBy
    @Column(updatable = false)
    var createdBy: Long? = 0
        protected set

    @LastModifiedBy
    @Column(nullable = false)
    var updatedBy: Long? = 0
        protected set
}
