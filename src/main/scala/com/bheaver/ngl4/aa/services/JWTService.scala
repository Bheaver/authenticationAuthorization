package com.bheaver.ngl4.aa.services

import reactor.core.publisher.Mono
import org.jose4j.jwk.RsaJsonWebKey
import org.jose4j.jwk.RsaJwkGenerator
import org.jose4j.jws.AlgorithmIdentifiers
import org.jose4j.jws.JsonWebSignature
import org.jose4j.jwt.JwtClaims
import java.util

import com.bheaver.ngl4.aa.model.responses.Patron
import com.bheaver.ngl4.util.config.ApplicationConf


trait JWTService {
  def encrypt(patron: Patron): Mono[String]
  def isTokenValid(jwtToken: String): Mono[Boolean]
}


class JWTServiceImpl(applicationConf: ApplicationConf) extends JWTService{
  val jwtConf = applicationConf.general.jwt
  val rsaJsonWebKey: RsaJsonWebKey = RsaJwkGenerator.generateJwk(2048)
  val jws = new JsonWebSignature

  override def encrypt(patron: Patron): Mono[String] = Mono.fromSupplier(()=>{
    if(patron==null)
      return Mono.just("")
    rsaJsonWebKey.setKeyId(jwtConf.keyId)
    // Create the Claims, which will be the content of the JWT// Create the Claims, which will be the content of the JWT

    val claims = new JwtClaims
    claims.setIssuer(jwtConf.issuer) // who creates the token and signs it

    claims.setAudience(jwtConf.audience) // to whom the token is intended to be sent

    claims.setExpirationTimeMinutesInTheFuture(jwtConf.expirationTimeInMins) // time when the token will expire (10 minutes from now)

    claims.setGeneratedJwtId() // a unique identifier for the token

    claims.setIssuedAtToNow() // when the token was issued/created (now)

    claims.setSubject(jwtConf.subject) // the subject/principal is whom the token is about

    claims.setClaim("email", jwtConf.email) // additional claims/attributes about the subject can be added

    claims.setClaim("patronId",patron.patronId)
    claims.setClaim("patronCategoryId",patron.patronCategoryId)


    // A JWT is a JWS and/or a JWE with JSON claims as the payload.
    // In this example it is a JWS so we create a JsonWebSignature object.


    // The payload of the JWS is JSON content of the JWT Claims
    jws.setPayload(claims.toJson)

    // The JWT is signed using the private key
    jws.setKey(rsaJsonWebKey.getPrivateKey)

    // Set the Key ID (kid) header because it's just the polite thing to do.
    // We only have one key in this example but a using a Key ID helps
    // facilitate a smooth key rollover process
    jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId)

    // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256)

    // Sign the JWS and produce the compact serialization or the complete JWT/JWS
    // representation, which is a string consisting of three dot ('.') separated
    // base64url-encoded parts in the form Header.Payload.Signature
    // If you wanted to encrypt it, you can simply set this jwt as the payload
    // of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
    val jwt = jws.getCompactSerialization
    jwt
  })

  override def isTokenValid(jwtToken: String): Mono[Boolean] = ???
}