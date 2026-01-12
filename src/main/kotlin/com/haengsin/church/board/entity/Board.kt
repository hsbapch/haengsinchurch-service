package com.haengsin.church.board.entity

import com.haengsin.church.board.enum.ArticleType
import com.haengsin.church.board.vo.UpdateBoardRequest
import com.haengsin.church.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Lob
import java.time.OffsetDateTime


@Entity
class Board(
    title: String,
    content: String,
    articleType: ArticleType,
    youtubeUrl: String? = null,
    id: Long = 0,
) : BaseEntity(id) {


    var title: String = title
        protected set

    var content: String = content
        protected set


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
        this.updatedAt = OffsetDateTime.now()
    }
}