package com.haengsin.church.domain.ftp.api


import com.haengsin.church.domain.ftp.usecase.UploadFileUsecase
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "FTP", description = "파일")
@RestController
@RequestMapping("/api/v1/file")
class FtpController(
    private val uploadFileUsecase: UploadFileUsecase
) {

    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(
        @RequestPart("file") file: MultipartFile
    ) = uploadFileUsecase.execute(file)

}