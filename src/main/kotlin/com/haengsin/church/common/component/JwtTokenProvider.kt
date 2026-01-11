package com.haengsin.church.common.component

import com.haengsin.church.authentication.dto.Token
import com.haengsin.church.util.SecureUtils
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${security.jwt.secret}")
    private val secretKey: String,
    @Value("\${security.jwt.access-token-ttl-seconds}")
    private val accessTokenExpiresIn: Long,
    @Value("\${security.jwt.refresh-token-ttl-seconds}")
    private val refreshTokenExpiresIn: Long,
) {

    fun issueToken(userId: Long): Token =
        OffsetDateTime.now().let {
            Token(
                accessToken = issueAccessToken(userId, it),
                accessTokenExpiresIn = accessTokenExpiresIn,
                refreshToken = issueRefreshToken(userId, it),
                refreshTokenExpiresIn = refreshTokenExpiresIn,
                issuedAt = it,
            )
        }


    fun isReissueNeeded(token: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(SecureUtils.toHmacShaKey(secretKey))
                .setAllowedClockSkewSeconds(60)
                .build()
                .parseClaimsJws(token)
                .body
            val expiration = claims.expiration.toInstant().atOffset(OffsetDateTime.now().offset)
            val now = OffsetDateTime.now()
            val reissueThreshold = expiration.minusSeconds(accessTokenExpiresIn / 10)
            now.isAfter(reissueThreshold)
        } catch (e: ExpiredJwtException) {
            true
        }
    }

    fun reissueAccessTokenByRefreshToken(token: String): Token {
        val claims = Jwts.parserBuilder()
            .setSigningKey(SecureUtils.toHmacShaKey(secretKey))
            .setAllowedClockSkewSeconds(60)
            .build()
            .parseClaimsJws(token)
            .body
        val userId = claims["uid"].toString().toLong()
        return issueToken(userId)
    }



    private fun issueAccessToken(userId: Long, issuedAt: OffsetDateTime): String =
        createJwtToken(userId, issuedAt, accessTokenExpiresIn)

    private fun issueRefreshToken(userId: Long, issuedAt: OffsetDateTime): String =
        createJwtToken(userId, issuedAt, refreshTokenExpiresIn)

    private fun createJwtToken(userId: Long, issuedAt: OffsetDateTime, ttl: Long): String =
        Jwts.builder()
            .setSubject(userId.toString())
            .claim("uid", userId.toString())
            .setIssuedAt(Date.from(issuedAt.toInstant()))
            .setExpiration(Date.from(issuedAt.plusSeconds(ttl).toInstant()))
            .signWith(SecureUtils.toHmacShaKey(secretKey), SignatureAlgorithm.HS256)
            .compact()


}