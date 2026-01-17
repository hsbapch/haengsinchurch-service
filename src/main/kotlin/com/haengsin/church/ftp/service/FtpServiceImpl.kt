package com.haengsin.church.ftp.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ObjectCannedACL
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.InputStream

@Service
class FtpServiceImpl(
    private val s3Client: S3Client,
    @Value("\${cloud.aws.s3.bucket}") private val bucket: String,
): FtpService {

    override fun putObject(key: String, data: InputStream): String {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .acl(ObjectCannedACL.PUBLIC_READ)
            .build()

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(data.readBytes()))
        return "https://$bucket.s3.amazonaws.com/$key"
    }

    override fun getObject(key: String): ByteArray {
        TODO("Not yet implemented")
    }


}