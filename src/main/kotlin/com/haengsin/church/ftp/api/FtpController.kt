package com.haengsin.church.ftp.api

import com.haengsin.church.ftp.usecase.UploadFileUsecase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/file")
class FtpController(
    private val uploadFileUsecase: UploadFileUsecase
) {

    @PostMapping("/upload", consumes = ["multipart/form-data"])
    fun uploadFile(
        @RequestPart("file") file: MultipartFile
    ) = uploadFileUsecase.execute(file)

}