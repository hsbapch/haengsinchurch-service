package com.haengsin.church.board.entity

import com.haengsin.church.board.enums.ArticleType
import com.haengsin.church.board.vo.UpdateBoardRequest
import com.haengsin.church.common.BaseEntity
import com.haengsin.church.util.OffsetDateUtils
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.OffsetDateTime


@Entity
class Board(
    title: String,
    content: String,
    articleType: ArticleType,
    youtubeUrl: String? = null,
    id: Long = 0,
) : BaseEntity(id) {


    @Column(nullable = false)
    var title: String = title
        protected set

    @Column(nullable = false)
    var content: String = content
        protected set

    @Column(nullable = true)
    var youtubeUrl: String? = youtubeUrl
        protected set


    @Enumerated(EnumType.STRING)
    var articleType: ArticleType = articleType
        protected set

    fun update(updateBoardRequest: UpdateBoardRequest) {
        this.title = updateBoardRequest.title
        this.content = updateBoardRequest.content
        this.youtubeUrl = updateBoardRequest.youtubeUrl
        this.articleType = updateBoardRequest.articleType
        this.updatedAt = OffsetDateUtils.toSeoul(OffsetDateTime.now())
    }
}