package com.mhl.mycompanybackend.configs.jwt

import com.mhl.mycompanybackend.userdetails.UserDetailsImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils {
    @Value("\${app.jwtSecret}")
    private val jwtSecret: String? = null
    fun generateJwtToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetailsImpl
        val extraClaims: MutableMap<String, Any> = HashMap()
        extraClaims["user_id"] = userPrincipal.id!!
        extraClaims["username"] = userPrincipal.username
        extraClaims["email"] = userPrincipal.email
        extraClaims["firstname"] = userPrincipal.userData.firstname!!
        extraClaims["lastname"] = userPrincipal.userData.lastname!!
        extraClaims["middlename"] = userPrincipal.userData.middlename!!
        extraClaims["phone_number"] = userPrincipal.userData.phone_number!!
        extraClaims["roles"] = userPrincipal.roles[0].role_name!!
        extraClaims["permission"] = userPrincipal.roles[0].permissions?.name!!
        return Jwts.builder()
                .setSubject(userPrincipal.username).setIssuedAt(Date())
                .addClaims(extraClaims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact()
    }

    fun validateJwtToken(jwt: String?): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(jwt)
            return true
        } catch (e: MalformedJwtException) {
            System.err.println(e.message)
        } catch (e: IllegalArgumentException) {
            System.err.println(e.message)
        }
        return false
    }

    fun getUserNameFromJwtToken(jwt: String?): String {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(jwt).body.subject
    }
}
