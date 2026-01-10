package com.haengsin.church.authentication.component

import com.haengsin.church.authentication.dto.AccessToken
import com.haengsin.church.authentication.entity.Users
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

import java.time.OffsetDateTime
import java.util.Date
import kotlin.Long

@Component
class JwtTokenProvider(
    @Value("\${security.jwt.secret}")
    private val secretKey: String,
    @Value("\${security.jwt.access-token-ttl-seconds}")
    private val expiredDuration: Long,
) {


    private val signingKey = Keys.hmacShaKeyFor(
        secretKey.toByteArray(StandardCharsets.UTF_8)
    )

    fun issueAccessToken(user: Users): AccessToken {

        val now = OffsetDateTime.now()
        val expiredAt = now.plusSeconds(expiredDuration)

        val token = Jwts.builder()
            .setSubject(user.userId)
            .claim("uid", user.id)
            .setIssuedAt(Date.from(now.toInstant()))
            .setExpiration(Date.from(expiredAt.toInstant()))
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact()

        return AccessToken(
            accessToken =  token,
            expiresIn =  expiredDuration,
            expiredAt = expiredAt,
        )
    }


}