package com.haengsin.church.domain.ftp.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.domain.ftp.service.FtpService

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Usecase
class UploadFileUsecase(
    private val ftpService: FtpService
) : UsecaseInterface<MultipartFile, String>{

    override fun execute(input: MultipartFile): String {
        return ftpService.putObject("${ UUID.randomUUID()}-${input.originalFilename}", input.inputStream)
    }
}